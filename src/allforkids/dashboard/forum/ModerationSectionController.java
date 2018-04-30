/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.dashboard.forum;

import allforkids.forum.models.Post;
import allforkids.forum.models.Report;
import allforkids.userManagement.models.User;
import dopsie.core.Model;
import dopsie.exceptions.ModelException;
import dopsie.exceptions.UnsupportedDataTypeException;
import helpers.DopsieCellBuilder;
import helpers.MailingService;
import helpers.NavigationService;
import helpers.TrayNotificationService;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Wassim
 */
public class ModerationSectionController implements Initializable {

    @FXML
    private TableColumn<Report, String> userCol;
    @FXML
    private TableColumn<Report, String> postCol;
    @FXML
    private TableView<?> reportsTableView;
    
    ArrayList<Report> reports = new ArrayList<>();

    ObservableList obs = null;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reportsTableView.setEditable(false);

        try {
            reports = Model.fetch(Report.class).all().where("treated", "0").execute();
            System.out.println(reports);
        } catch (ModelException | UnsupportedDataTypeException ex) {
            TrayNotificationService.failureRedNotification("Reports", "Could not fetch reports");
        }

        obs = FXCollections.observableArrayList(reports);

        userCol.setCellValueFactory(new DopsieCellBuilder(p -> {
            try {
                return ((Report) p).post().author().getFullName();
            } catch (ModelException ex) {
                Logger.getLogger(ModerationSectionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }));
        postCol.setCellValueFactory(new DopsieCellBuilder(p -> {
            try {
                return (String) ((Report) p).post().getAttr("content");
            } catch (ModelException ex) {
                Logger.getLogger(ModerationSectionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }));
        reportsTableView.setItems(obs);
       
    }    

    @FXML
    private void goBack(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/dashboard/forum/TopicsList.fxml");
    }

    @FXML
    private void banClicked(ActionEvent event) {
        try {
            Report selectedReport = (Report) reportsTableView.getSelectionModel().getSelectedItem();
            Post postToHide = selectedReport.post();
            User userToBan =  postToHide.author();
            
            userToBan.setAttr("active", 0);
            postToHide.setAttr("reported", 1);
            
            userToBan.save();
            postToHide.save();
            
            String userMail = (String)userToBan.getAttr("email");
            if(userMail != null) {
                MailingService.send(userMail, 
                    "You've been banned", 
                    "You've been banned due to posting inappropriate content on allForKids Forum");
            }
            
            postReportsProcessed(postToHide);
            TrayNotificationService.successBlueNotification("Topic deleted!", "Topic deleted!");
        } catch (ModelException | UnsupportedDataTypeException ex) {
            TrayNotificationService.failureRedNotification("Delete Topic", "Could not delete Topic");
        }
    }
    
    private void postReportsProcessed(Post reportedPost) {
        try {
            ArrayList<Report> reportsToRemove = new ArrayList<>();
            for(Report report: reports) {
                if(report.getAttr("post_id") == reportedPost.getAttr("id")) {
                    report.setAttr("treated", 1);
                    report.save();

                    reportsToRemove.add(report);
                }
            }
            for (Report report: reportsToRemove) {
                reports.remove(report);
                reportsTableView.getItems().remove(report);
            }
        } catch (ModelException | UnsupportedDataTypeException ex) {
            
        }
    }

    @FXML
    private void unreportClicked(ActionEvent event) {
        try {
            Report selectedReport = (Report) reportsTableView.getSelectionModel().getSelectedItem();
            Post post = selectedReport.post();
            postReportsProcessed(post);
        } catch (ModelException ex) {
            Logger.getLogger(ModerationSectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
