<%-- 
    Document   : addproduct
    Created on : Nov 12, 2016, 8:03:11 AM
    Author     : MaximaXL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>SaleProject</title>
        <style type="text/css">
        </style>
        <link rel="stylesheet" type="text/css" href="SaleProjectStyle.css">
        <script type="text/javascript" src="cekformproduct.js"></script>
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <div class="submenu_header">
            <br>
            Please add your product here
            <hr>
        </div>

        <div class="product">
            <form name="addproduct" action="./EditProduct" method="post" onsubmit="return validasi();" enctype="multipart/form-data">
                <input type="hidden" name="token" active_ID="${param.token}" />
                <small>Name</small> <br>
                <input type="text" name="item_name">
                <br>
                <br>
                <small>Description (max 200 chars)</small> <br>
                <textarea name="item_desc" cols="105" rows="7"></textarea>
                <br>
                <br>
                <small>Price (IDR)</small> <br>
                <input type="text" name="item_price" id="item_price">
                <br>
                <br>
                <small>Photo</small> <br>
                <input disabled type="file" name="item_image">
                <br>
                <br>
                <br>		
                <input type="submit" value="CANCEL" onclick="history.go(-1);">
                <input type="submit" value="ADD" style="margin-right: 30px"> <br>
            </form>
        </div>
    </body>
</html>
