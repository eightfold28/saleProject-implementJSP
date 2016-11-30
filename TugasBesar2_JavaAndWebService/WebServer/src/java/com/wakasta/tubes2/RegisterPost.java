/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakasta.tubes2;

import java.io.IOException;
import java.io.PrintWriter;
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
public class RegisterPost extends HttpServlet {

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

	String $FullName = request.getParameter("fullname");
	String $Username = request.getParameter("username");
	String $Email = request.getParameter("email");
	String $Password = request.getParameter("password");
	String $FullAddress = request.getParameter("fulladdress");
	String $PostalCode = request.getParameter("postalcode");
	String $PhoneNumber = request.getParameter("phonenumber");
        
        String data = "fullname="+$FullName+"&username="+$Username+"&email="+$Email+"&password="+$Password+"&fulladdress="+$FullAddress+"&postalcode="+$PostalCode+"&phone="+$PhoneNumber;
        // cek username email
        
        // make a get request to REST API
        String res = httpreq.executePost("http://localhost:8080/IdentityService/RegisterServlet", data);
        JSONParser parser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) parser.parse(res);
            boolean ok = "success".equals((String) obj.get("status") );
            String redirUrl = "./login.jsp";
            
            //Jika username dan email belum ada di db user maka insert
            if( !ok ){
                String msg = (String) obj.get("message");
                response.sendRedirect("register.jsp?error="+msg);
            }
            else{
                String token = (String) obj.get("access_token");
                response.sendRedirect("catalog.jsp?token="+token);
            }
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
