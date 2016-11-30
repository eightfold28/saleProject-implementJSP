/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakasta.tubes3;

import java.util.Date;

/**
 *
 * @author MaximaXL
 */
public class Sender {
    public String from;
    public long timestamp;
    
    public Sender(String from) {
        this.from = from;
        this.timestamp = new Date().getTime();
    }
}
