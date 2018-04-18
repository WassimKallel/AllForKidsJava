/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.store;

import allforkids.orderManagement.models.Order;
import allforkids.store.models.Product;
import allforkids.userManagement.models.User;
import allforkids.userManagement.models.UserSession;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import dopsie.exceptions.ModelException;
import dopsie.exceptions.UnsupportedDataTypeException;
import helpers.CustomImageViewPane;
import helpers.NavigationService;
import helpers.TrayNotificationService;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author Amine
 */
public class ProductDetailsController implements Initializable {

    @FXML
    private AnchorPane avatarContainer;
    @FXML
    private FontAwesomeIconView cartIcon;
    @FXML
    private Label cartLabel;
    @FXML
    private AnchorPane imageContainer;
    @FXML
    private WebView longDescription;
    @FXML
    private Label productNameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private FontAwesomeIconView checkIcon;
    @FXML
    private Label referenceLabel;
    @FXML
    private Text shortDescription;
    @FXML
    private Spinner<Integer> quantitySpinner;

    private Product product;
    @FXML
    private Label totalLabel;

    String cartLabelText = null;

    User u = UserSession.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Cart Button
        cartIcon.setIcon(FontAwesomeIcon.SHOPPING_CART);

        // set cartLabel
        setcartLabelText();

        checkIcon.setIcon(FontAwesomeIcon.CHECK_CIRCLE_ALT);

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1);

        quantitySpinner.setValueFactory(valueFactory);

        quantitySpinner.valueProperty()
                .addListener(
                        (ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
                            this.quantityUpdated(newValue);

                        }
                );
    }

    public void setcartLabelText() {
        try {
            cartLabelText = "Cart (" + u.getUserShoppingCart().getNumberOfLineItem() + ")";
        } catch (ModelException ex) {
            Logger.getLogger(ProductDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedDataTypeException ex) {
            Logger.getLogger(ProductDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cartLabel.setText(cartLabelText);
    }

    public void setProduct(Product product) {
        this.product = product;
        Double price = (Double) product.getAttr("unit_price");
        this.priceLabel.setText(price.toString() + " HT");
        this.totalLabel.setText(price.toString() + " HT");
        this.productNameLabel.setText((String) product.getAttr("name"));
        this.referenceLabel.setText((String) product.getAttr("reference"));
        String imagePath = (String) product.getAttr("image");
        if (imagePath != null) {
            System.out.println(imagePath);
            String absolutePath = System.getProperty("uploads_folder");
            imagePath = "file:" + absolutePath + imagePath;
        } else {
            imagePath = "/img/default-product.png";   
        }
        double imageWidth = imageContainer.getPrefWidth();
        double imageHeight = imageContainer.getPrefHeight();
        imageContainer.getChildren().add(new CustomImageViewPane(imagePath, imageWidth, imageHeight));
        
        this.shortDescription.setText((String) product.getAttr("short_description"));
        final WebEngine webEngine = this.longDescription.getEngine();
        webEngine.loadContent((String) product.getAttr("description") + "<style> body{ background-color: #f4f4f4}</style>");

    }

    @FXML
    private void goToCart(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/orderManagement/views/shoppingCart.fxml");

    }

    @FXML
    private void goToStore(ActionEvent event) {
        NavigationService.goTo(event, this, "/allforkids/store/ProductsList.fxml");
    }

    @FXML
    private void addToCart(ActionEvent event) throws ModelException, UnsupportedDataTypeException {
        boolean newItem = false;
        String notif = "Item added successfully";
        Order o = u.getUserShoppingCart();
        newItem = o.addItemToShoppingCart(product, quantitySpinner.getValue());
        if (!newItem) {
            notif = "Quantity updated successfully";
        }
        TrayNotificationService.successBlueNotification((String) product.getAttr("short_description"), notif);
        setcartLabelText();

    }

    private void quantityUpdated(Integer quantity) {
        this.totalLabel.setText(((Double) this.product.getAttr("unit_price") * quantity) + " HT");
    }
}
