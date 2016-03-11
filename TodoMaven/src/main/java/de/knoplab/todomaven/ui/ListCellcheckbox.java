/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.todomaven.ui;

import de.knoplab.todomaven.event.TodoTaskModifiedEvent;
import de.knoplab.todomaven.task.TodoTaskWrapper;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import org.scijava.event.EventHandler;

/**
 *
 * @author tuananh
 */
class ListCellcheckbox extends ListCell<TodoTaskWrapper> {

    Label label = new Label();

    CheckBox checkbox = new CheckBox();

    HBox box = new HBox(checkbox, label);

    public ListCellcheckbox() {
        box.getStyleClass().add("list-cell");
        itemProperty().addListener(this::onItemChanged);
    }
 

    public void onItemChanged(Observable obs, TodoTaskWrapper oldValue, TodoTaskWrapper newValue) {
        if (oldValue != null) {
            checkbox.selectedProperty().unbindBidirectional(oldValue.stateProperty());
        }

        if (newValue == null) {
            setGraphic(null);
        } else {

            label.textProperty().setValue(newValue.getName());

            checkbox.selectedProperty().bindBidirectional(newValue.stateProperty());
            setGraphic(box);

        }
    }

    @EventHandler
    public void onModifiedTaskEvent(TodoTaskModifiedEvent event) {
        Platform.runLater(() -> {
            checkbox.setSelected(event.getData().getState());
        });

    }
}
