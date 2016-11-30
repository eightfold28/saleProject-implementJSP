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
@XmlType(name = "Transaction")
public class Transaction {
//    public int buyer_id;
//    public int seller_id;
//    public int product_id; 

    @XmlElement(name = "product_name")
    public String product_name;
    @XmlElement(name = "product_price")
    public int product_price;
    @XmlElement(name = "buyer_username")
    public String buyer_username;
    
    @XmlElement(name = "quantity")
    public int quantity;
    @XmlElement(name = "consignee")
    public String  consignee;
    @XmlElement(name = "full_address")
    public String  full_address;
    @XmlElement(name = "postal_code")
    public int postal_code;
    @XmlElement(name = "phone_number")
    public String phone_number;
    @XmlElement(name = "time_add")
    public String time_add;
    @XmlElement(name = "date_add")
    public String date_add;
//    public int credit_card_number;
//    public int credit_card_verification;
    
    @XmlElement(name = "create_time")
    public int create_time; // epoch time
}
