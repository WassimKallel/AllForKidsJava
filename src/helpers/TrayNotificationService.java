/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 *
 * @author Wassim
 */
public class TrayNotificationService {

    public static void successBlueNotification(String title, String message) {
        TrayNotification tray = new TrayNotification();
        tray.setNotificationType(NotificationType.CUSTOM);
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setAnimationType(AnimationType.POPUP);
        tray.showAndDismiss(Duration.millis(1000));
        tray.setRectangleFill(Color.valueOf("#4183D7"));
        tray.setImage(new Image("/img/icons8_Ok_96px.png"));
    }
    
    public static void failureRedNotification(String title, String message) {
        TrayNotification tray = new TrayNotification();
        tray.setNotificationType(NotificationType.CUSTOM);
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setAnimationType(AnimationType.POPUP);
        tray.showAndDismiss(Duration.millis(1000));
        tray.setRectangleFill(Color.valueOf("#e2040b"));
        tray.setImage(new Image("/img/icon-fail.png"));
    }

    public static void faceBlueNotification(String title, String message) {
        TrayNotification tray = new TrayNotification();
        tray.setNotificationType(NotificationType.CUSTOM);
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setAnimationType(AnimationType.FADE);
        tray.showAndDismiss(Duration.millis(1500));
        tray.setRectangleFill(Color.valueOf("#4183D7"));
        tray.setImage(new Image("/img/icons8_Male_User_100px_2.png"));
    }
}
