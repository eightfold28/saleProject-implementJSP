/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakasta.tubes2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author septialoka
 */
public class RegisterServlet extends HttpServlet {
    
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
        try {
            PrintWriter out = response.getWriter();
            Register register = new Register();
            String fullname = request.getParameter("fullname");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String fulladdress = request.getParameter("fulladdress");
            String postalcode = request.getParameter("postalcode");
            String phone = request.getParameter("phone");
            String ip = request.getParameter("ip");
            String ua = request.getParameter("ua");

            out.print(
                register.RegisterProcess(ip,ua,fullname, username, password, email, fulladdress, postalcode, phone)
            );
            
            
            
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            JSONObject res = new JSONObject();
            res.put("status", "error");
            res.put("message", e.getMessage());
            out.println(res.toJSONString());
        }

        /*
        response.setContentType(MediaType.APPLICATION_JSON);
        InputStream inputStream = request.getInputStream();
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        String body = s.hasNext() ? s.next() : "";
        PrintWriter out = response.getWriter();
        try {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject)parser.parse(body);
            
            String FullName = object.get("fullname").toString();
            String Username = object.get("username").toString();
            String Password = object.get("password").toString();
            String Email = object.get("email").toString();
            String FullAddress = object.get("fulladdress").toString();
            String PostalCode = object.get("postalcode").toString();
            String PhoneNumber = object.get("phonenumber").toString();
          
            
            
            //CONNECT SQL
            Class.forName("com.mysql.jdbc.Driver");
            
            String host = "jdbc:mysql://localhost:3306/tubeswbd1";
            String uName = "root";
            String pass = "";
            
            Connection con = DriverManager.getConnection(host, uName, pass);
         
            Statement statement1 = con.createStatement();
            ResultSet resultSet = statement1.executeQuery("SELECT Username from User where Username = '" + Username + "' OR Email = '" + Email + "'"); 
            if (resultSet.next()) {
                JSONObject res = new JSONObject();
                res.put("status", "error");
                res.put("message", "sudah ada username/ emailnya");
                out.println(res);
                response.setStatus(400);
            } else {
                String sql = "INSERT INTO User (FullName, Username, Email, Password, FullAddress, "
                    + "PostalCode, PhoneNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, FullName);
                stmt.setString(2, Username);
                stmt.setString(3, Email);
                stmt.setString(4, Password);
                stmt.setString(5, FullAddress);
                stmt.setString(6, PostalCode);
                stmt.setString(7, PhoneNumber);

                int result = stmt.executeUpdate();

                JSONObject res = new JSONObject();
                res.put("status", "success");
                res.put("message", "Sukses coy");
                out.println(res);
            }
           
        } catch (Exception e) {
            JSONObject res = new JSONObject();
            res.put("status", "error");
            res.put("message", e.getMessage());
            out.println(res);
            response.setStatus(400);
        }
        */
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
