/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.todomaven.event;

import de.knoplab.todomaven.task.DefaultTodoTask;
import de.knoplab.todomaven.ui.ViewModel;
import java.util.List;
import org.scijava.event.SciJavaEvent;

/**
 *
 * @author tuananh
 */
public class DataDeleteEvent extends SciJavaEvent {

    public final List<ViewModel> data;

    public DataDeleteEvent(List<ViewModel> tasks) {
        this.data = tasks;
    }

    public List<ViewModel> getData() {
        return this.data;
    }
}
