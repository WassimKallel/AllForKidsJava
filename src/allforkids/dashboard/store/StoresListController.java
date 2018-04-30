/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.dashboard.store;

import allforkids.store.models.Category;
import allforkids.store.models.Store;
import com.jfoenix.controls.JFXTextField;
import dopsie.core.Model;
import dopsie.exceptions.ModelException;
import dopsie.exceptions.UnsupportedDataTypeException;
import helpers.DopsieCellBuilder;
import helpers.NavigationService;
import helpers.TrayNotificationService;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;

/**
 * FXML Controller class
 *
 * @author Amine
 */
public class StoresListController implements Initializable {

    @FXML
    private JFXTextField newStoreNameTf;
    @FXML
    private JFXTextField latTf;
    @FXML
    private JFXTextField longTF;
    @FXML
    private TableView<Store> storesTableView;
    @FXML
    private TableColumn<Store, String> nameCol;
    @FXML
    private TableColumn<Store, String> latCol;
    @FXML
    private TableColumn<Store, String> lonCol;
    
    private ArrayList<Store> stores = new ArrayList<>();
    
    private ObservableList obs;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        storesTableView.setEditable(true);
        nameCol.setEditable(true);
        nameCol.setEditable(true);
        nameCol.setEditable(true);
        
        try {
            stores = Model.fetch(Store.class).all().where("active", "1").execute();
        } catch (ModelException | UnsupportedDataTypeException ex) {
            Logger.getLogger(ProductsListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        obs = FXCollections.observableArrayList(stores);
        
        
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        nameCol.setCellValueFactory(new DopsieCellBuilder(p -> {
            return (String)((Store) p).getAttr("name");
        }));
        
        latCol.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        latCol.setCellValueFactory(new DopsieCellBuilder(p -> {
            return (Double)((Store) p).getAttr("lat") + "";
        }));
        
        lonCol.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        lonCol.setCellValueFactory(new DopsieCellBuilder(p -> {
            return (Double)((Store) p).getAttr("lon") + "";
        }));
        
        
        storesTableView.setItems(obs);
    }    

    @FXML
    private void backToProducts(ActionEvent event) {
        NavigationService.goTo(event, this, "ProductsList.fxml");
    }

    @FXML
    private void addStore(ActionEvent event) {
        String storeName = newStoreNameTf.getText();
        String storeLat = latTf.getText();
        String storeLon = longTF.getText();
        
        if (storeName.isEmpty() || storeLat.isEmpty() || storeLon.isEmpty()) {
            TrayNotificationService.failureRedNotification("Add Store", "You should specify the store name / longitude / latitude");
            return;
        }
        
        try{
            Double storeLatDouble = Double.parseDouble(storeLat);
            Double storeLonDouble = Double.parseDouble(storeLon);

            Store newStore = new Store();
            newStore.setAttr("name", storeName);
            newStore.setAttr("lat", storeLatDouble);
            newStore.setAttr("lon", storeLonDouble);
    
            newStore.save();
            
            stores.add(newStore);
            storesTableView.getItems().add(newStore);
            newStoreNameTf.clear();
            longTF.clear();
            latTf.clear();
            TrayNotificationService.successBlueNotification("Add Store", "Store added successfully");
        } catch (ModelException | UnsupportedDataTypeException | NumberFormatException ex) {
            TrayNotificationService.failureRedNotification("Add Store", "Failed to add Store");
        }
    }

    @FXML
    private void deleteClicked(ActionEvent event) {
        Store selectedStore = (Store) storesTableView.getSelectionModel().getSelectedItem();
        if(selectedStore == null) {
            TrayNotificationService.failureRedNotification("Warning", "You should select a Store to delete");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText(null);
        alert.setContentText("Do you confirm ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                selectedStore.setAttr("active", "0");
                selectedStore.save();
                stores.remove(selectedStore);
                storesTableView.getItems().remove(selectedStore);
                TrayNotificationService.successBlueNotification("Store deleted!", "Store deleted!");
            } catch (ModelException | UnsupportedDataTypeException ex) {
                TrayNotificationService.failureRedNotification("Store delete failed!", "Store deleted failed!");
            }
        }
    }

    @FXML
    private void inputModified(TableColumn.CellEditEvent<Store, String> event) {
        Store store = event.getRowValue();
        String newData = event.getNewValue();
        String colName = event.getTableColumn().getId();
        System.out.println(colName);
        if(colName.equals("nameCol")) {
            store.setAttr("name", newData);
        } else if(colName.equals("latCol")) {
            store.setAttr("lat", newData);
        } else if(colName.equals("lonCol")) {
            store.setAttr("lon", newData);
        } 
        try {
            store.save();
            TrayNotificationService.successBlueNotification("Store updated!", "Store updated!");
        } catch (ModelException | UnsupportedDataTypeException ex) {
            TrayNotificationService.failureRedNotification("Store not updated!", "Store not updated!");
        }
    }
    
}
