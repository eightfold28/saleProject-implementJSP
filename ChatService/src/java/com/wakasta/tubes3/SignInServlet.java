/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakasta.tubes3;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.tasks.OnCompleteListener;
import com.google.firebase.tasks.OnSuccessListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONObject;

/**
 *
 * @author septialoka
 */
public class SignInServlet extends HttpServlet {
    public void init() {
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("com/wakasta/tubes3/tugas3-4f03a-firebase-adminsdk-q3j51-2cf5e105cd.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
            .setServiceAccount(input)
            .setDatabaseUrl("https://tugas3-4f03a.firebaseio.com")
            .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        } else {
            if (FirebaseDatabase.getInstance().getReference() == null) {
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            }
        }
    }

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
        String token = request.getParameter("token");
        String username = request.getParameter("username");
        
        HashMap<String, Object> additionalClaims = new HashMap<String, Object>();
        additionalClaims.put("username", username);
        
        final AsyncContext asyncContext = request.startAsync(request, response);

                
        FirebaseAuth.getInstance().createCustomToken(token, additionalClaims)
            .addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String customToken) {
                    // Send token back to client
                    System.out.println("customToken: " + customToken);
                    
                    JSONObject res = new JSONObject();
                    res.put("token", customToken);

                    try {
                        HttpServletResponse asyncResponse = (HttpServletResponse)asyncContext.getResponse();
                        asyncResponse.setContentType(MediaType.APPLICATION_JSON);
                        asyncResponse.setHeader("Access-Control-Allow-Origin", "*");
                        asyncResponse.setHeader("Access-Control-Allow-Methods", "POST");
                        asyncResponse.setHeader("Access-Control-Allow-Headers", "Content-Type");

                        PrintWriter out = asyncContext.getResponse().getWriter();
                        out.println(res.toJSONString());
                    } catch (Exception e) {
                        System.out.println("Exception on getting async printWriter: " + e.getMessage());
                        e.printStackTrace();
                    } finally {
                        asyncContext.complete();
                    }
                }
            });
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
