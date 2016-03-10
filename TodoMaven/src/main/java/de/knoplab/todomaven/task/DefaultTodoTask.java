/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.todomaven.task;

/**
 *
 * @author tuananh
 */
public class DefaultTodoTask implements TodoTask {
    private String name;
    private boolean state;
    public DefaultTodoTask()
    {
        
    }
    public DefaultTodoTask(String n, boolean s)
    {
        this.name = n;
        this.state = s;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean getState() {
        return state;
    }

    @Override
    public void setState(boolean state) {
        System.err.println(this.state+"Change state "+state );
        this.state = state;
    }
    
    
}
