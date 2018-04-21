/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.userManagement.profile;

import allforkids.userManagement.models.User;
import allforkids.userManagement.models.UserSession;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dopsie.exceptions.ModelException;
import dopsie.exceptions.UnsupportedDataTypeException;
import helpers.BCrypt;
import helpers.CustomImageViewPane;
import helpers.NavigationService;
import helpers.TrayNotificationService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Bechir
 */
public class EditProfileController implements Initializable {

    @FXML
    private JFXTextField firstNameTF;
    @FXML
    private JFXTextField lastNameTF;
    @FXML
    private JFXTextField usernameTF;
    @FXML
    private JFXTextField mailTF;
    @FXML
    private JFXPasswordField passwordTF;
    @FXML
    private AnchorPane avatarImContainer;

    private String newPicPath = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        User user = UserSession.getInstance();
        firstNameTF.setText((String) user.getAttr("first_name"));
        lastNameTF.setText((String) user.getAttr("last_name"));
        usernameTF.setText((String) user.getAttr("username"));
        usernameTF.setDisable(true);
        mailTF.setText((String) user.getAttr("email"));
        String imagePath = (String) user.getAttr("avatar_path");

        if (imagePath == null) {
            imagePath = "/img/default-user.png";
        } else {
            String absolutePath =  System.getProperty("uploads_folder");
            imagePath = "file:" + absolutePath + imagePath;
        }
        setPic(imagePath);
    }

    public void setPic(String path) {
        double containerWidth = avatarImContainer.getPrefWidth();
        double containerHeight = avatarImContainer.getPrefHeight();

        CustomImageViewPane avatarImView
                = new CustomImageViewPane(path,
                        containerWidth,
                        containerHeight);
        avatarImContainer.getChildren().removeAll(avatarImContainer.getChildren());

        avatarImContainer.getChildren().add(avatarImView);

    }

    @FXML
    private void onEdit(ActionEvent event) {
        User currentUser = UserSession.getInstance();

        String username = usernameTF.getText();
        String password = passwordTF.getText();
        String firstName = firstNameTF.getText();
        String lastName = lastNameTF.getText();
        String email = mailTF.getText();

        if (username.isEmpty()
                || firstName.isEmpty()
                || lastName.isEmpty()
                || email.isEmpty()) {
            TrayNotificationService.failureRedNotification("All fields required", "All fields required");
        } else {
            try {
                currentUser.setAttr("first_name", firstName);
                currentUser.setAttr("last_name", lastName);
                currentUser.setAttr("username", username);
                currentUser.setAttr("email", email);
                if (!password.isEmpty()) {
                    currentUser.setAttr("password", BCrypt.hashpw(password, BCrypt.gensalt()));
                }
                if (this.newPicPath != null) {
                    File source = new File(this.newPicPath);
                    String outputFilePath = "uploads/avatars/" + username;
                    File dest = new File(System.getProperty("uploads_folder") + outputFilePath);
                    copyFileUsingStream(source, dest);
                    currentUser.setAttr("avatar_path", outputFilePath);
                }
                currentUser.save();
                TrayNotificationService.successBlueNotification("Profile", "Profile Updated");
            } catch (ModelException | UnsupportedDataTypeException | IOException ex) {
                System.out.println(ex.getMessage());
                TrayNotificationService.failureRedNotification("Profile", "Could not update profile");
            }
        }
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

    @FXML
    private void goToWelcome(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/welcome/Welcome.fxml");
    }

    private String getFileExtension(File file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @FXML
    private void uploadPic(MouseEvent event) {
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
            setPic("file:" + this.newPicPath);
        }
    }

    public static String getCurrentAbsolutePath() {
        return Paths.get("").toAbsolutePath().toString();
    }

}
