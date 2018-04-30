/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.orderManagement.controllers;

import allforkids.userManagement.models.User;
import allforkids.orderManagement.models.*;
import allforkids.userManagement.models.UserSession;
import com.jfoenix.controls.JFXButton;
import dopsie.exceptions.ModelException;
import dopsie.exceptions.UnsupportedDataTypeException;
import helpers.DopsieCellBuilder;
import helpers.NavigationService;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author KHOUBEIB
 */
public class ShoppingCartController implements Initializable {

    @FXML
    public TableView<LineItem> itemsTableView;
    @FXML
    private TableColumn<LineItem, ImageView> imageItemColumn;
    @FXML
    private TableColumn<LineItem, String> descriptionAndPriceColumn;
    @FXML
    private TableColumn<LineItem, Integer> quantityColumn;
    @FXML
    private TableColumn<LineItem, Double> totalItemRowColumn;
//    @FXML
//    private TableColumn<ShoppingItem, Button> deleteButtonColumn;

    ObservableList ShoppingitemList;
    @FXML
    private Label nbrItem;
    @FXML
    private JFXButton filterButton1;
    @FXML
    private Label totalHT;
    @FXML
    private Label nbrItem1;

    @FXML
    private JFXButton goToStore;
    @FXML
    private Label shippementLabel;
    @FXML
    private Label shippementFee;
    
    @FXML
    private Label totalTTC;

    private ArrayList<LineItem> allLineItems;
    
    /**
     * Initializes the controller class.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        itemsTableView.setEditable(true);
        totalItemRowColumn.setEditable(true);

        User currentUser = null;
        Order shoppingCart = null;
        
        try {
            currentUser = UserSession.getInstance();
            shoppingCart = currentUser.getUserShoppingCart();
        } catch (ModelException | UnsupportedDataTypeException ex) {
            Logger.getLogger(ShoppingCartController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String absolutePath = System.getProperty("uploads_folder");
        
        try {
            allLineItems = shoppingCart.lineItems();
            nbrItem.setText("Number of Items : " + allLineItems.size());
            totalHT.setText("VAT.free Total : " + shoppingCart.getOrderTotalWithVAT());
            totalTTC.setText("Total TTC : " + shoppingCart.getOrderTotalWithVAT());
            shippementLabel.setText("Shipping Type : " + shoppingCart.shippingMethod().getMethodName());
            shippementFee.setText("shippementFee : " + shoppingCart.shippingMethod().getFee());
        } catch (ModelException ex) {
            Logger.getLogger(ShoppingCartController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ShoppingitemList = FXCollections.observableArrayList(allLineItems);
        
        // product item image column
        imageItemColumn.setCellValueFactory(new DopsieCellBuilder(p -> {
            try {
                String path;
                String imageRelPath = (String)((LineItem)p).product().getAttr("image");
                if(!imageRelPath.isEmpty() && imageRelPath != null) {
                    path = "file:" + absolutePath + imageRelPath;
                } else {
                    path = "/img/default-product.png";
                }
                ImageView productImage = new ImageView(path);
                productImage.setFitHeight(100);
                productImage.setFitWidth(100);
                return productImage;
            } catch (ModelException ex) {
                Logger.getLogger(ShoppingCartController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }));

        imageItemColumn.setPrefWidth(110);

        // produc (description + unit price) column
        descriptionAndPriceColumn.setCellValueFactory(new DopsieCellBuilder(p -> {
            try {
                return ((LineItem) p).product().getAttr("short_description") + " : " +((LineItem) p).product().getAttr("unit_price");
            } catch (ModelException ex) {
                Logger.getLogger(ShoppingCartController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }));

        quantityColumn.setEditable(true);
        
        // Quantity Spinner Column 
        quantityColumn.setCellValueFactory(new DopsieCellBuilder(p -> {
            int quantity = (int)((LineItem)p).getAttr("quantity");
            return quantity;
        }));
        quantityColumn.setCellFactory(tc -> {
                return new SpinnerTableCell<>(20, (a,b) -> updatedQuantity(a,b), 1);
            }
        );

        // Total  Column 
        totalItemRowColumn.setCellValueFactory(new DopsieCellBuilder(p -> {
            try {
                int quantity = (int)((LineItem)p).getAttr("quantity");
                double unitPrice = (double) ((LineItem) p).product().getAttr("unit_price");
                return quantity * unitPrice;
            } catch (ModelException ex) {
                Logger.getLogger(ShoppingCartController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return 0;
        }));

        //  Delete button column 
        //Insert Button
        TableColumn deleteColumn = new TableColumn<>("Action");
        itemsTableView.getColumns().add(deleteColumn);

        deleteColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<LineItem, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<LineItem, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        //Adding the Button to the cell
        deleteColumn.setCellFactory(
                new Callback<TableColumn<LineItem, Boolean>, TableCell<LineItem, Boolean>>() {

            @Override
            public TableCell<LineItem, Boolean> call(TableColumn<LineItem, Boolean> p) {
                return new ButtonCell(ShoppingitemList, itemsTableView);
            }

        });

        /* fill the itemsTableView with items */
        // adding the shoppingitemList
        itemsTableView.setItems(ShoppingitemList);
        // setting the table view editable
        itemsTableView.setEditable(true);

    }
    
    private void updatedQuantity(LineItem item, Integer diff) {
        try {
            try {
                int indexOfItem = itemsTableView.getItems().indexOf(item);
                int quantity = (Integer)item.getAttr("quantity") + diff;
                System.out.println("quantity is " + quantity);
                item.setAttr("quantity", quantity);
                item.save();
                ShoppingitemList.set(indexOfItem, item);
            } catch (ModelException | UnsupportedDataTypeException ex) {
                Logger.getLogger(ShoppingCartController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            List<LineItem> allItems = itemsTableView.getItems();
            
            User currentUser = UserSession.getInstance();
            Order shoppingCart = currentUser.getUserShoppingCart();
            
            System.out.println(shoppingCart);
            allLineItems = shoppingCart.lineItems();
            System.out.println(allLineItems);
            nbrItem.setText("Number of Items : " + allLineItems.size());
            totalHT.setText("VAT.free Total : " + shoppingCart.getOrderTotalWithoutVAT());
            totalTTC.setText("Total TTC : " + shoppingCart.getOrderTotalWithVAT());
            shippementLabel.setText("Shipping Type : " + shoppingCart.shippingMethod().getMethodName());
            shippementFee.setText("shippementFee : " + shoppingCart.shippingMethod().getFee());
        } catch (ModelException | UnsupportedDataTypeException ex) {
            Logger.getLogger(ShoppingCartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void goToStore(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/store/ProductsList.fxml");
    }
}
