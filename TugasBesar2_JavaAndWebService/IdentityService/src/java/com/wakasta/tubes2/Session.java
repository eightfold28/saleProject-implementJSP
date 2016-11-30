/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakasta.tubes2;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.HashMap;
import org.json.simple.JSONObject;
/**
 *
 * @author hishshahghassani
 */

public class Session {
	
    public static class UserSession {
        public static final int session_duration = 1000 * 60 * 10; // 10 min

        UserSession(String t, String u, String ip_, String ua){
            token = t;
            username = u;
            expire_date = new Date(new Date().getTime() + session_duration);
            ip = (ip_ == null ? "" : ip_);
            user_agent = ( ua == null ? "X" : ua );
        }
        
        public boolean isExpired(){
            return new Date().getTime() > expire_date.getTime();
        }
        
        public boolean isValidIP(String ip_){
            return ip.equals(ip_);
        }
        public boolean isValidUserAgent(String ua){
            return user_agent.equals(ua);
        }
        
        public JSONObject getUserData() throws SQLException{
            //CONNECT SQL
//            Class.forName("com.mysql.jdbc.Driver");

            String host = "jdbc:mysql://localhost:3306/tubeswbd1";
            String uName = "root";
            String pass = "";

            Connection con = DriverManager.getConnection(host, uName, pass);

            Statement statement1 = con.createStatement();
            String query = "SELECT active_ID, Username, Email, Fullname, FullAddress, PostalCode, PhoneNumber FROM User WHERE Username='"+username+"';";
            ResultSet rs = statement1.executeQuery(query);             
            JSONObject obj = new JSONObject();
            if( rs.next() ){
                String Username = rs.getString("Username");
                String Fullname = rs.getString("Fullname");
                String FullAddress = rs.getString("FullAddress");
                String Email = rs.getString("Email");
                String PostalCode = rs.getString("PostalCode");
                String PhoneNumber = rs.getString("PhoneNumber");
                String id = rs.getString("active_ID");
                obj.put("id", id);
                obj.put("FullName", Fullname);
                obj.put("Username", Username);
                obj.put("Email", Email);
                obj.put("FullAddress", FullAddress);
                obj.put("PostalCode", PostalCode);
                obj.put("PhoneNumber", PhoneNumber);
            }
            return obj;
        }

        public final String ip;
        public final String user_agent;
        public final String token;
        public final String username;
        public final Date expire_date;
    }

    public static HashMap<String, UserSession> userToken = new HashMap<>(); // token to username
    public static HashMap<String, String> username2Token = new HashMap<>(); // username


    private static String generateToken(String username) {
        if( userToken.containsKey(username) )
            return userToken.get(username).token;

        Random rand = new Random();
        String characters = "qwertyuiopasdfghjklzxcvbnmm1234567890";
        char[] token = new char[10];
        for (int i = 0; i < 10; i++) {
                token[i] = characters.charAt(rand.nextInt(characters.length()));
        }
        String tokens = String.valueOf(token);

        return tokens;
    }
    
    public static String registerToken(String username, String ip, String ua){
        if( username2Token.containsKey(username) )
            return username2Token.get(username);
        
        String token = generateToken(username);
        userToken.put(token,new UserSession(token,username,ip,ua));
        username2Token.put(username, token);
        return token;
    }
    
    public static JSONObject invalidToken(){
        JSONObject obj = new JSONObject();
        obj.put("status", "error");
        obj.put("message","token invalid");
        return obj;
    }

    public static JSONObject debugToken(String msg){
        JSONObject obj = new JSONObject();
        obj.put("status", "programming error");
        obj.put("message",msg);
        return obj;
    }
    
    public static JSONObject getSession(String token, String ip, String userAgent) throws SQLException {
        if( !userToken.containsKey(token) )
            return invalidToken();

        UserSession session = userToken.get(token);
        if( ip == null ) ip = "";
        if( userAgent == null ) userAgent = "";
        

        if( !session.isValidIP(ip) )
            return debugToken("ip " + ip +" != "+ session.ip);

        if( !session.isValidUserAgent(userAgent) )
            return debugToken("ua");

        if( session.isExpired() ){
            removeToken(session.token);
            return invalidToken();
        }


        JSONObject obj = session.getUserData();
        obj.put("expire_date", session.expire_date.toString());
        obj.put("user_agent", session.user_agent.toString());
        obj.put("ip", session.ip.toString());
        obj.put("status", "success");
        return obj;
    }

    public static boolean removeToken(String token, String ip, String ua){
        if (!userToken.containsKey(token)) 
            return false;

        UserSession session = userToken.get(token);
        if( !session.isValidIP(ip))
            return false;
        if( !session.isValidUserAgent(ua))
            return false;
        
        return removeToken(token);
    }
    
    private static boolean removeToken(String token){
        if (!userToken.containsKey(token)) 
            return false;
        UserSession session = userToken.get(token);
        username2Token.remove(session.username);
        userToken.remove(token);
        return true;
    }
    
    public static boolean isLoggedIn(String username){
        return username2Token.containsKey(username);
    }
}