/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab;

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
                CheckBox b = new CheckBox();
                b.setSelected(task.getState());
                System.out.println("list" + task.getState());
                setGraphic(b);
                Label l = new Label(task.getName());
                layout.getChildren().addAll(b,l);
                
                setGraphic(layout);
            }
            else
            {
                setGraphic(null);
            }
        }
       
    }
