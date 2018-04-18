/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.store;

import allforkids.store.models.Product;
import com.jfoenix.controls.JFXButton;
import helpers.CustomImageViewPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Amine
 */
public class ProductCardController implements Initializable {

    @FXML
    private AnchorPane imageContainer;
    @FXML
    private JFXButton productName;
    @FXML
    private JFXButton productPrice;
    
    private Product product;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
        
    public void setProduct(Product product) {
        this.product = product;
        String prodName = (String) product.getAttr("name");
        Double prodPrice = (Double) product.getAttr("unit_price");
        this.productName.setText(prodName);
        this.productPrice.setText(prodPrice + " HT");
        String imagePath = (String) product.getAttr("image");
        if(imagePath != null) {
            String absolutePath =  System.getProperty("uploads_folder");
            imagePath = "file:" + absolutePath + imagePath;
        } else {
            imagePath = "/img/default-product.png";
        }
        double imageWidth = imageContainer.getPrefWidth();
        double imageHeight = imageContainer.getPrefHeight();
        imageContainer.getChildren().add(new CustomImageViewPane(imagePath, imageWidth, imageHeight));
    }
    
    @FXML
    private void goToProduct(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/allforkids/store/ProductDetails.fxml"));
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.hide();
            Pane newLoadedPane = loader.load();
            Scene HomePageScene = new Scene(newLoadedPane);
            ProductDetailsController controller = loader.<ProductDetailsController>getController();
            controller.setProduct(product);
            appStage.setScene(HomePageScene);
            appStage.show();
        } catch (IOException ex) {
            Logger.getLogger(ProductCardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
