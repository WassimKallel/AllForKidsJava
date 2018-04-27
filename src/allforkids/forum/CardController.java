/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.forum;


import dopsie.core.Model;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Wassim
 */
public class CardController implements Initializable {

    @FXML
    private Label titleLabel;
    @FXML
    private Label subTitleLabel;
    
    private BiConsumer handler;

    private Model entity;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    public void setContent(String title, String subTitle, Model entity, BiConsumer handler) {
        this.titleLabel.setText(title);
        this.subTitleLabel.setText(subTitle);
        this.handler = handler;
        this.entity = entity;
    }

    @FXML
    private void onClick(ActionEvent event) {
        handler.accept(event, this.entity);
    }
    
}
