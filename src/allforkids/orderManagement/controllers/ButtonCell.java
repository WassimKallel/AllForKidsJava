/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.orderManagement.controllers;

//import allforkids.orderManagement.controllers.ShoppingCartController.ShoppingItem;
import allforkids.orderManagement.models.LineItem;
import com.jfoenix.controls.JFXButton;
import dopsie.core.Model;
import dopsie.exceptions.ModelException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;

/**
 *
 * @author KHOUBEIB
 */
//Define the button cell
public class ButtonCell extends TableCell<LineItem, Boolean> {

    public JFXButton cellButton = new JFXButton("Delete");
   
    ButtonCell(ObservableList<LineItem> thisList, TableView<LineItem> thisTableView) {
        cellButton.setStyle("-fx-font-size:13px;-fx-background-color:RED; -fx-text-fill:white;");
        cellButton.setAlignment(Pos.CENTER);
        //Action when the button is pressed
        cellButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent t) {
                // get Selected Item
                LineItem currentItem = (LineItem) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());

                //remove selected item from the table list
                System.out.println(currentItem);

                thisList.remove(currentItem);

                // remove from DB
                try {
                    LineItem l = Model.find(LineItem.class, (int)currentItem.getAttr("id"));
                    l.delete();
                } catch (ModelException ex) {
                    Logger.getLogger(ButtonCell.class.getName()).log(Level.SEVERE, null, ex);
                }
                thisTableView.refresh();
            }
        });
    }

    //Display button if the row is not empty
    @Override
    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if (!empty) {
            setGraphic(cellButton);
        }
    }
}
