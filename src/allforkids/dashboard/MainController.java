/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.dashboard;

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
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Wassim
 */
public class MainController implements Initializable {

    private Label label1;
    @FXML
    private FontAwesomeIconView usersIcon;
    @FXML
    private Label usersLabel;
    @FXML
    private FontAwesomeIconView blogIcon;
    @FXML
    private Label blogLabel;
    @FXML
    private FontAwesomeIconView forumIcon;
    @FXML
    private Label forumLabel;
    @FXML
    private JFXButton usersMenu;
    @FXML
    private JFXButton blogMenu;
    @FXML
    private JFXButton forumMenu;
    @FXML
    private JFXButton productsMenu;
    @FXML
    private FontAwesomeIconView productsIcon;
    @FXML
    private Label productsLabel;
    @FXML
    private JFXButton OrdersMenu;
    @FXML
    private FontAwesomeIconView ordersIcon;
    @FXML
    private Label ordersLabel;
    @FXML
    private JFXButton StatisticsMenu;
    @FXML
    private FontAwesomeIconView statisticsIcon;
    @FXML
    private Label statisticsLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       usersIcon.setIcon(FontAwesomeIcon.USERS);
       usersLabel.setText("Users");
       
       blogIcon.setIcon(FontAwesomeIcon.TH);
       blogLabel.setText("Posts");
       
       forumIcon.setIcon(FontAwesomeIcon.ALIGN_JUSTIFY);
       forumLabel.setText("Forum");
       
       productsIcon.setIcon(FontAwesomeIcon.CUBE);
       productsLabel.setText("Products");
       
       ordersIcon.setIcon(FontAwesomeIcon.SHOPPING_CART);
       ordersLabel.setText("Orders");
       
       statisticsIcon.setIcon(FontAwesomeIcon.PIE_CHART);
       statisticsLabel.setText("Statistics");
       
       User currentUser = UserSession.getInstance();
       
       if(currentUser.getRole() == Role.ANALYST) {
           usersMenu.setDisable(true);
           blogMenu.setDisable(true);
           forumMenu.setDisable(true);
           productsMenu.setDisable(true);
           OrdersMenu.setDisable(true);
       } 
       if(currentUser.getRole() == Role.COMMUNITY_MANAGER) {
            usersMenu.setDisable(true);
            productsMenu.setDisable(true);
            OrdersMenu.setDisable(true);
            StatisticsMenu.setDisable(true);
       }
       
    }    

    @FXML
    private void userMenuClicked(ActionEvent event) throws IOException {
        NavigationService.goTo(event, this, "/allforkids/dashboard/userManagement/UsersList.fxml");
    }

    @FXML
    private void goToWelcome(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/welcome/Welcome.fxml");
    }

    @FXML
    private void blogMenuClicked(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/dashboard/blog/PostsList.fxml");
    }

    @FXML
    private void forumMenuClicked(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/dashboard/forum/TopicsList.fxml");
    }

    @FXML
    private void goToProducts(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/dashboard/store/ProductsList.fxml");
    }

    @FXML
    private void goToOrderManagement(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/orderManagement/views/ordersTable.fxml");
    }

    @FXML
    private void goToStats(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/dashboard/stats/Stats.fxml");
    }
}
