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
@Plugin(type = TodoPlugin.class, label = "Load Data", priority = 100)
public class DefaultLoadPlugin implements TodoPlugin {

    private ObjectMapper mapper;
    @Parameter
    public DataTaskService tasks;

    @Override
    public void execute() {
        mapper = new ObjectMapper();
        try {
             tasks = mapper.readValue(new File("./src/main/resources/json/saveTasks.json"), DataTaskService.class);
        } catch (IOException ex) {
            Logger.getLogger(DefaultLoadPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
