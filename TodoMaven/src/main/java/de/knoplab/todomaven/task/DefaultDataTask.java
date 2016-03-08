/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.todomaven.task;

import de.knoplab.todomaven.event.DataDeleteEvent;
import de.knoplab.todomaven.event.DataCheckAllEvent;
import de.knoplab.todomaven.event.DataAddedEvent;
import de.knoplab.todomaven.event.DataLoadEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.scijava.event.EventHandler;
import org.scijava.event.EventService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.service.AbstractService;
import org.scijava.service.SciJavaService;

/**
 *
 * @author tuananh
 */
//I don't know which interface I should have implemented
@Plugin(type = SciJavaService.class, priority = 10)
public final class DefaultDataTask extends AbstractService implements DataTaskService {

    private List<TodoTask> myList;
    @Parameter
    EventService eventService;

    public DefaultDataTask() throws Exception {
        myList = new ArrayList<>();
        TodoTask t = new TodoTask(("Data 1"), true);
        myList.add(t);

    }

    @Override
    public void addNewTask(String name) {
        TodoTask t = new TodoTask(name, false);
        myList.add(t);
        eventService.publish(new DataAddedEvent(t));
    }

    public List<TodoTask> getMyList() {
        return myList;
    }

    @Override
    public void checkAll() {
        this.myList.stream().forEach(e -> e.setState(true));
        eventService.publish(new DataCheckAllEvent(myList));
        System.out.println("State of all tasks");
        this.myList.stream().forEach(e -> System.out.println(e.getName() + " " + e.getState()));
    }

    @Override
    public void deleteSelected() {
        List<TodoTask> listToDelete = this.myList;
        this.myList = this.myList.stream().filter(e -> e.getState() == false).collect(Collectors.toList());
        listToDelete.removeAll(this.myList);
        eventService.publish(new DataDeleteEvent(listToDelete));
    }

    @EventHandler
    public void loadUI(DataLoadEvent event) {
        this.myList.stream().forEach(e -> this.eventService.publish(new DataAddedEvent(e)));
    }

}
