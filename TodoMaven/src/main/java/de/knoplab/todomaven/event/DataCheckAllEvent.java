/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.todomaven.event;

import de.knoplab.todomaven.task.DefaultTodoTask;
import de.knoplab.todomaven.task.TodoTask;
import java.util.List;
import org.scijava.event.SciJavaEvent;

/**
 *
 * @author tuananh
 */
public class DataCheckAllEvent extends SciJavaEvent {

    public final List<TodoTask> data;

    public DataCheckAllEvent(List<TodoTask> task) {
        this.data = task;
    }

    public List<TodoTask> getData() {
        return this.data;

    }
}
