/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author tuananh
 */

//I don't know which interface I should have implemented
public class Data {
private List<OneTask> myList;
private ObservableList<OneTask> items ;
    public Data (){
        myList  = new ArrayList<OneTask>();
        OneTask t = new OneTask("Exemple 1", false);
        OneTask tt = new OneTask("Exemple 2", false);

        myList.add(t);
        myList.add(tt);
        
    }
    
    public void addNewTask(String name)
    {
        OneTask t = new OneTask(name, false);
        myList.add(t);
        
    }

    public List<OneTask> getMyList() {
        return myList;
    }

    public void setMyList(List<OneTask> myList) {
        this.myList = myList;
    }
    
    public List <String> getListofString(){
        List <String> result = myList.stream().map(OneTask::getName).collect(Collectors.toList());
        return result;
    }
    public ObservableList<OneTask> getObservableList()
    {
            items = FXCollections.observableArrayList(this.getMyList());
            System.out.println("State of all tasks");
            this.items.stream().forEach(e -> System.out.println(e.getName() + " "+e.getState()));
            return items;
    }
    public void checkAll()
    {
        this.myList.stream().forEach(e -> e.setState(true));
        System.out.println("State of all tasks");
        this.items.stream().forEach(e -> System.out.println(e.getName() + " "+e.getState()));
        
    }
    
    public void deleteSelected()
    {
        this.myList = this.myList.stream().filter(e -> e.getState() == false).collect(Collectors.toList());
        
    }

    

    

  

}
