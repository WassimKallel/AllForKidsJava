/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.orderManagement.controllers;

import java.util.function.BiConsumer;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;

/**
 *
 * @author KHOUBEIB
 */
public class SpinnerTableCell<S, T extends Integer> extends TableCell<S, T> {

    private final Spinner<T> spinner;

    public SpinnerTableCell(int stock, BiConsumer<S, T> update, int step) {
        this.spinner = new Spinner<>(0, 20, 1);
        this.spinner.setEditable(false);
//
        spinner.setOnScroll(e -> {
            if (e.getDeltaY() > 0) {
                spinner.increment(1);
                System.out.println("increment");
            } else {
                spinner.decrement(1);
                System.out.println("decrement");
            }

        });
        spinner.setOnMouseClicked(e -> {
            String eventTarget = e.getTarget().toString();
            S item = getTableView().getItems().get(getIndex());
            if(eventTarget.contains("increment-arrow-button")) {
                System.out.println("increment");
                update.accept(item, (T) new Integer(1));
            } else {
                System.out.println("decrement");
                update.accept(item, (T) new Integer(-1));
                System.out.println(new Integer(-1));
            }
        });

    }

    @Override
    protected void updateItem(T c, boolean empty) {
        super.updateItem(c, empty);

        if (empty || c == null) {
            setText(null);
            setGraphic(null);
            return;
        }

        this.spinner.getValueFactory().setValue(c);
        System.out.println(c);

        setGraphic(spinner);
    }

}
