/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.blog;

import allforkids.blog.models.Comment;
import allforkids.userManagement.models.User;
import dopsie.exceptions.ModelException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Wassim
 */
public class CommentController implements Initializable {

    @FXML
    private Label commentContent;
    @FXML
    private Label commentedByLabel;
    @FXML
    private AnchorPane avatarContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void setCommentData(Comment comment) {
        try {
            this.commentContent.setText((String)comment.getAttr("content"));
            User commentor = comment.commentor();
            String commentorName = (String)commentor.getAttr("first_name");
            this.commentedByLabel.setText("Comment by " + commentorName);
            this.avatarContainer.getChildren()
                                .add(
                                        commentor.getAvatarViewPane(
                                                avatarContainer.getPrefWidth(), 
                                                avatarContainer.getPrefHeight()
                                        )
                                );
            
        } catch (ModelException ex) {
            Logger.getLogger(CommentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
