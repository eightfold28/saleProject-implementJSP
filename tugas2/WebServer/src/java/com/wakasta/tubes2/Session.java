/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakasta.tubes2;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author MaximaXL
 */
public class Session {
    public int user_id;
    public User user;
    public String access_token;
    public boolean invalid;
    
    public Session(String token, String ip, String ua){
        try {
            access_token = token;
            String data = "token="+token+"&ip="+ip+"&ua="+ua;
            String res = httpreq.executePost("http://localhost:8080/IdentityService/SessionServlet", data);
            System.out.print(data);
            System.out.print(res);
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(res);

            if( "success".equals((String) obj.get("status")) ){
                user = new User();
                user.setUsername( (String) obj.get("Username") );
                user.setFullname((String) obj.get("FullName"));
                user.setFullAddress((String) obj.get("FullAddress"));
                user.setEmail((String)obj.get("Email"));
                user.setPhoneNumber((String) obj.get("PhoneNumber"));
                user.setPostalCode(Integer.parseInt((String) obj.get("PostalCode")));
                
                user_id = Integer.parseInt((String) obj.get("id"));
                invalid = false;
            }
            else {
                invalid = true;
            }
        } catch (ParseException ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
