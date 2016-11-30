<%-- 
    Document   : yourproduct
    Created on : Nov 12, 2016, 10:05:06 AM
    Author     : MaximaXL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>SaleProject</title>
        <style type="text/css">
        </style>
        <link rel="stylesheet" href="SaleProjectStyle.css" type="text/css">
    </head>
<body>
        <jsp:include page="header.jsp" >
            <jsp:param name="header_yourproduct_class" value="active" />
        </jsp:include>

        <div class="submenu_header">
                <br>
                What are you going to sell today?
                <hr>
        </div>

        <div class="sales_history"><br></div>
        <%-- start web service invocation --%>
        <%
        try {
            com.wakasta.tubes2.Marketplace_Service service = new com.wakasta.tubes2.Marketplace_Service();
            com.wakasta.tubes2.Marketplace port = service.getMarketplacePort();
             // TODO initialize WS operation arguments here
            java.lang.String token = request.getParameter("token");
            // TODO process result here
            java.util.List<com.wakasta.tubes2.Product> result = port.getYourProducts(token);
            request.setAttribute("prod_list", result);
        } catch (Exception ex) {
            // TODO handle custom exceptions here
            out.print(ex);
        }
        %>
        <%-- end web service invocation --%>
        <c:if test="${prod_list.size() == 0}">
            <div class="catalog_item">0 result</div>;
        </c:if>
        <c:forEach var="prod" items="${prod_list}" varStatus="status">
            <div class = "catalog_item">
                <b><c:out value="${prod.date_add}" /></b>
                <br />
                at <c:out value="${prod.time_added}" />
                <br />
                <hr />
                <div class = "product_image"><img src="data:image/jpg;base64, ${prod.item_image}" /></div>
                <div class = "yourprod_details">
                    <b><c:out value="${prod.item_name}" /></b><br />
                    <div class="harga">IDR <c:out value="{prod.item_price}" /><br></div>
                    <div class="desc"><c:out value="{prod.item_desc}" /></div>
                </div>
                <div class="yourprod_likeandpurch">
                        <br />
                        <c:out value="{prod.like_count}" />
                        <c:out value="{prod.item_purchases}" />
                        <br />
                        <br />
                        <br />
                        <div class="yourprod_button">
                            <b><a href="editproduct.jsp?token=${param.token}">EDIT</a></b>
                        </div>
                        <div class="yourprod_button">
                            <b><a onclick="javascript: return confirm('Are you sure to delete this item?');" href="yourproduct.jsp?token=${param.token}">DELETE</a></b>
                        </div>
                </div>
                <div class = "yourprod_clear"><br /><br /></div><br />	
            </div>
        </c:forEach>
        
    </body>
</html>