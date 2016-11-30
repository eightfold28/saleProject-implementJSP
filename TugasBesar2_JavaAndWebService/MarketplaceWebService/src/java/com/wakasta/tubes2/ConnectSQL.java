/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakasta.tubes2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author septialoka
 */
public class ConnectSQL {
    
    final static String host = "jdbc:mysql://localhost:3306/tubeswbd1";
    final static String uName = "root";
    final static String pass = "";
    static Connection con;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(host, uName, pass);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return con;
    }
}
