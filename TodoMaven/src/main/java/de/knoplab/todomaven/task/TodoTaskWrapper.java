package de.knoplab.todomaven.task;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import de.knoplab.todomaven.event.TodoTaskModifiedEvent;
import javafx.beans.property.Property;
import javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;
import org.scijava.event.EventService;

/**
 *
 * @author cyril
 */
public class TodoTaskWrapper implements TodoTask {
    private final TodoTask task;

    private Property<String> nameProperty;
    private Property<Boolean> stateProperty;
    private EventService eventService;
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
    
    public TodoTaskWrapper(TodoTask task, boolean b, String s)
    {
        this.task=task;
    }

    @Override
    public String getName() {
        return task.getName();
    }

    @Override
    public boolean getState() {
        return task.getState();
    }

    @Override
    public void setState(boolean isDone) {
        // When setting the property, it will non only 
        // change the wrapped task, but it will
        // also automatically notify all the listeners
        // of the "state" property.
        stateProperty.setValue(isDone);
        System.out.println("from wrapper "+stateProperty);

    }

    @Override
    public void setName(String name) {
        // same as before
        nameProperty.setValue(name);
    }
    @JsonProperty("task")
    public TodoTask getTask() {
        return task;
    }

    public Property<String> nameProperty() {
        return nameProperty;
    }

    public Property<Boolean> stateProperty() {
        return stateProperty;
    }


}
