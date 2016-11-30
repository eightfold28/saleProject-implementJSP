/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package identityservice;

import static identityservice.Session.userToken;
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
	
	public void LoginProcess() {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter username: ");
		String username = input.nextLine();
		System.out.print("Enter password: ");
		String password = input.nextLine();
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
				Session ses = new Session();
				char[] token = ses.generateToken(username);
				String tokens = String.valueOf(token);
				obj.put("access_token", tokens);
								
				userArr.add(obj);
				System.out.println(userArr);
			
				res.put("status", "success");
				res.put("message", "login success");
			} else {
				res.put("status", "error");
				res.put("message", "usename atau password salah");
			}
			System.out.println(res);
		}
		catch (ClassNotFoundException | SQLException e) {
			JSONObject res = new JSONObject();
			res.put("status", "error");
			res.put("message", e.getMessage());
			System.out.println(res);
		}
	}
	
	public void LogoutProcess() {
		userArr.remove(0);
		System.out.println(userArr);
	}
}
