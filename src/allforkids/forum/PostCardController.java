/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.forum;

import allforkids.forum.models.Post;
import allforkids.forum.models.Report;
import allforkids.forum.models.Vote;
import allforkids.forum.models.VoteType;
import allforkids.userManagement.models.User;
import allforkids.userManagement.models.UserSession;
import com.jfoenix.controls.JFXButton;
import dopsie.exceptions.ModelException;
import dopsie.exceptions.UnsupportedDataTypeException;
import helpers.TrayNotificationService;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.ocpsoft.prettytime.PrettyTime;

/**
 * FXML Controller class
 *
 * @author Wassim
 */
public class PostCardController implements Initializable {

    @FXML
    private Label userAvatarNameLabel;
    @FXML
    private Text postContent;
    @FXML
    private Label postMetaLabel;
    @FXML
    private Label voteCounter;
    
    private Post post;
    @FXML
    private JFXButton upArrowBtn;
    @FXML
    private JFXButton downArrowBtn;
    
    private long voteScore;
    @FXML
    private AnchorPane avatarContainer;
    @FXML
    private Label roleLabel;
    @FXML
    private JFXButton reportBtn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
    public void setPost(Post post) {
        try {
            this.post = post;
            Date creationDate = new Date(((Date) post.getAttr("creation_date")).getTime());
            PrettyTime p = new PrettyTime();
            this.postMetaLabel.setText(p.format(creationDate));
            this.userAvatarNameLabel.setText((String) post.author().getFullName());
            this.roleLabel.setText((String) post.author().getRole().name().toLowerCase());
            this.avatarContainer.getChildren()
                                .add(
                                        post.author().getAvatarViewPane(
                                                avatarContainer.getPrefWidth(), 
                                                avatarContainer.getPrefHeight()
                                        )
                                );
            
            if((Integer)post.getAttr("reported") != null && (Integer)post.getAttr("reported") == 1) {
                this.postContent.setText("The content of this message was reported");
                this.postContent.setOpacity(0.7);
                this.upArrowBtn.setDisable(true);
                this.downArrowBtn.setDisable(true);
                this.reportBtn.setDisable(true);
            } else {
                this.postContent.setText((String) post.getAttr("content"));
            }
            this.voteScore = post.voteScore();
            updateVoteScore();
            User currentUser = UserSession.getInstance();
            if(post.userVoted(currentUser)){
                this.upArrowBtn.setDisable(true);
                this.downArrowBtn.setDisable(true);
                this.reportBtn.setDisable(true);
            }

            if(post.userReported(currentUser)) {
                reportBtn.setDisable(true);
            }
            
        } catch (ModelException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void updateVoteScore() {
        this.voteCounter.setText(this.voteScore + "");
        this.voteCounter.getStyleClass().removeAll(this.voteCounter.getStyleClass());
        if(this.voteScore > 0) {
            this.voteCounter.getStyleClass().add("green-vote");
        } else if (this.voteScore < 0) {
            this.voteCounter.getStyleClass().add("red-vote");
        } else {
            this.voteCounter.getStyleClass().add("gray-vote");
        }
    }
    @FXML
    private void upVote(ActionEvent event) {
        try {
            vote(VoteType.UP);
            this.voteScore++;
            updateVoteScore();
            TrayNotificationService.faceBlueNotification("Upvoted", "");
        } catch(Exception e) {
            TrayNotificationService.failureRedNotification("Upvote", "Failed to Upvote");
        }
        
    }

    @FXML
    private void downVote(ActionEvent event) {
        try {
            vote(VoteType.DOWN);
            this.voteScore--;
            updateVoteScore();
            TrayNotificationService.faceBlueNotification("Downvoted", "");
        } catch(Exception e) {
            TrayNotificationService.failureRedNotification("Downvote", "Failed to Downvote");
        }
    }
    
    public void vote(VoteType voteType) throws ModelException, UnsupportedDataTypeException {
        this.upArrowBtn.setDisable(true);
        this.downArrowBtn.setDisable(true);
        Vote vote = new Vote();
        vote.setAttr("user_id", 1);
        vote.setAttr("post_id", this.post.getAttr("id"));
        vote.setVoteType(voteType);
        vote.save();
    }

    @FXML
    private void reportClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Report");
        alert.setHeaderText(null);
        alert.setContentText("Do you confirm ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                Report report = new Report();
                User currentUser = UserSession.getInstance();
                report.setAttr("user_id", currentUser.getAttr("id"));
                report.setAttr("post_id", this.post.getAttr("id"));
                report.save();
                this.reportBtn.setDisable(true);
                TrayNotificationService.successBlueNotification("Reporting Post", "Post reported successfully");
            } catch (ModelException | UnsupportedDataTypeException ex) {
                TrayNotificationService.failureRedNotification("Reporting Post", "Reporting post failed");
            }
        }
    }
}
