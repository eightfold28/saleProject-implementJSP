/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package identityservice;

import java.util.Random;
import java.util.HashMap;
import org.json.simple.JSONObject;
/**
 *
 * @author hishshahghassani
 */

public class Session {
	
	public static HashMap<String, char[]> userToken = new HashMap<>();
	
	public char[] generateToken(String username) {
		Random rand = new Random();
		String characters = "qwertyuiopasdfghjklzxcvbnmm1234567890";
		char[] token = new char[10];
		for (int i = 0; i < 10; i++) {
			token[i] = characters.charAt(rand.nextInt(characters.length()));
		}
		
		userToken.put(username, token);
		
		return token;
	}
	
	public String validateToken(char[] token) {
		if (userToken.containsValue(token)) {
			for (String key: userToken.keySet()) {
				if (userToken.get(key).equals(token)) {
					return key;
				}
			}
		} return "error";
	}
	
}