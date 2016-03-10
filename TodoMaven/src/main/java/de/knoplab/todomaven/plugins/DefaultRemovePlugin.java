/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.todomaven.plugins;

import de.knoplab.todomaven.task.DataTaskService;
import de.knoplab.todomaven.task.TodoTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 *
 * @author tuananh
 */
@Plugin(type = TodoPlugin.class, label = "Remove Tasks", priority = 10)

public class DefaultRemovePlugin implements TodoPlugin{
    @Parameter
    public DataTaskService tasks;
    @Override
    public void execute() throws IOException {
            tasks.deleteSelected();
    }
    
}
