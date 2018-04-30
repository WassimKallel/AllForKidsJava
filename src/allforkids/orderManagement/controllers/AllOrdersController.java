/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.orderManagement.controllers;

import allforkids.orderManagement.models.Order;
import java.net.URL;
import java.time.LocalDate;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import dopsie.dataTypes.Date;
import dopsie.exceptions.ModelException;
import dopsie.exceptions.UnsupportedDataTypeException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author KHOUBEIB
 */
public class AllOrdersController implements Initializable {

    @FXML
    private Button addOrderButton;
    @FXML
    private TextField OrderStatusTextField;
    @FXML
    private TextField paymentTextField;
    @FXML
    private TextField CustomerIdTextField;
    @FXML
    private TextField orderReferenceTextField;
    @FXML
    private DatePicker creationDatePicker;
    @FXML
    private Button sayHelloButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void addOrder(ActionEvent event) throws ModelException {

        String orderStatus = OrderStatusTextField.getText();
        String payment = paymentTextField.getText();
        String customerId = CustomerIdTextField.getText();
        String orderRef = orderReferenceTextField.getText();
        LocalDate dateCreation = creationDatePicker.getValue();

        Order order = new Order();

        System.out.println(dateCreation);
        System.out.println(new Date());
        
        order.setAttr("payment_status", payment);
        order.setAttr("customer_id", customerId);
        order.setAttr("order_status", orderStatus);
        order.setAttr("creation_date", new Date());
        order.setAttr("order_reference", orderRef);

        System.out.println(order.getPrimaryKeyName());

        try {
            order.save();
        } catch (UnsupportedDataTypeException ex) {
            Logger.getLogger(AllOrdersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

    @FXML
    public void Print(ActionEvent event) {
        System.out.println("Hello");
    }

}
