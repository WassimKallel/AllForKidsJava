/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.dashboard.stats;

import allforkids.blog.models.Comment;
import allforkids.forum.models.Post;
import allforkids.forum.models.Thread;
import allforkids.orderManagement.models.Order;
import allforkids.store.models.Product;
import allforkids.userManagement.models.User;
import dopsie.core.Model;
import dopsie.exceptions.ModelException;
import dopsie.exceptions.UnsupportedDataTypeException;
import helpers.NavigationService;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Wassim
 */
public class StatsController implements Initializable {

    @FXML
    private Label user;
    @FXML
    private Label usersLabel;
    @FXML
    private Label productsLabel;
    @FXML
    private Label ordersLabel;
    @FXML
    private Label incomeLabel;
    @FXML
    private Label blogPostsLabel;
    @FXML
    private Label commentsLabel;
    @FXML
    private Label postsLabel;
    @FXML
    private Label threadsLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            int numberOfUsers = Model.fetch(User.class).all().where("active", "1").execute().size();
            this.usersLabel.setText(numberOfUsers + " User");
        } catch (ModelException | UnsupportedDataTypeException ex) {
            Logger.getLogger(StatsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            int numberOfProducts = Model.fetch(Product.class).all().where("active", "1").execute().size();
            this.productsLabel.setText(numberOfProducts + " Product");
        } catch (ModelException | UnsupportedDataTypeException ex) {
            Logger.getLogger(StatsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            int numberOfOrders = Model.fetch(Order.class).all().execute().size();
            this.ordersLabel.setText(numberOfOrders + " Order");
        } catch (ModelException | UnsupportedDataTypeException ex) {
            Logger.getLogger(StatsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            int numberOfBlogPosts = Model.fetch(allforkids.blog.models.Post.class).all().where("online", "1").execute().size();
            this.blogPostsLabel.setText(numberOfBlogPosts + " Post");
        } catch (ModelException | UnsupportedDataTypeException ex) {
            Logger.getLogger(StatsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            int numberOfBlogComments = Model.fetch(Comment.class).all().execute().size();
            this.commentsLabel.setText(numberOfBlogComments + " Comment");
        } catch (ModelException | UnsupportedDataTypeException ex) {
            Logger.getLogger(StatsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            int numberOfForumThreads = Model.fetch(Thread.class).all().execute().size();
            this.threadsLabel.setText(numberOfForumThreads + " Thread");
        } catch (ModelException | UnsupportedDataTypeException ex) {
            Logger.getLogger(StatsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            int numberOfForumPosts = Model.fetch(Post.class).all().execute().size();
            this.postsLabel.setText(numberOfForumPosts + " Post");
        } catch (ModelException | UnsupportedDataTypeException ex) {
            Logger.getLogger(StatsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void goToMainMenu(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/dashboard/Main.fxml");
    }
    
}
