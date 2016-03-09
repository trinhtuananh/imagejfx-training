/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.todomaven.ui;

import de.knoplab.todomaven.task.TodoTask;
import javafx.beans.property.Property;
import javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder;

/**
 *
 * @author tuananh
 */
public class ViewModel implements TodoTask {
    private final TodoTask task;
    private Property<Boolean> doneProperty;

    public ViewModel(TodoTask task){
        this.task = task;
        try {
            this.doneProperty = new JavaBeanObjectPropertyBuilder<>().bean(task).name("done").build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getName() {
        return task.getName();
    }

    @Override
    public void setName(String name) {
        this.task.setName(name);
    }

    @Override
    public boolean getState() {
        return this.task.getState();
    }

    @Override
    public void setState(boolean state) {
        this.task.setState(state);
    }
    
  

    public Property<Boolean> doneProperty() {
        return doneProperty;
    }
    
}
