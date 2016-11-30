/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package identityservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
public class Register {
	public void RegisterProcess() {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter full name: ");
		String fullname = input.nextLine();
		System.out.print("Enter username: ");
		String username = input.nextLine();
		System.out.print("Enter password: ");
		String password = input.nextLine();
		System.out.print("Enter email: ");
		String email = input.nextLine();
		System.out.print("Enter full address: ");
		String fulladdress = input.nextLine();
		System.out.print("Enter postal code: ");
		String postalcode = input.nextLine();
		System.out.print("Enter phone number: ");
		String phone = input.nextLine();
		
		try {
			
			JSONObject obj = new JSONObject();
			obj.put("FullName", fullname);
			obj.put("Username", username);
			obj.put("Password", password);
			obj.put("Email", email);
			obj.put("FullAddress", fulladdress);
			obj.put("PostalCode", postalcode);
			obj.put("PhoneNumber", phone);
			
			//CONNECT SQL
			Class.forName("com.mysql.jdbc.Driver");

			String host = "jdbc:mysql://localhost:3306/tubeswbd1";
			String uName = "root";
			String pass = "";

			Connection con = DriverManager.getConnection(host, uName, pass);

			Statement statement1 = con.createStatement();
			String sqlquery = "SELECT Username from User where Username = '" + username + "' OR Email = '" + email + "'";
			ResultSet resultSet = statement1.executeQuery(sqlquery); 
			 
			JSONObject res = new JSONObject();
			if (resultSet.next()) {
				res.put("status", "error");
				res.put("message", "sudah ada username/ emailnya");
			} else {
				String sql = "INSERT INTO User (FullName, Username, Email, Password, FullAddress, "
                    + "PostalCode, PhoneNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, fullname);
                stmt.setString(2, username);
                stmt.setString(3, email);
                stmt.setString(4, password);
                stmt.setString(5, fulladdress);
                stmt.setString(6, postalcode);
                stmt.setString(7, phone);

                int result = stmt.executeUpdate();

				Session ses = new Session();
				char[] token = ses.generateToken(username);
				obj.put("access_token", token);
				
				JSONArray userArr = new JSONArray();
				userArr.add(obj);
			
				res.put("status", "success");
				res.put("message", "register success");
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
}
