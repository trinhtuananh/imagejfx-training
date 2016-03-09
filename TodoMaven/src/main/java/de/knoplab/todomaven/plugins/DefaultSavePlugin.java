/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.todomaven.plugins;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.knoplab.todomaven.task.DataTaskService;
import java.io.File;
import java.io.IOException;

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
    public DataTaskService tasks;
   
    
    public void execute() throws IOException {
        
        System.out.println("t");
        mapper = new ObjectMapper();

//Object to JSON in file
        mapper.writeValue(new File("./src/main/resources/json/saveTasks.json"), tasks.getList());

//Object to JSON in String
        String jsonInString = mapper.writeValueAsString(tasks.getList());
        System.out.println(jsonInString);
    }

}
