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
import javafx.util.Callback;

/**
 *
 * @author tuananh
 */
public class Data {
private List<OneTask> myList;

    public Data (){
        myList  = new ArrayList<OneTask>();
        OneTask t = new OneTask("1", true);
        OneTask tt = new OneTask("2", true);

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

             ObservableList<OneTask> items = FXCollections.observableArrayList(this.getMyList());

             return items;
    }
    public void checkAll()
    {
        this.myList.stream().forEach(e -> e.setState(true));
        for (OneTask t : myList)
        {
            t.setState(true);
        }
    }

    

    

  

}
