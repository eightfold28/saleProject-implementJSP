<%-- 
    Document   : login
    Created on : Nov 11, 2016, 10:12:54 PM
    Author     : MaximaXL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang= "en-US">
<head>

<style type=“text/css”>
</style>
<link rel="stylesheet" href="SaleProjectStyle.css" type="text/css">
<script type="text/javascript" src="ceklogin.js"></script>
</head>

<body>
    <div class="title">
            <br>
            Sale<span style="color: #4887E4">Project</span> <br>
    </div>


    <div class="submenu_header">
        <br>
        Please login
        <hr>
    </div>
    

    <c:if test="${param.error eq '1'}">
        <div class= "errorregister"><h3>Username atau password anda salah. Coba lagi.</h3></div>
    </c:if>

    <div class="loginregister">
        <form name= "LoginForm" action= "LoginPost" onsubmit= "return validasiFormKosong()" method= "post">
            <small> Email or Username </small> <br>
            <input type= "text" name= "EmailOrUsername" size= "60" value=""> <br> <br>  
            <small> Password </small> <br>
            <input type ="password" name= "Password" size= "60" value="">
            <br>
            <input type="submit" value="LOGIN" style="float: right;"> <br>
        </form>
    </div>

    <div class="regfirst">
        Don't have an account yet? Register <a href="register.jsp"> here </a>
    </div>

</body>
</html>