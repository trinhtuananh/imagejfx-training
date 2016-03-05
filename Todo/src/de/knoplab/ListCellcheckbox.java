/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab;

import javafx.event.EventType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author tuananh
 */
class ListCellcheckbox extends ListCell<OneTask> {
        @Override
        public void updateItem(OneTask task, boolean empty) {
            super.updateItem(task, empty);
            if (task != null) {
                FlowPane layout = new FlowPane();
                CheckBox box = new CheckBox();
                
                box.setOnAction(e -> task.setState(!task.getState()));
                box.setSelected(task.getState());

                setGraphic(box);
                Label l = new Label(task.getName());
                layout.getChildren().addAll(box,l);
                
                setGraphic(layout);
            }
            else
            {
                setGraphic(null);
            }
        }
       
    }
