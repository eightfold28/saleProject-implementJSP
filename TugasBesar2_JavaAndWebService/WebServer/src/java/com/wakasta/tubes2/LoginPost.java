/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakasta.tubes2;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/**
 *
 * @author MaximaXL
 */
public class LoginPost extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("EmailOrUsername");
        String password = request.getParameter("Password");
        // make a get request to REST API

        String ua = URLEncoder.encode(request.getHeader("User-Agent"), "UTF-8");
        String ip = URLEncoder.encode(request.getRemoteAddr(), "UTF-8");

        String data = "username="+username+"&password="+password+"&ip="+ip+"&ua="+ua;
        System.out.println(data);
        String res = httpreq.executePost("http://localhost:8080/IdentityService/LoginServlet", data);
        JSONParser parser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) parser.parse(res);
            boolean ok = "success".equals((String) obj.get("status") );
            String token = (String) obj.get("access_token");
            String redirUrl = "./login.jsp";

            if(ok){
                redirUrl = "./catalog.jsp?token=".concat(token);
            }
            else {
                redirUrl = "./login.jsp?error=1";
            }        
            response.sendRedirect(redirUrl); 
        } catch (ParseException ex) {
            Logger.getLogger(LoginPost.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
