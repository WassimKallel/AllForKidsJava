package allforkids;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Wassim
 */
public class AllForKids extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        Parent root = null;
        try { 
            primaryStage.setResizable(false);
            root = FXMLLoader.load(getClass().getResource("/allforkids/login/Login.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("All For Kids");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println(ex.getMessage());
            
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("host", "pma.allforkids.ml");
        System.setProperty("port", "3306");
        System.setProperty("database", "from_scratch");
        System.setProperty("user", "wassim");
        System.setProperty("password", "Lpd*de7W");
        System.setProperty("uploads_folder", "/Users/wattouma/Documents/www/AllForKidsWeb/data/");
        launch(args);
    }
    
}
