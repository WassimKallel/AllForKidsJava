/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import allforkids.blog.BlogMainController;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Wassim
 */
public class ChipController implements Initializable {

    

    @FXML
    private Label tagNameLabel;
    @FXML
    private JFXButton closeBtn;
    
    private Consumer<String> onRemove;
    
    @FXML
    private HBox parentHBox;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
   
    public void setTagName(String tagName, boolean removable) {
        this.tagNameLabel.setText(tagName);
        if(removable == false) {
            this.parentHBox.getStylesheets().add("/helpers/Chip.css");
            this.parentHBox.getStyleClass().removeAll();
            
            this.parentHBox.getStyleClass().add("disabled-chip");
            this.tagNameLabel.getStyleClass().removeAll();
            
            this.tagNameLabel.getStyleClass().add("disabled-chip-label");
            this.parentHBox.getChildren().remove(closeBtn);
        }
    }
    
    public void setTagName(String tagName) {
        this.tagNameLabel.setText(tagName);
    }
    
    public void setOnRemove(Consumer<String> onRemove) {
        this.onRemove = onRemove;
    }
   
    @FXML
    private void remove(ActionEvent event) {
        if(onRemove != null ) {
            onRemove.accept(tagNameLabel.getText());
        }
    }
}
