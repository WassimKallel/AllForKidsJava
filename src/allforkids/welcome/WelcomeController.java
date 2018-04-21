/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.welcome;

import allforkids.userManagement.models.Role;
import allforkids.userManagement.models.User;
import allforkids.userManagement.models.UserSession;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import helpers.NavigationService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Wassim
 */
public class WelcomeController implements Initializable {

    @FXML
    private FontAwesomeIconView blogIcon;
    @FXML
    private FontAwesomeIconView forumIcon;
    @FXML
    private FontAwesomeIconView storeIcon;
    @FXML
    private JFXButton goToDashboardBtn;
    @FXML
    private JFXButton userFullNameBtn;
    @FXML
    private AnchorPane avatarContainer;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        blogIcon.setIcon(FontAwesomeIcon.ALIGN_LEFT);
        forumIcon.setIcon(FontAwesomeIcon.FORUMBEE);
        storeIcon.setIcon(FontAwesomeIcon.SHOPPING_CART);
        User currentUser = UserSession.getInstance();
        Role role = currentUser.getRole();
        this.userFullNameBtn.setText(currentUser.getFullName());
        if(role != Role.USER) {
            goToDashboardBtn.setText("You're " + role.name().toLowerCase() + ", go to dashboard");
        } else {
            goToDashboardBtn.setVisible(false);
        }
        avatarContainer.getChildren()
            .add(currentUser.getAvatarViewPane(40, 40));
    }    


    @FXML
    private void goToBlog(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/blog/BlogMain.fxml");
    }


    @FXML
    private void gotToDashboard(ActionEvent event) throws IOException {
        NavigationService.goTo(event, this, "/allforkids/dashboard/Main.fxml");
    }

    @FXML
    private void goToForum(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/forum/ForumMain.fxml");
    }

    @FXML
    private void goToStore(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/store/ProductsList.fxml");
    }

    @FXML
    private void goToProfile(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/userManagement/profile/EditProfile.fxml");
    }

    @FXML
    private void logout(ActionEvent event) {
        UserSession.setInstance(null);
        NavigationService.goTo(event, this, "/allforkids/login/Login.fxml");
    }

    
}
