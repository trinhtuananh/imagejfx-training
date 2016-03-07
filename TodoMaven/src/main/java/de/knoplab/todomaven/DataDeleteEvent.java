/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.todomaven;

import java.util.List;
import org.scijava.event.SciJavaEvent;

/**
 *
 * @author tuananh
 */
public class DataDeleteEvent extends SciJavaEvent{
    public final List <TodoTask> data;
    public DataDeleteEvent(List<TodoTask> tasks) {
        this.data  = tasks;
    }
    public List<TodoTask> getData() {
        return this.data;
    }
}