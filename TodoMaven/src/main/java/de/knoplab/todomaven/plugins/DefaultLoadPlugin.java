/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.todomaven.plugins;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import de.knoplab.todomaven.task.DataTaskService;
import de.knoplab.todomaven.task.DefaultTodoTask;
import de.knoplab.todomaven.task.TodoTask;
import de.knoplab.todomaven.task.TodoTaskWrapper;
import de.knoplab.todomaven.ui.AlertWindow;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 *
 * @author tuananh
 */
@Plugin(type = TodoPlugin.class, label = "Load Tasks", priority = 10)

public class DefaultLoadPlugin implements TodoPlugin{
    private ObjectMapper mapper;
    @Parameter
    public DataTaskService dataTaskService;
    @Override
    public void execute() {
        mapper = new ObjectMapper();

        try {
            List <TodoTask> listOutput;
            TypeFactory typeFactory = TypeFactory.defaultInstance();
            listOutput = mapper.readValue(new File("./src/main/resources/json/saveTasks.json"), typeFactory.constructCollectionType(ArrayList.class, DefaultTodoTask.class) );             
            listOutput.forEach(e -> dataTaskService.addNewTask(e));
             
        } catch (IOException ex) {
            AlertWindow.display("Error data", "Any saved data");
            Logger.getLogger(DefaultLoadPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
  
        
    }
    
}
