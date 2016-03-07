/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.todomavenfxml;

import java.util.List;

import javafx.collections.ObservableList;
import org.scijava.service.SciJavaService;

/**
 *
 * @author tuananh
 */
public interface IDataTaskService extends SciJavaService{
    public void addNewTask(String name);

    public List<TodoTask> getMyList();

    public void setMyList(List<TodoTask> myList);
    
    public List <String> getListofString();
    public ObservableList<TodoTask> getObservableList();
    public void checkAll();
    
    public void deleteSelected();
}
