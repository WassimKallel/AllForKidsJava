/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.orderManagement.controllers;

import allforkids.orderManagement.models.Order;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dopsie.core.Model;
import dopsie.exceptions.ModelException;
import dopsie.exceptions.UnsupportedDataTypeException;
import helpers.NavigationService;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.ChoiceBoxTreeTableCell;

/**
 * FXML Controller class
 *
 * @author KHOUBEIB
 */
public class OrdersTableController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTreeTableView<OrderView> treeView;
    @FXML
    private JFXTextField inputField;
    @FXML
    private JFXButton filterButton;
    @FXML
    private JFXButton goToMain;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO// TODO
        JFXTreeTableColumn<OrderView, String> referenceCol = new JFXTreeTableColumn<>("Reference");
        referenceCol.setPrefWidth(300);
        referenceCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<OrderView, String> param) -> param.getValue().getValue().reference //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        );

        JFXTreeTableColumn<OrderView, String> customerCol = new JFXTreeTableColumn<>("customer");
        customerCol.setPrefWidth(300);
        customerCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<OrderView, String> param) -> param.getValue().getValue().customer //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        );

        JFXTreeTableColumn<OrderView, String> statusCol = new JFXTreeTableColumn<>("status");
        statusCol.setPrefWidth(300);
        statusCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<OrderView, String> param) -> param.getValue().getValue().status //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        );
        JFXTreeTableColumn<OrderView, String> shippingMethodCol = new JFXTreeTableColumn<>("shipping Method");
        shippingMethodCol.setPrefWidth(300);
        shippingMethodCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<OrderView, String> param) -> param.getValue().getValue().shippingMethod //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        );
        JFXTreeTableColumn<OrderView, String> creationDateCol = new JFXTreeTableColumn<>("creation Date");
        creationDateCol.setPrefWidth(300);
        creationDateCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<OrderView, String> param) -> param.getValue().getValue().creationDate //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        );
        JFXTreeTableColumn<OrderView, String> amountCol = new JFXTreeTableColumn<>("amount ");
        amountCol.setPrefWidth(300);
        amountCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<OrderView, String> param) -> param.getValue().getValue().amount //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        );

        ObservableList<OrderView> orders = FXCollections.observableArrayList();

       
        
        
        // here
        ArrayList<Order> allOrderList = null ;
        try {
            allOrderList = Model.fetch(Order.class).all().execute();
        } catch (ModelException | UnsupportedDataTypeException ex) {
            Logger.getLogger(OrdersTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
          DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        for (Order o : allOrderList){
            try {
                orders.add(new OrderView(
                        String.valueOf(o.getAttr("id")),
                        (String)o.getAttr("reference"),
                        o.customer().getFullName(),
                        o.getOrderStatusName(),
                        o.shippingMethod().getMethodName(),
                        (String) df.format(o.getAttr("creation_date")),
                         o.getOrderTotalWithVAT().toString()
                ));
                
            } catch (ModelException ex) {
                Logger.getLogger(OrdersTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
     
        
        final TreeItem<OrderView> root = new RecursiveTreeItem<OrderView>(orders, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(referenceCol, customerCol, statusCol, shippingMethodCol, creationDateCol, amountCol);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        treeView.setEditable(true);
       
                
                List<Order.OrderStatus> lisFromEnum = Arrays.asList(Order.OrderStatus.values());

        // declare the observable list
        ObservableList<String> list = FXCollections.observableArrayList();
        // adding the status list from thedb to the observable list
        lisFromEnum.forEach((t) -> {

            list.add(t.statusName());       
        });

        statusCol.setCellFactory(ChoiceBoxTreeTableCell.forTreeTableColumn(list));
        statusCol.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<OrderView, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<OrderView, String> event) {
                TreeItem<OrderView> currentEditingOrder = treeView.getTreeItem(event.getTreeTablePosition().getRow());

                // retrieve id of the current editing row 
                String orderId = currentEditingOrder.getValue().id.getValue();


                try {
                    // using the setStatus(String , int) method :!! LOOSE
                    
                    System.out.println("***********************"+event.getNewValue()+orderId);
                    currentEditingOrder.getValue().setStatus(event.getNewValue(), orderId);
                } catch (ModelException ex) {
                    Logger.getLogger(OrdersTableController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedDataTypeException ex) {
                    Logger.getLogger(OrdersTableController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        
        
        inputField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeView.setPredicate(new Predicate<TreeItem<OrderView>>() {
                    @Override
                    public boolean test(TreeItem<OrderView> o) {

                        Boolean flag = o.getValue().reference.getValue().toLowerCase().contains(newValue.toLowerCase())
                                || o.getValue().shippingMethod.getValue().toLowerCase().contains(newValue.toLowerCase())
                                || o.getValue().amount.getValue().toLowerCase().contains(newValue.toLowerCase())
                                || o.getValue().creationDate.getValue().toLowerCase().contains(newValue.toLowerCase())
                                || o.getValue().customer.getValue().toLowerCase().contains(newValue.toLowerCase())
                                || o.getValue().status.getValue().toLowerCase().contains(newValue.toLowerCase());
                        return flag;
                    }
                });
            }
        });
    }

    @FXML
    private void goToMain(ActionEvent event) {
         NavigationService.goTo(event, this, "/allforkids/dashboard/Main.fxml");
    }
}

    class OrderView extends RecursiveTreeObject<OrderView> {
       
    StringProperty id;    
    StringProperty reference;
    StringProperty customer;
    StringProperty status;
    StringProperty shippingMethod;
    StringProperty creationDate;
    StringProperty amount;

    public OrderView(String id, String reference, String customer, String status, String shippingMethod, String creationDate, String amount) {
        this.id =  new SimpleStringProperty(id);
     //   this.id =  id ;
        this.reference = new SimpleStringProperty(reference);
        this.customer = new SimpleStringProperty(customer);
        this.status = new SimpleStringProperty(status);
        this.shippingMethod = new SimpleStringProperty(shippingMethod);
        this.creationDate = new SimpleStringProperty(creationDate);
        this.amount = new SimpleStringProperty(amount);

    }

    @Override
    public String toString() {
        return "OrderView{" + "reference=" + reference + ", customer=" + customer + ", status=" + status + ", shippingMethod=" + shippingMethod + ", creationDate=" + creationDate + ", amount=" + amount + '}';
    }

    public StringProperty getCustomer() {
        return customer;
    }

    public StringProperty getStatus() {
        return status;
    }

    public StringProperty getShippingMethod() {
        return shippingMethod;
    }

    public StringProperty getCreationDate() {
        return creationDate;
    }

    public StringProperty getAmount() {
        return amount;
    }

    public static int getIdOrder(String ref) throws ModelException, UnsupportedDataTypeException, SQLException {

        int id = 0;
        try {
            ArrayList<Order> o = Model.fetch(Order.class).all().where("order_reference","=", ref ).execute();
            System.out.println("-----------------------"+o);

        } catch (UnsupportedDataTypeException ex) {
            Logger.getLogger(OrdersTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return id;

    }
    
    public  StringProperty orderViewID(){
        return id;
    }

    public static void setStatus(String newStatus, String id) throws ModelException, UnsupportedDataTypeException {

        
        System.out.println("new status : "+ newStatus + "--  order id est : "+id);
        
        Order od = Model.find(Order.class, Integer.parseInt(id));
        od.setOrderStatusByName(newStatus);
        od.save();
        
    }
    
}
