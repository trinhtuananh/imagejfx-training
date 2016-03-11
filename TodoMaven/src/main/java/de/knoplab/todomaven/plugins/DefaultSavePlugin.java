/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.todomaven.plugins;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.knoplab.todomaven.task.DataTaskService;
import de.knoplab.todomaven.task.DefaultTodoTask;
import de.knoplab.todomaven.task.TodoTask;
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
@Plugin(type = TodoPlugin.class, label = "Save Tasks", priority = 10)
public class DefaultSavePlugin  implements TodoPlugin {
    private ObjectMapper mapper;

    @Parameter
    public DataTaskService dataTaskService;
   
    
    public void execute(){
        
        mapper = new ObjectMapper();
        List <TodoTask> listInput = new ArrayList<>();
        dataTaskService.getList().forEach(e -> listInput.add(new DefaultTodoTask(e.getName(), e.getState())));
        try {
            mapper.writeValue(new File("./src/main/resources/json/saveTasks.json"), listInput);
        } catch (IOException ex) {
            Logger.getLogger(DefaultSavePlugin.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

}
