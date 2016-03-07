/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab;

/**
 *
 * @author tuananh
 */
public class OneTask {
    private String name;
    private boolean state;

    public OneTask(String n, boolean s)
    {
        this.name = n;
        this.state = s;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
    
    
}
