/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.manageitems.ui;

import de.knoplab.manageitems.Item;
import javafx.beans.Observable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

/**
 *
 * @author tuananh
 */
class ListCellcheckbox extends ListCell<Item> {

    CheckBox checkbox = new CheckBox();

    HBox box = new HBox(checkbox);

    public ListCellcheckbox() {
        box.getStyleClass().add("list-cell");
        itemProperty().addListener(this::onItemChanged);
    }

    public void onItemChanged(Observable obs, Item oldValue, Item newValue) {

        if (newValue == null) {
            setGraphic(null);
        } else {
            setGraphic(box);
            checkbox.textProperty().setValue(newValue.getName() + " (" + newValue.getNumber() + ")");
            checkbox.setOnAction(e -> {
                newValue.setState(!newValue.getState());
                WidgetUI.setPredicate();
            });
            checkbox.setSelected(newValue.getState());
        }
    }

}
