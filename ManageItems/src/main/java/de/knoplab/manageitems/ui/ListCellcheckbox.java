/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.manageitems.ui;

import de.knoplab.manageitems.Item;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Box;

/**
 *
 * @author tuananh
 */
class ListCellcheckbox extends ListCell<Item> {


    CheckBox checkbox = new CheckBox();

    HBox box = new HBox(checkbox);
    
    public ListCellcheckbox() {
        box.getStyleClass().add("list-cell");
    }
 

@Override
        public void updateItem(Item item, boolean empty) {
            if (item != null) {
                checkbox.setText(item.getName()+" ("+item.getNumber()+")");
                checkbox.setOnAction(e -> item.setState(!item.getState()));
                checkbox.setSelected(item.getState());

                setGraphic(box);
            }
            else
            {
                setGraphic(null);
            }
        }

}
