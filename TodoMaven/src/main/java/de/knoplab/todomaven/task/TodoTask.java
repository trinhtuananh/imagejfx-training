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
public interface TodoTask {
    public String getName();

    public void setName(String name) ;

    public boolean getState();

    public void setState(boolean state);
}
