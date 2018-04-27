/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.forum;

import allforkids.forum.models.Post;
import allforkids.forum.models.Thread;
import allforkids.userManagement.models.User;
import allforkids.userManagement.models.UserSession;
import com.jfoenix.controls.JFXTextArea;
import helpers.TrayNotificationService;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Wassim
 */
public class AddPostController implements Initializable {

    @FXML
    private Label userAvatarNameLabel;
    @FXML
    private JFXTextArea newPostContentTA;

    private Thread thread;
    
    private Consumer addPostCallback;
    @FXML
    private AnchorPane avatarContainer;
    @FXML
    private Label roleLabel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        User currentUser = UserSession.getInstance();
        this.avatarContainer.getChildren()
                                .add(
                                        currentUser.getAvatarViewPane(
                                                avatarContainer.getPrefWidth(), 
                                                avatarContainer.getPrefHeight()
                                        )
                                );
        this.roleLabel.setText((String) currentUser.getRole().name().toLowerCase());
        userAvatarNameLabel.setText(currentUser.getFullName());
    }    
    public void setCallback(Consumer callback) {
        this.addPostCallback = callback;
    }
    public void setThread(Thread thread) {
        this.thread = thread;
    }
    
    
    @FXML
    private void addPost(ActionEvent event) {
        if(newPostContentTA.getText().isEmpty()) {
            TrayNotificationService.failureRedNotification("Adding a post", "Post content should not be empty");
            return;
        }
        try{
            User currentUser = UserSession.getInstance();
            Post post = new Post();
            Timestamp now = new Timestamp(new Date().getTime());
            post.setAttr("content", newPostContentTA.getText());
            post.setAttr("user_id", currentUser.getAttr("id"));
            post.setAttr("creation_date", now);
            post.setAttr("thread_id", thread.getAttr("id"));
            post.save();
            TrayNotificationService.faceBlueNotification("Adding a post", "Post added successfully");
            this.addPostCallback.accept(post);
        } catch(Exception e) {
            TrayNotificationService.failureRedNotification("Adding a post", "Failed to add post");
            System.out.println(e.getMessage());
        }
    }

    
}
