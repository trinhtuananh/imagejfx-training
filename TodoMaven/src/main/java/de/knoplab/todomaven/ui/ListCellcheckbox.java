/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.todomaven.ui;

import de.knoplab.todomaven.task.TodoTask;
import de.knoplab.todomaven.task.TodoTaskWrapper;
import javafx.beans.Observable;
import javafx.event.EventType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author tuananh
 */
class ListCellcheckbox extends ListCell<TodoTaskWrapper> {

        // label displaying the text of the task
        Label label = new Label();

        // checkbox displaying the status of the task (done/ not done)
        CheckBox checkbox = new CheckBox();

        // hbox containing everything
        HBox box = new HBox(checkbox, label);

        public ListCellcheckbox() {
            box.getStyleClass().add("list-cell");

            // 
            itemProperty().addListener(this::onItemChanged);
        }

        public void onItemChanged(Observable obs, TodoTaskWrapper oldValue, TodoTaskWrapper newValue) {

            // 
            // a same cell can be used for different Wrapper, must make sure to desactivate previous bidirectional binding
            if (oldValue != null) {
                checkbox.selectedProperty().unbindBidirectional(oldValue.stateProperty());
            }

            if (newValue == null) {
                setGraphic(null);
            } else {

                setGraphic(box);

                // we don't really listen to the text
                label.textProperty().setValue(newValue.getName());

                // it allows the checkbox to react to any change of wrapper and vis versa
                checkbox.selectedProperty().bindBidirectional(newValue.stateProperty());
                System.out.println(newValue.getName());

            }
        }
    }