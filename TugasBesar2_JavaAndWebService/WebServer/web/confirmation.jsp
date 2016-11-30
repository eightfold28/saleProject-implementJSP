<%-- 
    Document   : confirmation
    Created on : Nov 14, 2016, 2:22:25 PM
    Author     : MaximaXL
--%>
<%
    com.wakasta.tubes2.Session s = new com.wakasta.tubes2.Session(request.getParameter("token"));
    if( s.invalid )
        response.sendRedirect("/");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">

	<title>SaleProject - Confirmation Purchase</title>
	
	<link rel="stylesheet" type="text/css" href="SaleProjectStyle.css">
</head>

<body>
        <jsp:include page="header.jsp" />
        
        <%-- start web service invocation --%>
        <%
        com.wakasta.tubes2.Product product = new com.wakasta.tubes2.Product();
        try {
            com.wakasta.tubes2.Marketplace_Service service = new com.wakasta.tubes2.Marketplace_Service();
            com.wakasta.tubes2.Marketplace port = service.getMarketplacePort();
             // TODO initialize WS operation arguments here
            int productId = Integer.parseInt(request.getParameter("item_id"));
            // TODO process result here
            product = port.getProductData(productId);
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        %>
        <%-- end web service invocation --%>


	<div class="submenu_header">
		<br>
		Please confirm your purchase
		<hr>
	</div>
        
	<form action="/PurchaseServlet" method="post" name="confirmation">
	<div class="confirmation_purchase">
		Product : <%= product.getName() %><br>
		Price 	: IDR <%= product.getPrice() %><br>
		Quantity : <input type="text" name = "qty" id="qty" value="1" style="width: 30px;" onkeyup="calculate(<%=product.getPrice()%>);"> pcs <br>
		Total Price : IDR  <input type="text" id="total" value=<%= product.getPrice() %> style="width:100" readonly/><br>
		Delivery to : <br>
		<br>
	</div>

		<div class="confirmation_purchase_form">
			<small>Consignee</small> <br>
			<input type="text" name="consignee" value="<%= s.user.getFullname() %>" ><br> <br>
			<small>Full Address</small> <br>
			<textarea name="full_address" cols="130" rows="2"><%= s.user.getFullAddress() %></textarea> <br> <br>
			<small>Postal Code</small> <br>
			<input type="text" name="postal_code" value="<%=s.user.getPostalCode()%>" ><br> <br>
			<small>Phone Number</small> <br>
			<input type="text" name="phone_number" value="<%=s.user.getPhoneNumber()%>" > <br> <br>
			<small>12 Digits Credit Card Number</small> <br>
			<input type="text" id="credit_card" name="credit_card" value=""> <br> <br>
			<small>3 Digits Card Verification Value</small> <br>
			<input type="text" id="card_verification" name="card_verification" value=""> <br> <br> <br>

			<input type="text" name="itemid" hidden="" value="${param.item_id}" />
			<input type="text" name="userid" hidden="" value="<%=s.user_id%>" >

			<input type="submit" name="cancel" value="CANCEL" onclick="history.go(-1);">
			<input type="submit" name="confirm_purchse" value="CONFIRM" onclick= "return validasi()" style="margin-right: 30px;"> <br> <br>
		</div>
	</form>

</body>
<script type="text/javascript" src="confirmation.js"></script>
</html>