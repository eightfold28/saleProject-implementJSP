<%-- 
    Document   : header
    Created on : Nov 12, 2016, 8:04:37 AM
    Author     : MaximaXL
--%>

<%@page import="com.wakasta.tubes2.Session"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ page import="java.net.URLEncoder" %>
<%    
    String ua = URLEncoder.encode(request.getHeader("User-Agent"), "UTF-8");
    String ip = URLEncoder.encode(request.getRemoteAddr(), "UTF-8");
    com.wakasta.tubes2.Session s = new com.wakasta.tubes2.Session(request.getParameter("token"), ip,ua);
    if( s.invalid ){
        out.print("<script>window.location='./'</script>");
        return;
    }
    else {
        out.print("<script>");
        out.print("var cusername = \""+s.user.getUsername()+"\";");
        out.print("var cuid = "+s.user_id+";");
        out.print("var token = \""+request.getParameter("token")+"\";");
        out.print("</script>");
    }
%>
<div class="title">
        <br>
        Sale<span style="color: #4887E4">Project</span> <br>
</div>

<link rel="stylesheet" href="sales.css" type="text/css">

<div class= "user_greet">
    <br>
    Hi, <%= s.user.getUsername() %>! <br>
    <a href= "./LogoutPost?token=${param.token}">logout</a>
    <br> <br>
</div>

<div class="navigation_bar">
    <ul>
        <li><a class="${param.header_catalog_class}" href="catalog.jsp?token=${param.token}">Catalog</a></li>
        <li><a class="${param.header_yourproduct_class}" href="yourproduct.jsp?token=${param.token}">Your Product</a></li>
        <li><a class="${param.header_addproduct_class}" href="addproduct.jsp?token=${param.token}">Add Product</a></li>
        <li><a class="${param.header_sales_class}" href="sales.jsp?token=${param.token}">Sales</a></li>
        <li><a class="${param.header_purchases_class}" href="purchases.jsp?token=${param.token}">Purchases</a></li>
    </ul>
</div>
