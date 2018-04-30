/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.dashboard.store;

import allforkids.store.models.Category;
import allforkids.store.models.Product;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dopsie.core.Model;
import dopsie.exceptions.ModelException;
import dopsie.exceptions.UnsupportedDataTypeException;
import helpers.CustomImageViewPane;
import helpers.NavigationService;
import helpers.TrayNotificationService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Amine
 */
public class SingleProductController implements Initializable {

    @FXML
    private JFXTextField nameTF;
    @FXML
    private JFXTextField referenceTF;
    @FXML
    private JFXTextField vatTF;
    @FXML
    private JFXTextField quantityTF;
    @FXML
    private JFXTextField descriptionTF;
    @FXML
    private JFXComboBox<String> categoryDropDown;
    @FXML
    private AnchorPane imageContainer;
    @FXML
    private Label noImageLabel;
    @FXML
    private HTMLEditor longDescriptionEditor;
    
    private String newPicPath;
    @FXML
    private JFXButton validateBtn;
    @FXML
    private Label title;
    
    private Product product;
    @FXML
    private JFXTextField unitPriceTf;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ArrayList<Category> categories = Model.fetch(Category.class).all().where("active", "1").execute();
            for(Category category: categories) {
                categoryDropDown.getItems().add((String)category.getAttr("name"));
            }
        } catch (ModelException | UnsupportedDataTypeException ex) {
            Logger.getLogger(SingleProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void backToProductsList(ActionEvent event) {
        NavigationService.goTo(event, this, "ProductsList.fxml");
    }

    @FXML
    private void addProduct(ActionEvent event) {
        String productName = this.nameTF.getText();
        String reference = this.referenceTF.getText();
        String unitPrice = this.unitPriceTf.getText();
        String vat = this.vatTF.getText();
        String quantity = this.quantityTF.getText();
        String description = this.descriptionTF.getText();
        String longDescription = this.longDescriptionEditor.getHtmlText();
        String categoryName = this.categoryDropDown.getValue();
        
        if(productName.isEmpty() 
                || reference.isEmpty() 
                || reference.isEmpty() 
                || vat.isEmpty() 
                || quantity.isEmpty()
                || description.isEmpty()
                || longDescription.isEmpty()) {
            TrayNotificationService.failureRedNotification("Missing data", "you should fill all fields");
            return;
        }
        
        try {
            Category category = (Category)Model.fetch(Category.class).all().where("name", categoryName).execute().get(0);
            int categoryID = (int)category.getAttr("id");
       
        
        if(this.product == null) {
            this.product = new Product();
        }
        
        product.setAttr("name", productName);
        product.setAttr("reference", reference);
        product.setAttr("unit_price", Double.parseDouble(unitPrice));
        product.setAttr("quantity", Double.parseDouble(quantity));
        product.setAttr("vat_rate", vat);
        product.setAttr("description", longDescription);
        product.setAttr("short_description", description);
        product.setAttr("category_id", categoryID);
        if(newPicPath != null) {
            if (this.newPicPath != null) {
                File source = new File(this.newPicPath);
                String outputFilePath = "uploads/products/" + (new Date()).getTime();
                File dest = new File(System.getProperty("uploads_folder") + outputFilePath);
                copyFileUsingStream(source, dest);
                product.setAttr("image", outputFilePath);
            }
        }
        product.save();
        TrayNotificationService.successBlueNotification("Product Stored Successfully", "Product Stored Successfully");
        } catch (UnsupportedDataTypeException | ModelException | IOException ex) {
            Logger.getLogger(SingleProductController.class.getName()).log(Level.SEVERE, null, ex);
            TrayNotificationService.failureRedNotification("Error storing product", "Error storing product");
        }
        NavigationService.goTo(event, this, "ProductsList.fxml");
        
    }
    
    public void setProduct(Product product) {
        this.title.setText("Update an existing product");
        this.validateBtn.setText("Update");
        
        this.product = product;
        
        try {
            this.nameTF.setText((String) product.getAttr("name"));
            this.referenceTF.setText((String) product.getAttr("reference"));
            this.vatTF.setText(product.getAttr("vat_rate") + "");
            this.quantityTF.setText(product.getAttr("quantity") + "");
            this.unitPriceTf.setText(product.getAttr("unit_price") + "");
            this.descriptionTF.setText((String) product.getAttr("short_description"));
            this.categoryDropDown.setValue((String)product.category().getAttr("name"));
            if(product.getAttr("image") != null) {
                setPic("file:" + System.getProperty("uploads_folder") + product.getAttr("image"));
            }
            this.longDescriptionEditor.setHtmlText((String)product.getAttr("description"));
        } catch (ModelException ex) {
            Logger.getLogger(SingleProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }
    
    public void setPic(String path) {
        System.out.println(path);
        double containerWidth = imageContainer.getPrefWidth();
        double containerHeight = imageContainer.getPrefHeight();

        CustomImageViewPane avatarImView
                = new CustomImageViewPane(path,
                        containerWidth,
                        containerHeight);
        imageContainer.getChildren().removeAll(imageContainer.getChildren());

        imageContainer.getChildren().add(avatarImView);

    }
        
    @FXML
    private void uploadPic(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image");

        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(appStage);
        if (file != null) {
            this.newPicPath = file.getAbsolutePath();
            setPic("file:" + this.newPicPath);
        }
    }

    public static String getCurrentAbsolutePath() {
        return Paths.get("").toAbsolutePath().toString();
    }
    
}
