<%-- 
    Document   : register
    Created on : Nov 11, 2016, 10:59:06 PM
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
</head>
<body>

    <div class="title">
        <br>
        Sale<span style="color: #4887E4">Project</span> <br>
    </div>

    <div class="submenu_header">
        <br>
        Please register
        <hr>
    </div>
    <div class= "errorregister"><h3>
    <c:out value="${param.error}" />
    </h3></div>

    <div class="loginregister">
        <form name= "registerForm" action= "./RegisterPost" onsubmit= "return validasi()" method= "post">
            <small> Full Name </small> <br>
            <input type= "text" name= "fullname" size= "60"> <br> 
            <small> Username </small> <br>
            <input type= "text" name= "username" size= "60"> <br> 
            <small> Email </small> <br>
            <input type= "text" name= "email" size= "60" onblur= "return validasiEmail()"> <br> 
            <small> Password </small> <br>
            <input type ="password" name= "password" size= "60" > <br>
            <small> Confirm Password </small> <br>
            <input type= "password" name= "confirmpassword" size= "60" onblur= "return validasiPassword()"> <br> 
            <small> Full Address </small> <br>
            <textarea name="fulladdress" cols= "123" rows="2"></textarea><br> 
            <small> Postal Code </small> <br>
            <input type= "text" name= "postalcode" size= "60"> <br> 
            <small> Phone Number </small> <br>
            <input type= "text" name= "phonenumber" size= "60"> <br> 
            <br>
            <input type="submit" value="REGISTER" style="float: right;">
        </form>
    </div>

    <div class="regfirst">
        Already registered? Login <a href="login.jsp"> here </a>
    </div>

<script type="text/javascript" src="cekregister.js"></script>
</body>
</html>