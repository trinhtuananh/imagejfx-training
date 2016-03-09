/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.todomaven.event;

import de.knoplab.todomaven.task.DefaultTodoTask;
import de.knoplab.todomaven.task.TodoTask;
import de.knoplab.todomaven.ui.ViewModel;
import org.scijava.event.SciJavaEvent;

/**
 *
 * @author tuananh
 */
public class DataAddedEvent extends SciJavaEvent{
    public final ViewModel data;
    public DataAddedEvent(ViewModel task) {
        this.data  = task;
    }
    public ViewModel getData() {
        return this.data;
    }
}