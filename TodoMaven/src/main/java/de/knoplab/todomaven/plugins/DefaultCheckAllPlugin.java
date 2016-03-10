/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.todomaven.plugins;

import de.knoplab.todomaven.task.DataTaskService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 *
 * @author tuananh
 */
@Plugin(type = TodoPlugin.class, label = "Check All")
public class DefaultCheckAllPlugin implements TodoPlugin {
    @Parameter
    DataTaskService tasks;
    @Override
    public void execute() {
        //tasks.getList().stream().forEach(e -> e.setState(true));
        tasks.checkAll();
    }

}
