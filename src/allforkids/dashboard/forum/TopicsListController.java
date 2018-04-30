/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.dashboard.forum;

import allforkids.forum.models.Topic;
import com.jfoenix.controls.JFXButton;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Wassim
 */
public class TopicsListController implements Initializable {

    @FXML
    private JFXTextField newTopicTf;
    @FXML
    private TableView<Topic> topicsTableView;
    @FXML
    private TableColumn<Topic, String> topicsCol;

    ArrayList<Topic> topics = new ArrayList<>();

    ObservableList obs = null;
    @FXML
    private JFXButton moderationBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        topicsTableView.setEditable(true);
        topicsCol.setEditable(false);

        try {
            topics = Model.fetch(Topic.class).all().where("online", "1").execute();
        } catch (ModelException | UnsupportedDataTypeException ex) {
            TrayNotificationService.failureRedNotification("Topics", "Could not fetch topics");
        }

        obs = FXCollections.observableArrayList(topics);

        topicsCol.setCellValueFactory(new DopsieCellBuilder(p -> ((Topic) p).getAttr("name")));

        topicsTableView.setItems(obs);
    }

    @FXML
    private void goToMainMenu(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/dashboard/Main.fxml");
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
                Topic selectedTopic = (Topic) topicsTableView.getSelectionModel().getSelectedItem();
                selectedTopic.setAttr("online", 0);
                selectedTopic.save();
                topics.remove(selectedTopic);
                topicsTableView.getItems().remove(selectedTopic);
                TrayNotificationService.successBlueNotification("Topic deleted!", "Topic deleted!");
            } catch (ModelException | UnsupportedDataTypeException ex) {
                TrayNotificationService.failureRedNotification("Delete Topic", "Could not delete Topic");
            }
        }
    }

    @FXML
    private void add(ActionEvent event) {
        String topicName = newTopicTf.getText();
        if (topicName.isEmpty()) {
            TrayNotificationService.failureRedNotification("Add Topic", "You should specify the topic name");
            return;
        }
        Topic newTopic = new Topic();
        newTopic.setAttr("name", topicName);
        try {
            newTopic.save();
            topics.add(newTopic);
            topicsTableView.getItems().add(newTopic);
            newTopicTf.clear();
            TrayNotificationService.successBlueNotification("Add Topic", "Topic added successfully");
        } catch (ModelException | UnsupportedDataTypeException ex) {
            TrayNotificationService.failureRedNotification("Add Topic", "Failed to add topic");
        }
    }

    @FXML
    private void goToModerator(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/dashboard/forum/ModerationSection.fxml");
    }
    @FXML
    private void topicNameModified(TableColumn.CellEditEvent<Topic, String> event) {
        Topic topic = event.getRowValue();
        String newData = event.getNewValue();
        if (!newData.isEmpty()) {
            TrayNotificationService.successBlueNotification("Updating topic", "Topic name should not be blank");
        }
        topic.setAttr("name", newData);
        try {
            topic.save();
            TrayNotificationService.successBlueNotification("Topic updated!", "Topic updated!");
        } catch (ModelException | UnsupportedDataTypeException ex) {
            TrayNotificationService.failureRedNotification("Topic not updated!", "Topic not updated!");
        }

    }

}
