/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakasta.tubes2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MaximaXL
 */
public class httpreq {
    public static String executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;

        try {
          //Create connection
          URL url = new URL(targetURL);
          connection = (HttpURLConnection) url.openConnection();
          connection.setRequestMethod("POST");
          connection.setRequestProperty("Content-Type", 
              "application/x-www-form-urlencoded");

          connection.setRequestProperty("Content-Length", 
              Integer.toString(urlParameters.getBytes().length));
          connection.setRequestProperty("Content-Language", "en-US");  

          connection.setUseCaches(false);
          connection.setDoOutput(true);

          //Send request
          DataOutputStream wr = new DataOutputStream (
              connection.getOutputStream());
          wr.writeBytes(urlParameters);
          wr.close();

          //Get Response  
          InputStream is = connection.getInputStream();
          BufferedReader rd = new BufferedReader(new InputStreamReader(is));
          StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
          String line;
          while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
          }
          rd.close();
          return response.toString();
        } catch (ProtocolException ex) {
            Logger.getLogger(httpreq.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(httpreq.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(httpreq.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
          if (connection != null) {
            connection.disconnect();
          }
        }
        return null;
      }
}
