/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.forum;

import allforkids.forum.models.Post;
import dopsie.exceptions.ModelException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import allforkids.forum.models.Thread;
import allforkids.forum.models.Topic;
import allforkids.userManagement.models.User;
import allforkids.userManagement.models.UserSession;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import dopsie.exceptions.UnsupportedDataTypeException;
import helpers.NavigationService;
import java.util.Date;
import helpers.TrayNotificationService;
import java.sql.Timestamp;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Wassim
 */
public class TopicController implements Initializable {

    @FXML
    private Label mainTitleLabel;
    @FXML
    private ScrollPane allThreadsSP;
    @FXML
    private VBox allThreadsVBox;

    private Topic topic;
    @FXML
    private JFXTextField newThreadTitle;
    @FXML
    private JFXTextArea newPostContent;
    @FXML
    private VBox newThreadSection;
    @FXML
    private JFXButton newThreadBtn;
    @FXML
    private Label notificationLabel;
    @FXML
    private AnchorPane notificationPanel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void setTopic(Topic currentTopic) {
        this.mainTitleLabel.setText(this.mainTitleLabel.getText() + " - " + currentTopic.getAttr("name"));
        this.topic = currentTopic;
        ArrayList<Thread> allThreads = new ArrayList<>();
        try {
            allThreads = currentTopic.threads();
        }
        catch(ModelException e) {
            System.out.println(e.getMessage());
        }
        showThreads(allThreads);
    }

    @FXML
    private void backToForum(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/forum/ForumMain.fxml");
    }
    
    private void showThreads(ArrayList<Thread> threads) {
        try {
            allThreadsVBox.getChildren().removeAll(allThreadsVBox.getChildren());
            if(threads.isEmpty()) {
                Label label = new Label(" No Thread ");
                allThreadsVBox.getChildren().add(label);
            }
            for (Thread thread : threads) {       
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
                Pane newLoadedPane = loader.load(); 
                newLoadedPane.prefWidthProperty().bind(allThreadsVBox.prefWidthProperty());
                CardController controller = loader.<CardController>getController();
                controller.setContent((String)thread.getAttr("title"),  thread.posts().size() + " Posts", thread, (a, b) -> goToThread((ActionEvent) a, (Thread)b, null));
                allThreadsVBox.getChildren().add(newLoadedPane);
            }
        } catch (IOException | ModelException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void goToThread(ActionEvent event ,Thread thread, Post newPost) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/allforkids/forum/Thread.fxml"));
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.hide();
            Pane newLoadedPane = loader.load();
            Scene HomePageScene = new Scene(newLoadedPane);
            ThreadController controller = loader.<ThreadController>getController();
            controller.setThread(thread);
            if(newPost != null) {
                controller.setNewPost(newPost);
            }
            appStage.setScene(HomePageScene);
            appStage.show();            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @FXML
    private void openAddNewThread(ActionEvent event) {
        this.newThreadBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                closeAddNewThread(actionEvent);
            }
        });
        this.newThreadSection.setVisible(true);
        this.newThreadSection.setPrefHeight(Region.USE_COMPUTED_SIZE);
        this.newThreadSection.setMinHeight(Region.USE_COMPUTED_SIZE);
        this.newThreadSection.setMaxHeight(Region.USE_COMPUTED_SIZE);
    }
    
    private void closeAddNewThread(ActionEvent event) {
        this.newThreadBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                openAddNewThread(actionEvent);
            }
        });
        this.newThreadSection.setVisible(true);
        this.newThreadSection.setPrefHeight(0);
        this.newThreadSection.setMinHeight(0);
        this.newThreadSection.setMaxHeight(0);
    }

    @FXML
    private void addThread(ActionEvent event) {
        User currentUser = UserSession.getInstance();
        String threadTitle = this.newThreadTitle.getText();
        String postContent = this.newPostContent.getText();
        if(threadTitle.isEmpty() || postContent.isEmpty()) {
            TrayNotificationService.failureRedNotification("Add Thread", "Thread Title and Post content should not be empty");
            return;
        }
        try {
            Thread thread = new Thread();
            thread.setAttr("title", threadTitle);
            thread.setAttr("topic_id", topic.getAttr("id"));
            thread.save();

            Post post = new Post();

            Timestamp now = new Timestamp(new Date().getTime());
            post.setAttr("content", postContent);
            post.setAttr("thread_id", thread.getAttr("id"));
            post.setAttr("user_id", currentUser.getAttr("id"));
            post.setAttr("creation_date", now);
            post.save();
            TrayNotificationService.successBlueNotification("Add Thread", "Thread added successfully");
            goToThread(event, thread, post);
        } catch(ModelException | UnsupportedDataTypeException e) {
            TrayNotificationService.failureRedNotification("Add Thread", "Couldn't add Thread");
        }
    }
}
