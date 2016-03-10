/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.todomaven.event;

import de.knoplab.todomaven.task.TodoTask;
import java.util.List;
import org.scijava.event.SciJavaEvent;

/**
 *
 * @author tuananh
 */
public class DataDeleteEvent extends SciJavaEvent {

    public TodoTask data;

    public DataDeleteEvent(TodoTask tasks) {
        this.data = tasks;
    }

    public TodoTask getData() {
        return this.data;
    }
}
