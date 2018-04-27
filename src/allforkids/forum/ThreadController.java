/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.forum;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import allforkids.forum.models.Thread;
import allforkids.forum.models.Post;
import allforkids.forum.models.Topic;
import com.jfoenix.controls.JFXButton;
import dopsie.exceptions.ModelException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Wassim
 */
public class ThreadController implements Initializable {

    @FXML
    private Label mainTitleLabel;
    
    private Thread thread;
    @FXML
    private ScrollPane allPostSP;
    @FXML
    private VBox allPostVBox;
    @FXML
    private JFXButton backBtn;
    
    private ArrayList<Post> posts = new ArrayList<>();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setThread(Thread currentThread) {

        mainTitleLabel.setText((String)currentThread.getAttr("title"));

        this.thread = currentThread;
        try {
            this.posts = currentThread.posts();
        }
        catch(ModelException e) {
            System.out.println(e.getMessage());
        }
        showPosts(this.posts);
        try {
            Topic currentTopic = currentThread.topic();
            this.backBtn.setText("Back to " + (String)currentTopic.getAttr("name"));
        } catch (ModelException ex) {
            Logger.getLogger(ThreadController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void showPosts(ArrayList<Post> posts) {
        try {
            allPostVBox.getChildren().removeAll(allPostVBox.getChildren());
            if(posts.isEmpty()) {
                Label label = new Label(" No Post ");
                allPostVBox.getChildren().add(label);
            }
            for (Post post : posts) {       
                FXMLLoader loader = new FXMLLoader(getClass().getResource("PostCard.fxml"));
                Pane newLoadedPane = loader.load(); 
                newLoadedPane.prefWidthProperty().bind(allPostVBox.prefWidthProperty());
                PostCardController controller = loader.<PostCardController>getController();
                controller.setPost(post);
                allPostVBox.getChildren().add(newLoadedPane);
            }
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPost.fxml"));
            Pane newLoadedPane = loader.load(); 
            newLoadedPane.prefWidthProperty().bind(allPostVBox.prefWidthProperty());
            AddPostController controller = loader.<AddPostController>getController();
            controller.setThread(thread);
            controller.setCallback(a -> postAdded((Post) a));
            allPostVBox.getChildren().add(newLoadedPane);
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void postAdded(Post post) {
        this.posts.add(post);
        showPosts(this.posts);
    }
    
    @FXML
    private void backToTopic(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/allforkids/forum/Topic.fxml"));
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.hide();
            Pane newLoadedPane = loader.load();
            Scene HomePageScene = new Scene(newLoadedPane);
            TopicController controller = loader.<TopicController>getController();
            controller.setTopic(this.thread.topic());
            appStage.setScene(HomePageScene);
            appStage.show();
        } catch (IOException | ModelException ex) {
            System.out.println(ex.getMessage());
        }
    }

    void setNewPost(Post newPost) {
        this.posts.add(newPost);
        showPosts(posts);
    }
    
}
