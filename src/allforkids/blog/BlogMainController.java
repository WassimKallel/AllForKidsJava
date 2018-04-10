/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.blog;

import allforkids.blog.models.Post;
import allforkids.blog.models.Tag;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dopsie.core.Model;
import dopsie.exceptions.ModelException;
import dopsie.exceptions.UnsupportedDataTypeException;
import helpers.ChipController;
import helpers.NavigationService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 * FXML Controller class
 *
 * @author Wassim
 */
public class BlogMainController implements Initializable {

    @FXML
    private FlowPane allPostsFlowPane;
    @FXML
    private ScrollPane allPostsScrollPane;
    @FXML
    private HBox filterHBox;
    @FXML
    private JFXButton filterButton;
    @FXML
    private JFXTextField tagTF;
    @FXML
    private HBox tagsHBox;
    @FXML
    private JFXTextField searchTF;

    private HashMap<String, Pane> tags;
    @FXML
    private JFXButton AddTagButton;
    @FXML
    private JFXButton FilterButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.tags = new HashMap<>();
        try {
            allPostsFlowPane.prefWidthProperty().bind(allPostsScrollPane.prefWidthProperty().add(45));
            allPostsScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);

            ArrayList<Post> allPosts = Model.fetch(Post.class).all().where("online", "1").orderBy("creation_date", "DESC").execute();
            showPosts(allPosts);
        } catch (UnsupportedDataTypeException | ModelException ex) {
            Logger.getLogger(BlogMainController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

        
    private void showPosts(ArrayList<Post> posts)  {
        try {
            allPostsFlowPane.getChildren().removeAll(allPostsFlowPane.getChildren());
            if(posts.isEmpty()) {
                Label label = new Label(" No Post ");
                allPostsFlowPane.getChildren().add(label);
            }
            for (Post post : posts) {       
                FXMLLoader loader = new FXMLLoader(getClass().getResource("PostCard.fxml"));
                Pane newLoadedPane = loader.load();
                newLoadedPane.prefWidthProperty().bind(allPostsFlowPane.prefWidthProperty());
                PostCardController controller = loader.<PostCardController>getController();
                controller.setPostData(post);
                allPostsFlowPane.getChildren().add(newLoadedPane);
            }
        } catch (IOException | ModelException ex) {
            Logger.getLogger(BlogMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void openFilter(ActionEvent event) {
        this.filterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                closeFilter(actionEvent);
            }
        });
        this.filterHBox.setVisible(true);
        this.filterHBox.setPrefHeight(Region.USE_COMPUTED_SIZE);
        this.filterHBox.setMinHeight(Region.USE_COMPUTED_SIZE);
        this.filterHBox.setMaxHeight(Region.USE_COMPUTED_SIZE);
    }
    
    private void closeFilter(ActionEvent event) {
        this.filterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                openFilter(actionEvent);
            }
        });
        this.filterHBox.setVisible(false);
        this.filterHBox.setPrefHeight(0);
        this.filterHBox.setMinHeight(0);
        this.filterHBox.setMaxHeight(0);
    }


    @FXML
    private void addTag(ActionEvent event) {
        tagTF.getScene().getStylesheets().add("/helpers/Chip.css");
        String tagName = tagTF.getText();
        if (!tagName.isEmpty() && !this.tags.containsKey(tagName)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/helpers/Chip.fxml"));
                Pane newLoadedPane = loader.load();
                ChipController controller = loader.<ChipController>getController();
                controller.setTagName(tagName);
                controller.setOnRemove(a -> removeTag(a));
                this.tagsHBox.getChildren().add(newLoadedPane);
                this.tags.put(tagName, newLoadedPane);
                this.tagTF.clear();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @FXML
    private void search(ActionEvent event) {
        try {
            String searchString = this.searchTF.getText();
            ArrayList<Post> posts = Model.fetch(Post.class)
                                            .all()
                                            .where("title", "LIKE", "%" + searchString + "%")
                                            .orderBy("creation_date", "DESC")
                                            .execute();
            showPosts(posts);
            
        } catch (ModelException | UnsupportedDataTypeException ex) {
            Logger.getLogger(BlogMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void removeTag(String tagName) {
        Pane chip = this.tags.get(tagName);
        this.tags.remove(tagName);
        this.tagsHBox.getChildren().remove(chip);
    }

    @FXML
    private void reset(ActionEvent event) {
        this.searchTF.clear();
        this.tagTF.clear();
        this.tagsHBox.getChildren().removeAll(this.tagsHBox.getChildren());
        initialize(null, null);
    }

    @FXML
    private void filter(ActionEvent event) {
        Set<String> tags = this.tags.keySet();
        HashSet<Post> posts = new HashSet<>();
        for(String tagName: tags) {
            try {
                ArrayList<Tag> tagByName = Model.fetch(Tag.class).all().where("name", tagName).execute();
                if(!tagByName.isEmpty()) {
                    Tag tag = tagByName.get(0);
                    for(Post post: tag.posts()) {
                        posts.add(post);
                    }
                }
            } catch (ModelException | UnsupportedDataTypeException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        ArrayList<Post> postsToShow = new ArrayList<>(posts);
        this.showPosts(postsToShow);
    }

    @FXML
    private void TagTfKeyReleased(KeyEvent event) {
    }

    @FXML
    private void goToMainMenu(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/welcome/Welcome.fxml");
    }
}