/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.todomaven.task;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.scijava.service.SciJavaService;

/**
 *
 * @author tuananh
 */
/*@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
@JsonSubTypes({
    @Type(value = DefaultDataTask.class, name = "DefaultDataTask") })*/
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonDeserialize(as = DefaultDataTask.class)

public interface DataTaskService extends SciJavaService {

    public void addNewTask(TodoTask t);

    public void checkAll();

    public void deleteSelected();
}
