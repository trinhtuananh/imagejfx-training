/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.todomaven.ui;

import de.knoplab.todomaven.task.TodoTask;
import javafx.event.EventType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author tuananh
 */
class ListCellcheckbox extends ListCell<TodoTask> {
        @Override
        public void updateItem(TodoTask task, boolean empty) {
            super.updateItem(task, empty);
            if (task != null) {
                CheckBox box = new CheckBox(task.getName());
                
                box.setOnAction(e -> task.setState(!task.getState()));
                box.setSelected(task.getState());

                setGraphic(box);
            }
            else
            {
                setGraphic(null);
            }
        }
       
    }
