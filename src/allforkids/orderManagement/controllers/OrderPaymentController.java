/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.orderManagement.controllers;

import allforkids.orderManagement.models.Order;
import allforkids.orderManagement.models.Payment;
import allforkids.userManagement.models.User;
import allforkids.userManagement.models.UserSession;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dopsie.core.Model;
import dopsie.exceptions.ModelException;
import dopsie.exceptions.UnsupportedDataTypeException;
import helpers.NavigationService;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.RotateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

/**
 * FXML Controller class
 *
 * @author KHOUBEIB
 */
public class OrderPaymentController implements Initializable {

    @FXML
    private JFXButton checkoutPayment;
    @FXML
    private JFXComboBox<String> comboPaymentMethod;

    /**
     * Initializes the controller class.
     */
    User currentUser = null;
    Order shoppingCart = null;
    @FXML
    private Label totalAmount;
    @FXML
    private ImageView img1;
    @FXML
    private Label lbl1;
    @FXML
    private ImageView img2;
    @FXML
    private Label lbl2;
    @FXML
    private ImageView img3;
    @FXML
    private Label lbl3;
    @FXML
    private ImageView img4;
    @FXML
    private Label lbl4;
    @FXML
    private ImageView img5;
    @FXML
    private Text text1;
    @FXML
    private Text text2;
    @FXML
    private Text text3;
    @FXML
    private Text text4;
    @FXML
    private Text text5;

    private RotateTransition rotateTransition1, rotateTransition2, rotateTransition3,
            rotateTransition4, rotateTransition5;
    @FXML
    private Label lbl_msg;
    @FXML
    private Label banking;
    @FXML
    private JFXButton goToMain;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        try {
//            currentUser = Model.find(User.class, 1);
//            shoppingCart = currentUser.getUserShoppingCart();
//        } catch (ModelException | UnsupportedDataTypeException ex) {
//            Logger.getLogger(ShoppingCartController.class.getName()).log(Level.SEVERE, null, ex);
//        }

        try {
            currentUser = UserSession.getInstance();
            shoppingCart = currentUser.getUserShoppingCart();
        } catch (ModelException | UnsupportedDataTypeException ex) {
            Logger.getLogger(ShoppingCartController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList<String> list = FXCollections.observableArrayList();
        List<Payment.PaymentMethod> paymentMethodList = Arrays.asList(Payment.PaymentMethod.values());
        // adding the status list from thedb to the observable list
        paymentMethodList.forEach((t) -> {

            list.add(t.paymentMethodName());
            System.out.println(t.paymentMethodName());
        });

        comboPaymentMethod.getItems().addAll(list);

        try {
            float fee = shoppingCart.getOrderShippementFee();
            Double ttc = (Double) shoppingCart.getOrderTotalWithVAT();
            Double amountToPay = fee + ttc;
            totalAmount.setText(amountToPay.toString());
        } catch (ModelException ex) {
            Logger.getLogger(OrderPaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void goToMain(ActionEvent event) {
        
        NavigationService.goTo(event, this, "/allforkids/store/welcome/Welcome.fxml");
        
    }

    @FXML
    private void validatePayment(ActionEvent event) throws ModelException, UnsupportedDataTypeException {

        checkoutPayment.setText("Please wait ...");
        checkoutPayment.setDisable(true);
        banking.setVisible(true);
        banking.setDisable(false);

        String absolutePath = System.getProperty("uploads_folder");

        String path = "file:" + absolutePath + "animation/";
        img1.setImage(new Image(path + "syn.png"));
        text1.setText("Order Verification");
        rotateTransition1 = new RotateTransition(Duration.seconds(5), img1);
        rotateTransition2 = new RotateTransition(Duration.seconds(5), img2);
        rotateTransition3 = new RotateTransition(Duration.seconds(5), img3);
        rotateTransition4 = new RotateTransition(Duration.seconds(5), img4);
        rotateTransition5 = new RotateTransition(Duration.seconds(5), img5);
        RotateTransition transition[] = {rotateTransition1, rotateTransition2,
            rotateTransition3, rotateTransition4, rotateTransition5};
        for (RotateTransition rTransition : transition) {
            rTransition.setCycleCount(1);
            rTransition.setAutoReverse(false);
            rTransition.setFromAngle(720);
            rTransition.setToAngle(0);
        }
        rotateTransition1.play();
        rotateTransition1.setOnFinished((e) -> {
            img1.setImage(new Image(path + "ok.png"));
            lbl1.setStyle("-fx-background-color:#45A563");
            img2.setImage(new Image(path + "syn.png"));
            text2.setText("Order Confirmation");
            rotateTransition2.play();
        });

        rotateTransition2.setOnFinished((e) -> {
            img2.setImage(new Image(path + "ok.png"));
            lbl2.setStyle("-fx-background-color:#45A563");
            img3.setImage(new Image(path + "syn.png"));
            text3.setText("Payment Verification");
            rotateTransition3.play();
        });
        rotateTransition3.setOnFinished((e) -> {
            img3.setImage(new Image(path + "ok.png"));
            lbl3.setStyle("-fx-background-color:#45A563");
            img4.setImage(new Image(path + "syn.png"));
            text4.setText("Payment Success");
            rotateTransition4.play();
        });
        rotateTransition4.setOnFinished((e) -> {
            img4.setImage(new Image(path + "ok.png"));
            lbl4.setStyle("-fx-background-color:#45A563");
            img5.setImage(new Image(path + "syn.png"));
            text5.setText("Preparing Shippement");
            rotateTransition5.play();
        });
        rotateTransition5.setOnFinished((e) -> {
            img5.setImage(new Image(path + "ok.png"));
            lbl_msg.setVisible(true);

            
            
            Task<Void> sleeper = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                    }
                    return null;
                }
            };

            sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    lbl_msg.setText("Your Order Number is : " + (String) shoppingCart.getAttr("reference"));
                }
            });
            new Thread(sleeper).start();

        });
        
        shoppingCart.setOrderStatusByName("Payment Completed");
        shoppingCart.save();
        
        

    }

    @FXML
    private void selectPaymentMethod(ActionEvent event) throws ModelException, UnsupportedDataTypeException {
        String method = comboPaymentMethod.getSelectionModel().getSelectedItem();
        if (!method.isEmpty()) {
            shoppingCart.setOrderPaymentMethodByName(method);
        }

    }
}