/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakasta.tubes2;

import static com.wakasta.tubes2.Session.userToken;
import static java.lang.System.in;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author hishshahghassani
 */
public class Login {
	public static JSONArray userArr = new JSONArray();
	
	public String LoginProcess(String username, String password, String ip, String ua) {
		try {
			
			JSONObject obj = new JSONObject();
			obj.put("Username", username);
			obj.put("Password", password);
			
			//CONNECT SQL
			Class.forName("com.mysql.jdbc.Driver");

			String host = "jdbc:mysql://localhost:3306/tubeswbd1";
			String uName = "root";
			String pass = "";

			Connection con = DriverManager.getConnection(host, uName, pass);

			Statement statement1 = con.createStatement();
			String sqlquery = "SELECT Username FROM user WHERE (Username = '" + username + "' OR Email = '" + username + "') AND Password = '" + password + "'";
			ResultSet resultSet = statement1.executeQuery(sqlquery); 
			 
			JSONObject res = new JSONObject();
			if (resultSet.next()) {
				String tokens = Session.registerToken(username,ip,ua);
				obj.put("access_token", tokens);
								
				userArr.add(obj);
                                //return userArr;
			
				res.put("access_token", tokens);
				res.put("status", "success");
				res.put("message", "login success");
			} else {
                                res.put("username",username);
                                res.put("password",password);
				res.put("status", "error");
				res.put("message", "usename atau password salah");
			}
                        return res.toJSONString();
		}
		catch (ClassNotFoundException | SQLException e) {
			JSONObject res = new JSONObject();
			res.put("status", "error");
			res.put("message", e.getMessage());
                        return res.toJSONString();
		}
	}
	
	public String LogoutProcess(String token, String ip, String ua) {
            JSONObject res = new JSONObject();
            if( Session.removeToken(token,ip,ua) ){
                res.put("status", "success");
            }
            else {
                res.put("status", "error");                
            }
            return res.toJSONString();
	}
}
