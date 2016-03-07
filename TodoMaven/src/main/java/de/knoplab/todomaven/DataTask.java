/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.todomaven;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.scijava.Priority;
import org.scijava.app.StatusService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.service.AbstractService;
import org.scijava.service.SciJavaService;
import org.scijava.service.Service;

/**
 *
 * @author tuananh
 */

//I don't know which interface I should have implemented
@Plugin(type = SciJavaService.class)
public class DataTask extends AbstractService implements IDataTaskService{
private List<TodoTask> myList;

private ObservableList<TodoTask> items ;
    public DataTask (){
        myList  = new ArrayList<>();
        TodoTask t = new TodoTask("Exemple 1", false);
        TodoTask tt = new TodoTask("Exemple 2", false);
        myList.add(t);
        myList.add(tt);
        
    }
    
    
@Override
    public void addNewTask(String name)
    {
        TodoTask t = new TodoTask(name, false);
        myList.add(t);
        
    }

@Override
    public List<TodoTask> getMyList() {
        return myList;
    }

@Override
    public void setMyList(List<TodoTask> myList) {
        this.myList = myList;
    }
    
@Override
    public List <String> getListofString(){
        List <String> result = myList.stream().map(TodoTask::getName).collect(Collectors.toList());
        return result;
    }
@Override
    public ObservableList<TodoTask> getObservableList()
    {
            items = FXCollections.observableArrayList(this.getMyList());
            System.out.println("State of all tasks");
            this.items.stream().forEach(e -> System.out.println(e.getName() + " "+e.getState()));
            return items;
    }
@Override
    public void checkAll()
    {
        this.myList.stream().forEach(e -> e.setState(true));
        System.out.println("State of all tasks");
        this.items.stream().forEach(e -> System.out.println(e.getName() + " "+e.getState()));
        
    }
    
@Override
    public void deleteSelected()
    {
        this.myList = this.myList.stream().filter(e -> e.getState() == false).collect(Collectors.toList());
        
    }

    

    

  

}
