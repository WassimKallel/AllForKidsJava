/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.orderManagement.controllers;

import allforkids.orderManagement.models.Order;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dopsie.core.Model;
import dopsie.dataTypes.Date;
import dopsie.exceptions.ModelException;
import dopsie.exceptions.UnsupportedDataTypeException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author KHOUBEIB
 */
public class OrderTableController implements Initializable {

    @FXML
    private TableView<OrderView> orderTable;
    @FXML
    private TableColumn<OrderView, String> orderRefColumn;
    @FXML
    private TableColumn<OrderView, String> orderSatusColumn;
    @FXML
    private TableColumn<OrderView, String> orderCustomerColumn;
    @FXML
    private TableColumn<OrderView, Double> orderAmountColumn;
    @FXML
    private TableColumn<OrderView, String> orderShippingColumn;
    @FXML
    private TableColumn<OrderView, String> orderDateColumn;

    ObservableList<OrderView> orderList = FXCollections.observableArrayList();
    @FXML
    private Label customLab;
    @FXML
    private Label totalLab;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ArrayList<OrderView> ordersList = null;

        /*       try {
            orders = Model.fetch(Order.class).all().execute();
        } catch (ModelException ex) {
            Logger.getLogger(OrderTableController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedDataTypeException ex) {
            Logger.getLogger(OrderTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
        try {
            ResultSet rs
                    = //                    Order.sqlQuery("SELECT order_reference, "
                    //                    + "order_status, "
                    //                    + "customer_id, "
                    //                    + "payment_status"
                    //                    + "  FROM `order`");
                    //            
                    Order.sqlQuery("SELECT CONCAT(u.firstname,\" \",u.lastname) as \"customer\" , sh.method as \"shippingMethod\","
                            + " sc.total as \"amount\", "
                            + "od.order_reference as \"reference\", os.`status` \n"
                            + ", od.creation_date as \"creationDate\"\n"
                            + "FROM user u , shopping_cart sc, \n"
                            + "shipping_method sh, `order` od , order_status os\n"
                            + "WHERE\n"
                            + "u.id = sc.fk_user_id and od.customer_id = u.id "
                            + "and od.fk_shipping_method_id = sh.id and os.id = od.order_status;");

            while (rs.next()) {

                System.out.println(rs.getString("amount"));

                orderList.add(new OrderView(
                        rs.getString("reference"),
                        rs.getString("customer"),
                        rs.getString("status"),
                        rs.getString("shippingMethod"), 
                        rs.getString("creationDate"),
                        rs.getDouble("amount")
                ));
            }

            //    System.out.println("Lines number :"+orderList.stream().count());
        } catch (UnsupportedDataTypeException ex) {
            Logger.getLogger(OrderTableController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(OrderTableController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ModelException ex) {
            Logger.getLogger(OrderTableController.class.getName()).log(Level.SEVERE, null, ex);
        }

        orderRefColumn.setCellValueFactory(new PropertyValueFactory<>("reference"));
        orderSatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        orderCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
        orderAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        orderShippingColumn.setCellValueFactory(new PropertyValueFactory<>("shippingMethod"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));

        orderTable.setItems(orderList);
        
        
        //clear OrderDetails
        showOrderDetails(null);
        
        //Listen for selection changes and show the order properties when changed
        orderTable.getSelectionModel().selectedItemProperty().addListener(
        
                ((observable, oldValue, newValue) -> { showOrderDetails(newValue);
                })
        
        );
        
        

    }

    private void showOrderDetails(OrderView order){
        if (order != null){
            totalLab.setText(String.valueOf(order.getAmount()));
            customLab.setText(order.getCustomer());
            
        }else
        {
            totalLab.setText("---");
            customLab.setText("---");
        }
    }
    
    public class OrderView extends RecursiveTreeObject<Object>{

        private final String reference;
        private final String customer;
        private final String status;
        private final String shippingMethod;
        private final String creationDate;
        private final Double amount;

        public OrderView(String reference, String customer, String status, String shippingMethod, String creationDate, Double amount) {
            this.reference = reference;
            this.customer = customer;
            this.status = status;
            this.shippingMethod = shippingMethod;
            this.creationDate = creationDate;
            this.amount = amount;
        }

        public String getReference() {
            return reference;
        }

        public String getCustomer() {
            return customer;
        }

        public String getStatus() {
            return status;
        }

        public String getShippingMethod() {
            return shippingMethod;
        }

        public String getCreationDate() {
            return creationDate;
        }

        public Double getAmount() {
            return amount;
        }
        

        
    
        
    }

}
