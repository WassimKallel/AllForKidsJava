/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.blog;

import allforkids.blog.models.Comment;
import allforkids.blog.models.Post;
import allforkids.blog.models.Tag;
import allforkids.userManagement.models.User;
import allforkids.userManagement.models.UserSession;
import com.jfoenix.controls.JFXTextField;
import java.sql.Timestamp;
import dopsie.exceptions.ModelException;
import dopsie.exceptions.UnsupportedDataTypeException;
import helpers.ChipController;
import helpers.NavigationService;
import helpers.TrayNotificationService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.ocpsoft.prettytime.PrettyTime;
import org.w3c.dom.Document;

/**
 * FXML Controller class
 *
 * @author Wassim
 */
public class PostDetailsController implements Initializable {

    @FXML
    private Label titleLabel;
    @FXML
    private Label postedByLabel;
    @FXML
    private VBox commentsSection;

    private Label noCommentLabel = null;
    @FXML
    private JFXTextField commentBodyTf;

    Post post;
    @FXML
    private WebView contentWebView;
    @FXML
    private HBox tagsHBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setPostData(Post post) {
        try {
            this.post = post;
            this.titleLabel.setText((String) post.getAttr("title"));

            final WebEngine webEngine = this.contentWebView.getEngine();
            webEngine.loadContent((String) post.getAttr("content") + "<style>body {background-color: rgb(244, 244, 244);} </style>");
            webEngine.documentProperty().addListener(new ChangeListener<Document>() {
                @Override 
                public void changed(ObservableValue<? extends Document> prop, Document oldDoc, Document newDoc) {
                  String heightText = contentWebView.getEngine().executeScript(
                    "window.getComputedStyle(document.body, null).getPropertyValue('height')"
                  ).toString();
                  double height = Double.valueOf(heightText.replace("px", ""));    

                  System.out.println(height);
                  contentWebView.setMinHeight(height + 50);
                }
              });
            Date creationDate = new Date(((Date) post.getAttr("creation_date")).getTime());
            User author = post.author();
            String authorName = (String) author.getAttr("first_name");

            PrettyTime p = new PrettyTime();

            this.postedByLabel.setText("Posted by " + authorName + " " + p.format(creationDate));

            ArrayList<Comment> allComments = post.comments();
            if (allComments.isEmpty()) {
                this.noCommentLabel = new Label("Still no comment for this post! Be the first to comment");
                this.noCommentLabel.getStyleClass().add("no-comment");
                commentsSection.getChildren().add(this.noCommentLabel);
            } else {
                for (Comment comment : allComments) {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Comment.fxml"));
                    Pane newLoadedPane = loader.load();
                    newLoadedPane.prefWidthProperty().bind(commentsSection.prefWidthProperty());
                    CommentController controller = loader.<CommentController>getController();
                    controller.setCommentData(comment);
                    commentsSection.getChildren().add(newLoadedPane);
                }
            }
            ArrayList<Tag> postTags = post.tags();
            for(Tag tag: postTags) {
                addTag((String)tag.getAttr("name"));
            }
            
        } catch (ModelException | IOException ex) {
            Logger.getLogger(PostDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addTag(String tagName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/helpers/Chip.fxml"));
            Pane newLoadedPane = loader.load();
            ChipController controller = loader.<ChipController>getController();
            controller.setTagName(tagName, false);
            tagsHBox.getChildren().add(newLoadedPane);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    @FXML
    private void goBack(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/blog/BlogMain.fxml");
    }

    @FXML
    private void addComment(ActionEvent event) throws ModelException, UnsupportedDataTypeException {
        String commentText = this.commentBodyTf.getText();
        if (!commentText.isEmpty()) {
            try {
                int id = (int) this.post.getAttr("id");
                Timestamp now = new Timestamp(new Date().getTime());
                User currentUser = UserSession.getInstance();
                Comment comment = new Comment();
                comment.setAttr("post_id", id);
                comment.setAttr("content", commentText);
                comment.setAttr("user_id", currentUser.getAttr("id"));
                comment.setAttr("creation_date", now);
                comment.save();
                this.commentsSection.getChildren().remove(this.noCommentLabel);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("Comment.fxml"));
                Pane newLoadedPane = loader.load();
                newLoadedPane.prefWidthProperty().bind(commentsSection.prefWidthProperty());
                CommentController controller = loader.<CommentController>getController();
                controller.setCommentData(comment);
                commentsSection.getChildren().add(newLoadedPane);
                TrayNotificationService.successBlueNotification("Comment", "Commented on post successfully");
            } catch (IOException | ModelException | UnsupportedDataTypeException ex) {
                TrayNotificationService.failureRedNotification("Comment", "Failed to comment");
            }
        }
        this.commentBodyTf.clear();
    }
}
