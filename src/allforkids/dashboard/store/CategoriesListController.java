/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.dashboard.store;

import allforkids.store.models.Category;
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
public class CategoriesListController implements Initializable {

    @FXML
    private TableView<Category> categoriesTableView;
    @FXML
    private TableColumn<Category, String> nameCol;
    
    private ObservableList obs;

    private ArrayList<Category> categories = new ArrayList<>();
    @FXML
    private JFXTextField newCategoryTF;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoriesTableView.setEditable(true);
        nameCol.setEditable(true);
        
        try {
            categories = Model.fetch(Category.class).all().where("active", "1").execute();
        } catch (ModelException | UnsupportedDataTypeException ex) {
            Logger.getLogger(ProductsListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        obs = FXCollections.observableArrayList(categories);
        
        
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        nameCol.setCellValueFactory(new DopsieCellBuilder(p -> {
            return (String)((Category) p).getAttr("name");
        }));
        
        categoriesTableView.setItems(obs);
    }    

    @FXML
    private void backToProducts(ActionEvent event) {
        NavigationService.goTo(event, this, "ProductsList.fxml");
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
                Category selectedCategory = (Category) categoriesTableView.getSelectionModel().getSelectedItem();
                selectedCategory.setAttr("active", "0");
                selectedCategory.save();
                categories.remove(selectedCategory);
                categoriesTableView.getItems().remove(selectedCategory);
                TrayNotificationService.successBlueNotification("Category deleted!", "Category deleted!");
            } catch (ModelException | UnsupportedDataTypeException ex) {
                TrayNotificationService.failureRedNotification("Category delete failed!", "Category deleted failed!");
            }
        }
        
    }

    @FXML
    private void addCategory(ActionEvent event) {
        String categoryName = newCategoryTF.getText();
        if (categoryName.isEmpty()) {
            TrayNotificationService.failureRedNotification("Add Category", "You should specify the category name");
            return;
        }
        Category newCategory = new Category();
        newCategory.setAttr("name", categoryName);
        try {
            newCategory.save();
            categories.add(newCategory);
            categoriesTableView.getItems().add(newCategory);
            newCategoryTF.clear();
            TrayNotificationService.successBlueNotification("Add Category", "Category added successfully");
        } catch (ModelException | UnsupportedDataTypeException ex) {
            TrayNotificationService.failureRedNotification("Add Category", "Failed to add Category");
        }
    }
    
     @FXML
    private void inputModified(TableColumn.CellEditEvent<Category, String> event) {
        Category category = event.getRowValue();
        String newData = event.getNewValue();
        category.setAttr("name", newData);
        try {
            category.save();
            TrayNotificationService.successBlueNotification("Category updated!", "Category updated!");
        } catch (ModelException | UnsupportedDataTypeException ex) {
            TrayNotificationService.failureRedNotification("Category not updated!", "Category not updated!");
        }
    }
    
}
