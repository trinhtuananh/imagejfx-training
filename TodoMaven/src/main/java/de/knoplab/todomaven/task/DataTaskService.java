/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.todomaven.task;


import org.scijava.service.SciJavaService;

/**
 *
 * @author tuananh
 */
public interface DataTaskService extends SciJavaService {

    public void addNewTask(String name);

    public void checkAll();

    public void deleteSelected();
}
