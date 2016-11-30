<%-- 
    Document   : catalog
    Created on : Nov 13, 2016, 11:24:28 PM
    Author     : MaximaXL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html  ng-app="ChatBox">

    <head>
        <meta charset="utf-8">

        <title>SaleProject - Catalog</title>


        <!-- firebase stuff -->
        <script src="https://www.gstatic.com/firebasejs/3.6.1/firebase.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script>
          // Initialize Firebase
          var config = {
            apiKey: "AIzaSyDHFpxz-Vyadm-6ONVRjt0wy0ftoJQ__W0",
            authDomain: "tugas3-4f03a.firebaseapp.com",
            databaseURL: "https://tugas3-4f03a.firebaseio.com",
            storageBucket: "tugas3-4f03a.appspot.com",
            messagingSenderId: "758817827099"
          };
          firebase.initializeApp(config);
        </script>
        <script src="SaleProjectChatFirebase.js"></script>

        <!-- angular stuff -->
	<link rel="stylesheet" type="text/css" href="chatstyle.css">
        <script src="angular.min.js"></script>
	<script type="text/javascript" src="chat.js"></script>

        <link rel="stylesheet" type="text/css" href="SaleProjectStyle.css">
        
        
    </head>

    <body ng-controller = "chatController">
        <jsp:include page="header.jsp" >
            <jsp:param name="header_catalog_class" value="active" />
        </jsp:include>

        <div class="submenu_header">
            <br>
            What are you going to buy today?
            <hr />
        </div>

        <div class="search_bar">
            <form action=catalog.jsp method="post" >
                <input type="hidden" name="token" value="${param.token}" />
                  <input type="text" name="search_catalog" placeholder="Search catalog ...">
                <input type="submit" value="GO">
                <br> <br>
                by
                <input type="radio" name="search_method" value="product" checked style="margin-left: 50px"> product <br>
                <input type="radio" name="search_method" value="store" style="margin-left: 73px"> store
            </form>
        </div>
        <%-- start web service invocation --%>
        <%
        try {
            com.wakasta.tubes2.Marketplace_Service service = new com.wakasta.tubes2.Marketplace_Service();
            com.wakasta.tubes2.Marketplace port = service.getMarketplacePort();

            java.lang.String token = request.getParameter("token");
            java.lang.String keyword = request.getParameter("search_catalog");
            java.lang.String prodOrStore = request.getParameter("search_method");

            java.util.List<com.wakasta.tubes2.Product> result = port.getCatalog(token, keyword, prodOrStore);
            request.setAttribute("prod_list", result);   
        } catch (Exception ex) {
            // TODO handle custom exceptions here
            ex.printStackTrace();
        }
        %>
        <%-- end web service invocation --%>

        <div class="catalog_item">
            <c:if test="${prod_list.size() == 0}">
                <div class="catalog_item">0 result</div>;
            </c:if>
            <c:forEach var="prod" items="${prod_list}" varStatus="status">
                <br /> 
                <%@ page import="com.wakasta.tubes2.httpreq" %>
                <%@ page import="org.json.simple.parser.JSONParser" %>
                <%@ page import="org.json.simple.JSONObject" %>
                <%@ page import="com.wakasta.tubes2.Product" %>


                <%
                    Product p = (Product) pageContext.getAttribute("prod");
                    String data =  "username="+p.getOwner();
                    String res = httpreq.executePost("http://localhost:8080/IdentityService/CheckLoggedInServlet", data);
                    JSONParser parser = new JSONParser();
                    JSONObject obj = (JSONObject) parser.parse(res);
                    Long output = ((Long) obj.get("o"));
                    if( output == 1 )
                        out.print("<div class=online-icon></div>");
                    else
                        out.print("<div class=offline-icon></div>");
                        
                %>
                <div class="owner" ng-click="hideChat = false; startChat('${prod.owner}');">${prod.owner}</div>
                
                added this on ${prod.dateAdd}, at ${prod.timeAdd}<hr> 
                <br>
                <div class="product_image">
                    <img src ="data:image/jpeg;base64, ${prod.image}"/> 
                </div>
                <div class="name">${prod.name}</div>
                <div class="price"> IDR ${prod.price}</div>
                <div class="count">
                    ${prod.likeCount} likes <br>
                    ${prod.purchaseCount} purchases
                </div>
                <div class="detail">${prod.description}</div>
                <br>
                <div class="buybutton">
                    <a href=confirmation.jsp?token=${param.token}&item_id=${prod.id}> BUY </a>
                </div>
                <div class="likebutton" id="${prod.id}" >
                    <a href="#" return onclick="likecount(${prod.id}, ${s.user_id});return false;">LIKE</a>
                </div>
                <div class="clearfix"></div>
                <br>
                <br>
            </c:forEach>
        </div>

            <!-- <a href="#" ng-click="hideChat = !hideChat">Test</a> -->
            <div class = "chatbox" ng-hide="hideChat">
                <div class = "username">
                    <span id=chat_username>{{chat_with}}</span>
                    <a href="#" ng-click="closeChat()"><img ng-src = "cancel.png"></a>
                </div>
                
                <div class = "chatlogs">
                <hr>
                <br>
                        <div ng-repeat="x in messages">
                                <div ng-if="x.status == 'received'">
                                        <div class = "chat received">
                                                <div class = "messages">
                                                        {{x.msg}}
                                                </div>
                                        </div>
                                </div>
                                <div ng-if="x.status == 'sent'">
                                        <div class = "chat sent">
                                                <div class = "messages">
                                                        {{x.msg}}
                                                </div>
                                        </div>
                                </div>
                        </div>
                </div>

                <hr>

                <div class="chat-input">
                    <textarea id="newmsg" ng-model="newmsg"></textarea>
                    <button ng-click="sendMessage()">Kirim</button>
                </div>
            </div>
    </body>
    <script type="text/javascript" src="ceklike.js"></script>
</html>