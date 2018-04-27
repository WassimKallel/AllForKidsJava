/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.forum;


import allforkids.forum.models.Topic;
import dopsie.core.Model;
import dopsie.exceptions.ModelException;
import dopsie.exceptions.UnsupportedDataTypeException;
import helpers.NavigationService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Wassim
 */
public class ForumMainController implements Initializable {

    @FXML
    private ScrollPane allTopicsSP;
    @FXML
    private VBox allTopicsVBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<Topic> allTopics = new ArrayList<>();
        try {
            allTopics = Model.fetch(Topic.class).all().where("online", "1").execute();
        }
        catch(UnsupportedDataTypeException | ModelException e) {
            System.out.println(e.getMessage());
        }
        showAllTopics(allTopics);
    }
    
    public void showAllTopics(ArrayList<Topic> topics) {
        try {
            allTopicsVBox.getChildren().removeAll(allTopicsVBox.getChildren());
            if(topics.isEmpty()) {
                Label label = new Label(" No Topic ");
                allTopicsVBox.getChildren().add(label);
            }
            for (Topic topic : topics) {       
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
                Pane newLoadedPane = loader.load(); 
                newLoadedPane.prefWidthProperty().bind(allTopicsVBox.prefWidthProperty());
                CardController controller = loader.<CardController>getController();
                controller.setContent((String)topic.getAttr("name"), topic.threads().size() + " Threads", topic, (a,b) -> goToTopic((ActionEvent) a, (Topic) b));
                allTopicsVBox.getChildren().add(newLoadedPane);
            }
        } catch (IOException | ModelException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void goToTopic(ActionEvent event, Topic topic) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/allforkids/forum/Topic.fxml"));
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.hide();
            Pane newLoadedPane = loader.load();
            Scene HomePageScene = new Scene(newLoadedPane);
            TopicController controller = loader.<TopicController>getController();
            controller.setTopic(topic);
            appStage.setScene(HomePageScene);
            appStage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void goToMainMenu(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/welcome/Welcome.fxml");
    }
}
