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
 * @author MaximaXL
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Product")
public class Product {
    @XmlElement(name = "id")
    public int id;
    @XmlElement(name = "name")
    public String name;
    @XmlElement(name = "description")
    public String description;
    @XmlElement(name = "price")
    public int price;
    @XmlElement(name = "purchase_count")
    public int purchase_count;
    @XmlElement(name = "like_count")
    public int like_count;
    @XmlElement(name = "time_add")
    public String time_add;
    @XmlElement(name = "date_add")
    public String date_add;
    @XmlElement(name = "owner")
    public String owner;
    @XmlElement(name = "image")
    public byte[] image;
    @XmlElement(name = "create_time")
    public int create_time; // epoch time
}
