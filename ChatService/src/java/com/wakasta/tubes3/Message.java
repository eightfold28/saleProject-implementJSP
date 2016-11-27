/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakasta.tubes3;

/**
 *
 * @author septialoka
 */
public class Message {
    public String from;
    public String to;
    public String message;
    
    public Message(String from, String to, String message) {
        this.from = from;
        this.to= to;
        this.message = message;
    }
}
