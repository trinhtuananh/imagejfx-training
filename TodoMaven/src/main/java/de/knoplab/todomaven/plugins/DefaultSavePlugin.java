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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 *
 * @author tuananh
 */
@Plugin(type = TodoPlugin.class, label = "Save Tasks", priority = 10)
public class DefaultSavePlugin implements TodoPlugin {

    private ObjectMapper mapper;

    @Parameter
    public DataTaskService tasks;

    @Override
    public void execute() {
            
        try {
            this.tasks.getContext().inject(this);
            System.out.println("t");
            mapper = new ObjectMapper();

//Object to JSON in file
            mapper.writeValue(new File("./src/main/resources/json/saveTasks.json"), tasks);

//Object to JSON in String
            String jsonInString = mapper.writeValueAsString(tasks);
            System.out.println(jsonInString);
        } catch (IOException ex) {
            Logger.getLogger(DefaultSavePlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
