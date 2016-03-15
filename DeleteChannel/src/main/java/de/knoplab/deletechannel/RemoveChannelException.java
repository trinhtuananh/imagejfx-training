/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.deletechannel;

/**
 *
 * @author tuananh
 */
class RemoveChannelException extends Exception {

    public RemoveChannelException() {
        System.err.println("Channel not available");
    }
    
}
