/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Wassim
 */
public class CustomImageViewPane extends Pane {

    public CustomImageViewPane(String url, double paneWidth, double paneHeight) {

        Image image = new Image(url);
        double width = image.getWidth();
        double height = image.getHeight();
        ImageView imageView = null;
        double normalRatio = paneWidth / paneHeight;
        double ratio = width / height;

        if (ratio > normalRatio) {
            imageView = new ImageView(new Image(url, (ratio / normalRatio) * paneWidth, paneHeight, false, true));
        } else {
            imageView = new ImageView(new Image(url, paneWidth, (normalRatio / ratio) * paneHeight, false, true));
        }

        Rectangle rec = new Rectangle(paneWidth, paneHeight);

        rec.relocate(0, 0);

        imageView.setClip(rec);

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);

        WritableImage wim = null;
        wim = imageView.snapshot(parameters, wim);

        // new imageview
        ImageView clippedView = new ImageView(wim);

        getChildren().addAll(clippedView);

    }
}
