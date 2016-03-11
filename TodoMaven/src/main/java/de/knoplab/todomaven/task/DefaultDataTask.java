/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.todomaven.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.knoplab.todomaven.event.DataDeleteEvent;
import de.knoplab.todomaven.event.DataCheckAllEvent;
import de.knoplab.todomaven.event.DataAddedEvent;
import de.knoplab.todomaven.event.DataLoadEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.scijava.Context;
import org.scijava.event.DefaultEventService;
import org.scijava.event.EventHandler;
import org.scijava.event.EventService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.service.AbstractService;

/**
 *
 * @author tuananh
 */

@Plugin(type = DataTaskService.class, priority = 10)
public final class DefaultDataTask extends AbstractService implements DataTaskService {

    private List<TodoTask> todoTaskList;
    @Parameter
    @JsonIgnore
    public EventService eventService;
    @JsonIgnore
    public Context context ;
    public DefaultDataTask() throws Exception {
        todoTaskList = new ArrayList<>();
        TodoTask task = new TodoTaskWrapper( new DefaultTodoTask("Example 1", false));
        todoTaskList.add(task);

    }

    @Override
    public void addNewTask(TodoTask t) {
        
        todoTaskList.add(t);
        eventService.publish(new DataAddedEvent(t));
    }

    public List<TodoTask> getMyList() {
        return todoTaskList;
    }

    @Override
    public void checkAll() {
        this.todoTaskList.stream().forEach(e -> e.setState(true));
        eventService.publish(new DataCheckAllEvent(todoTaskList));
        System.out.println("State of all tasks");
        this.todoTaskList.stream().forEach(e -> System.out.println(e.getName() + " " + e.getState()));
    }

    @Override
    public void deleteSelected() {
        List<TodoTask> listToDelete = this.todoTaskList;
        this.todoTaskList = this.todoTaskList.stream().filter(e -> e.getState() == false).collect(Collectors.toList());
        listToDelete.removeAll(this.todoTaskList);
        listToDelete.forEach(e -> {
            todoTaskList.remove(e);
            eventService.publish(new DataDeleteEvent(e));
            
                });
    }

    @EventHandler
    public void loadUI(DataLoadEvent event) {
        this.todoTaskList.stream().forEach(e -> this.eventService.publish(new DataAddedEvent(e)));
    }

    @Override
    public List<TodoTask> getList() {
        return this.todoTaskList;
    }

    @Override
    public void setList(List<TodoTask> l) {
        l.stream().forEach(e -> addNewTask(e));
    }


}
