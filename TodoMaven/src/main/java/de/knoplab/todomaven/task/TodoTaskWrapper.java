/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.todomaven.task;

import javafx.beans.property.Property;
import javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;

/**
 *
 * @author tuananh
 */
public class TodoTaskWrapper implements TodoTask{
    private TodoTask task;
    private Property<String> nameProperty;
    private Property<Boolean> stateProperty;

    public TodoTaskWrapper(TodoTask task) {

        this.task = task;

        // the BeanPropertyBuilder creates a property that calls the getters and setters of the task object;
        // In other words, each time the property is changed, it will called setDone or getIsDone from
        // the wrapped task.
        try {
            this.stateProperty = new JavaBeanObjectPropertyBuilder<>().bean(this.task).name("state").build();
            this.nameProperty = new JavaBeanStringPropertyBuilder().bean(this.task).name("name").build();
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

        this.nameProperty.setValue(name);
    }

    @Override
    public boolean getState() {
        return task.getState();
    }

    @Override
    public void setState(boolean state) {
        this.stateProperty.setValue(state);
    }
    public Property<String> nameProperty() {
        return nameProperty;
    }

    public Property<Boolean> stateProperty() {
        return stateProperty;
    }    
}
