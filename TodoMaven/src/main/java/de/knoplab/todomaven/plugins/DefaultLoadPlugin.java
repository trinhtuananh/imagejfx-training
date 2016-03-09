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

/**
 *
 * @author tuananh
 */
public class DefaultLoadPlugin implements TodoPlugin{
    private ObjectMapper mapper;
    @Parameter
    public DataTaskService tasks;
    @Override
    public void execute() throws IOException {
        mapper = new ObjectMapper();
        tasks  = mapper.readValue(new File("./src/main/resources/json/saveTasks.json"), DataTaskService.class);

        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
