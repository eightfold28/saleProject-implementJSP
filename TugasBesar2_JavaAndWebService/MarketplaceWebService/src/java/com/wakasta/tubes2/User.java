/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakasta.tubes2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author septialoka
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "User")
public class User {
    @XmlElement(name = "fullname")
    public String fullname;
    @XmlElement(name = "username")
    public String username;
    @XmlElement(name = "email")
    public String email;
    @XmlElement(name = "password")
    public String password;
    @XmlElement(name = "full_address")
    public String  full_address;
    @XmlElement(name = "postal_code")
    public int postal_code;
    @XmlElement(name = "phone_number")
    public String phone_number;
    
    @XmlElement(name = "create_time")
    public int create_time; // epoch time
}
