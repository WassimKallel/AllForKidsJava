/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.dashboard.store;

import allforkids.store.models.Product;
import dopsie.core.Model;
import dopsie.exceptions.ModelException;
import dopsie.exceptions.UnsupportedDataTypeException;
import helpers.DopsieCellBuilder;
import helpers.NavigationService;
import helpers.TrayNotificationService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Amine
 */
public class ProductsListController implements Initializable {

    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn<Product, String> refCol;
    @FXML
    private TableColumn<Product, String> nameCol;
    @FXML
    private TableColumn<Product, String> categoryCol;
    @FXML
    private TableColumn<Product, Double> unitPriceCol;
    @FXML
    private TableColumn<Product, String> descriptionCol;

    private ArrayList<Product> products = new ArrayList<>();
    
    private ObservableList<Product> obs;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            products = Model.fetch(Product.class).all().where("active", "1").execute();
        } catch (ModelException | UnsupportedDataTypeException ex) {
            Logger.getLogger(ProductsListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        obs = FXCollections.observableArrayList(products);
        
        refCol.setCellValueFactory(new DopsieCellBuilder(p -> {
            return (String)((Product) p).getAttr("reference");
        }));
        
        nameCol.setCellValueFactory(new DopsieCellBuilder(p -> {
            return (String)((Product) p).getAttr("name");
        }));
        
        categoryCol.setCellValueFactory(new DopsieCellBuilder(p -> {
            try {
                return (String)((Product) p).category().getAttr("name");
            } catch(ModelException ex) {
                Logger.getLogger(ProductsListController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }));
        
        unitPriceCol.setCellValueFactory(new DopsieCellBuilder(p -> {
            return (Double)((Product) p).getAttr("unit_price");
        }));
        
        descriptionCol.setCellValueFactory(new DopsieCellBuilder(p -> {
            return (String)((Product) p).getAttr("short_description");
        }));
        
        productsTableView.setItems(obs);
        
    }    
   

    @FXML
    private void goToMainMenu(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/dashboard/Main.fxml");
    }

    @FXML
    private void goToAdd(ActionEvent event) {
        NavigationService.goTo(event, this, "SingleProduct.fxml");
    }

    @FXML
    private void deleteClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText(null);
        alert.setContentText("Do you confirm ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                Product selectedProduct = (Product) productsTableView.getSelectionModel().getSelectedItem();
                selectedProduct.setAttr("active", "0");
                selectedProduct.save();
                products.remove(selectedProduct);
                productsTableView.getItems().remove(selectedProduct);
                TrayNotificationService.successBlueNotification("Product deleted!", "Product deleted!");
            } catch (ModelException | UnsupportedDataTypeException ex) {
                TrayNotificationService.failureRedNotification("Product deletion failed!", "Product deletion failed!");
            }
        }
    }

    @FXML
    private void editClicked(ActionEvent event) {
        Product selectedProduct = (Product) productsTableView.getSelectionModel().getSelectedItem();
        if(selectedProduct == null) {
            TrayNotificationService.failureRedNotification("Warning", "You should select a product to edit");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SingleProduct.fxml"));
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.hide();
            Pane newLoadedPane = loader.load();
            Scene HomePageScene = new Scene(newLoadedPane);
            SingleProductController controller = loader.<SingleProductController>getController();
            controller.setProduct(selectedProduct);
            appStage.setScene(HomePageScene);
            appStage.show();
        } catch (IOException ex) {
            Logger.getLogger(ProductsListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void goToCategories(ActionEvent event) {
        NavigationService.goTo(event, this, "CategoriesList.fxml");
    }

    @FXML
    private void goToStores(ActionEvent event) {
        NavigationService.goTo(event, this, "StoresList.fxml");
    }
    
}
