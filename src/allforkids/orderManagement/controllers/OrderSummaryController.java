/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.orderManagement.controllers;

import allforkids.orderManagement.models.ShippingMethod;
import allforkids.orderManagement.models.LineItem;
import allforkids.orderManagement.models.Order;
import allforkids.orderManagement.models.ShippingMethod;
import allforkids.userManagement.models.User;
import allforkids.userManagement.models.UserSession;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dopsie.core.Model;
import dopsie.exceptions.ModelException;
import dopsie.exceptions.UnsupportedDataTypeException;
import helpers.DopsieCellBuilder;
import helpers.NavigationService;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author KHOUBEIB
 */
public class OrderSummaryController implements Initializable {

    @FXML
    private TableView<LineItem> itemsTableView;
    @FXML
    private TableColumn<LineItem, ImageView> imageItemColumn;
    @FXML
    private TableColumn<LineItem, String> descriptionAndPriceColumn;
    @FXML
    private TableColumn<LineItem, Integer> quantityColumn;
    @FXML
    private TableColumn<LineItem, Double> totalItemRowColumn;
    @FXML
    private JFXButton goToCart;

    ObservableList ShoppingitemList;
    private ArrayList<LineItem> allLineItems;
    @FXML
    private JFXButton checkoutPayment;
    @FXML
    private Label deliveryAddress;
    @FXML
    private JFXTextField newAddressInput;
    @FXML
    private Label totalAmount;

    /**
     * Initializes the controller class.
     */
    User currentUser = null;
    Order shoppingCart = null;
    @FXML
    private Label vatAmount;
    @FXML
    private Label deliveryFeeAmount;
    @FXML
    private Label deliveryFeeLabel;
    @FXML
    private JFXComboBox<String> comboBoxDeliveryMethod;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        itemsTableView.setEditable(true);
        totalItemRowColumn.setEditable(true);
        deliveryFeeAmount.setVisible(false);
        deliveryFeeLabel.setVisible(false);

//        try {
//            currentUser = Model.find(User.class, 1);
//            shoppingCart = currentUser.getUserShoppingCart();
//        } catch (ModelException | UnsupportedDataTypeException ex) {
//            Logger.getLogger(ShoppingCartController.class.getName()).log(Level.SEVERE, null, ex);
//        }

        try {
            currentUser = UserSession.getInstance();
            shoppingCart = currentUser.getUserShoppingCart();
        } catch (ModelException | UnsupportedDataTypeException ex) {
            Logger.getLogger(ShoppingCartController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String absolutePath = System.getProperty("uploads_folder");

        try {
            allLineItems = shoppingCart.lineItems();
//            nbrItem.setText("Number of Items : " + allLineItems.size());
//            totalHT.setText("VAT.free Total : " + shoppingCart.getOrderTotalWithVAT());
//            totalTTC.setText("Total TTC : " + shoppingCart.getOrderTotalWithVAT());
//            shippementLabel.setText("Shipping Type : " + shoppingCart.shippingMethod().getMethodName());
//            shippementFee.setText("shippementFee : " + shoppingCart.shippingMethod().getFee());
        } catch (ModelException ex) {
            Logger.getLogger(ShoppingCartController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ShoppingitemList = FXCollections.observableArrayList(allLineItems);

        // product item image column
        imageItemColumn.setCellValueFactory(new DopsieCellBuilder(p -> {
            try {
                String path = "file:" + absolutePath + (String) ((LineItem) p).product().getAttr("image");
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
                return ((LineItem) p).product().getAttr("short_description") + " : " + ((LineItem) p).product().getAttr("unit_price");
            } catch (ModelException ex) {
                Logger.getLogger(ShoppingCartController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }));

        quantityColumn.setEditable(true);

        // Quantity Spinner Column 
        quantityColumn.setCellValueFactory(new DopsieCellBuilder(p -> {
            int quantity = (int) ((LineItem) p).getAttr("quantity");
            return quantity;
        }));

        // Total  Column 
        totalItemRowColumn.setCellValueFactory(new DopsieCellBuilder(p -> {
            try {
                int quantity = (int) ((LineItem) p).getAttr("quantity");
                double unitPrice = (double) ((LineItem) p).product().getAttr("unit_price");
                return quantity * unitPrice;
            } catch (ModelException ex) {
                Logger.getLogger(ShoppingCartController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return 0;
        }));

        itemsTableView.setItems(ShoppingitemList);

        try {
            //display delivery address
            String delivAdress="";
            delivAdress = shoppingCart.getDeliveryAddress();
            if (delivAdress.isEmpty()) {
                delivAdress= "You haven't yet specified an address";
            }                
            deliveryAddress.setText(delivAdress);
            //display total
            totalAmount.setText(shoppingCart.getOrderTotalWithoutVAT().toString());
            //display vat
            vatAmount.setText(shoppingCart.getOrderTotalVat().toString());
            //display delivery fee 
            deliveryFeeAmount.setVisible(false);
            float feeAmount = (float) 0.0;
            feeAmount = (float) shoppingCart.shippingMethod().getAttr("extra_fee");
            if(feeAmount > 0.0){
                deliveryFeeAmount.setText(Float.toString(feeAmount));
                deliveryFeeAmount.setVisible(true);
            }
            
            //display total to pay
            float fee = (float)(shoppingCart.shippingMethod().getAttr("extra_fee"));
            Double ttc = (Double) shoppingCart.getOrderTotalWithVAT();
            Double ttcToPay = fee + ttc;
            totalAmount.setText(ttcToPay.toString());

        } catch (ModelException ex) {
            Logger.getLogger(OrderSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // comboBox shipping method
        List<ShippingMethod> shippements = null;
        try {
            shippements = Order.getAllShippingMethod();
        } catch (ModelException ex) {
            Logger.getLogger(OrderSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedDataTypeException ex) {
            Logger.getLogger(OrderSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList<String> list = FXCollections.observableArrayList();
        // adding the status list from thedb to the observable list
        shippements.forEach((t) -> {

            list.add(t.getMethodName());
            System.out.println(t.getMethodName());
        });

        comboBoxDeliveryMethod.getItems().addAll(list);

    }

    @FXML
    private void goToCart(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/orderManagement/views/shoppingCart.fxml");

    }

    @FXML
    private void saveAddress(ActionEvent event) throws UnsupportedDataTypeException, ModelException {
        shoppingCart.setDeliveryAddress(newAddressInput.getText());
        try {
            deliveryAddress.setText(shoppingCart.getDeliveryAddress());

        } catch (ModelException ex) {
            Logger.getLogger(OrderSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void updateDeliveryMethod(ActionEvent event) throws ModelException, UnsupportedDataTypeException {
        String method = comboBoxDeliveryMethod.getSelectionModel().getSelectedItem();
        if (!method.isEmpty()) {
            shoppingCart.setDeliveryMethodByName(method);
            shoppingCart.save();
            try {
                if (method.equals("Pick up at Store")) {
                    deliveryFeeAmount.setVisible(false);
                    deliveryFeeLabel.setVisible(false);
                } else {
                    deliveryFeeAmount.setVisible(true);
                    deliveryFeeLabel.setVisible(true);
                }
                deliveryFeeAmount.setText(shoppingCart.shippingMethod().getAttr("extra_fee").toString());
                Double fee = Double.parseDouble(deliveryFeeAmount.getText());
                Double ttc = (Double) shoppingCart.getOrderTotalWithVAT();
                Double ttcToPay = fee + ttc;
                totalAmount.setText(ttcToPay.toString());

            } catch (ModelException ex) {
                Logger.getLogger(OrderSummaryController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @FXML
    private void proceddToPayment(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/orderManagement/views/orderPayment.fxml");
    }

}