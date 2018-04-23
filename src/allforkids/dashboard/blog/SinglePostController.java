/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.dashboard.blog;

import helpers.ChipController;
import allforkids.blog.models.Post;
import allforkids.blog.models.PostTag;
import allforkids.blog.models.Tag;
import allforkids.userManagement.models.User;
import allforkids.userManagement.models.UserSession;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import dopsie.core.Model;
import dopsie.exceptions.ModelException;
import dopsie.exceptions.UnsupportedDataTypeException;
import helpers.HtmlEditorWithImage;
import helpers.NavigationService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Wassim
 */
public class SinglePostController implements Initializable {

    private JFXTextArea contentTextArea;
    @FXML
    private JFXTextField titleTextField;
    @FXML
    private JFXButton AddTagButton;
    @FXML
    private Pane tagsPane;
    @FXML
    private JFXTextField newTagTF;

    private HashMap<String, Pane> tags;
    @FXML
    private AnchorPane addPostPane;
    @FXML
    private AnchorPane htmlEditorAP;

    private HtmlEditorWithImage htmlEditor;
    @FXML
    private ScrollPane htmlEditorSP;
    @FXML
    private JFXButton chooseImBtn;

    private String newPicPath;

    private Post post;
    @FXML
    private JFXButton validateBtn;
    
    private ArrayList<Tag> postTags;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.tags = new HashMap<String, Pane>();

    }

    public void setStage(Stage stage) {
        this.htmlEditor = new HtmlEditorWithImage(stage, 515, 1008);
        this.htmlEditorAP.getChildren().add(htmlEditor);
        this.htmlEditor.prefWidthProperty().bind(htmlEditorAP.prefWidthProperty());

    }

    @FXML
    private void validatePost(ActionEvent event) {
        try {
            String title = titleTextField.getText();
            String content = this.htmlEditor.getHtmlText();
            ArrayList<Tag> allPostTags = new ArrayList<>();
            ArrayList<Tag> tagFromDB;
            for (String tagName : this.tags.keySet()) {
                try {
                    System.out.println(tagName);
                    tagFromDB = Model.fetch(Tag.class)
                            .all()
                            .where("name", tagName)
                            .execute();
                    if (tagFromDB.isEmpty()) {
                        Tag newTag = new Tag();
                        newTag.setAttr("name", tagName);
                        newTag.save();
                        allPostTags.add(newTag);
                    } else {
                        allPostTags.add(tagFromDB.get(0));
                    }
                } catch (ModelException | UnsupportedDataTypeException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            
            User currentUser = UserSession.getInstance();
            if(this.post == null) {
                this.post = new Post();
                Timestamp now = new Timestamp(new Date().getTime());
                post.setAttr("user_id", currentUser.getAttr("id"));
                post.setAttr("creation_date", now);
            }
            
            post.setAttr("title", title);
            post.setAttr("content", content);
            
            if (newPicPath != null) {
                File source = new File(this.newPicPath);
                String outputFilePath = "uploads/blog/" + (new Date()).getTime();
                File dest = new File(System.getProperty("uploads_folder") + outputFilePath);
                copyFileUsingStream(source, dest);
                post.setAttr("image_path", outputFilePath);
            }
            
            post.save();


            ArrayList<PostTag> relations = post.postTagsRelation();
            
            for(PostTag relation: relations) {
                relation.delete();
            }
            
            for (Tag tag : allPostTags) {
                PostTag postTag = new PostTag();
                postTag.setAttr("post_id", post.getAttr("id"));
                postTag.setAttr("tag_id", tag.getAttr("id"));
                postTag.save();
            }
            
            

            goBack(event);
        } catch (ModelException | UnsupportedDataTypeException | IOException ex) {
            Logger.getLogger(SinglePostController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/dashboard/blog/PostsList.fxml");
    }

    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }
    
    public void addChip(String name) {
        newTagTF.getScene().getStylesheets().add("/helpers/Chip.css");
        if (!name.isEmpty() && !this.tags.containsKey(name)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/helpers/Chip.fxml"));
                Pane newLoadedPane = loader.load();
                ChipController controller = loader.<ChipController>getController();
                controller.setTagName(name);
                controller.setOnRemove( a -> removeTag(a));
                tagsPane.getChildren().add(newLoadedPane);
                this.tags.put(name, newLoadedPane);
                this.newTagTF.clear();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    @FXML
    private void addTag(ActionEvent event) {
        String tagName = newTagTF.getText();
        this.addChip(tagName);
    }

    public void removeTag(String tagName) {
        Pane chip = this.tags.get(tagName);
        this.tags.remove(tagName);
        this.tagsPane.getChildren().remove(chip);
    }

    @FXML
    private void TagTfKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            this.addTag(null);
        }
    }

    @FXML
    private void chooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image");

        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(appStage);
        if (file != null) {
            this.newPicPath = file.getAbsolutePath();
            this.chooseImBtn.getStyleClass().add("file-choosed");
            this.chooseImBtn.setText("Image Attached");
            this.chooseImBtn.setDisable(true);
        }
    }

    void setPost(Post selectedPost) {
        this.validateBtn.setText("Update Post");
        this.post = selectedPost;
        String postTitle = (String) post.getAttr("title");
        String postContent = (String) post.getAttr("content");
        ArrayList<Tag> tags = new ArrayList<>();
        try {
            tags = post.tags();
        } catch (ModelException ex) {
            Logger.getLogger(SinglePostController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(Tag tag: tags) {
            addChip((String)tag.getAttr("name"));
        }
        this.titleTextField.setText(postTitle);
        this.htmlEditor.setHtmlText(postContent);
    }

}
