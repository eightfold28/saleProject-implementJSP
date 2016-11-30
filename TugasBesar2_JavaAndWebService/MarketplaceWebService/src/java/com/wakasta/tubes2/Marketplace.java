/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakasta.tubes2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author rmxhaha
 */
@WebService(serviceName = "Marketplace")
public class Marketplace {
    /*
    @WebMethod(operationName = "extractBytes")
    public byte[] extractBytes (String ImageName) throws IOException {
        // open image
        File imgPath = new File(ImageName);
        BufferedImage bufferedImage = ImageIO.read(imgPath);

        // get DataBufferBytes from Raster
        WritableRaster raster = bufferedImage .getRaster();
        DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();

        return ( data.getData() );
   }
    */
    @WebMethod(operationName = "addProduct")
    public int addProduct(
            @WebParam(name = "token") String access_token,
            @WebParam(name = "product_name") String name,
            @WebParam(name = "product_description") String description,
            @WebParam(name = "product_price") int price,
            @WebParam(name = "product_image") byte[] image, // image di encode base64 yah
            @WebParam(name = "product_owner") String owner) throws Exception
    {
        try {
            //CONNECT SQL
            Connection con = ConnectSQL.getConnection();

            String sql = "INSERT INTO item (item_name, item_desc, item_price, item_image, "
                    + "item_owner) VALUES (?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setInt(3, price);
            stmt.setBytes(4, image);
            stmt.setString(5, owner);
            
            int result = stmt.executeUpdate();
            if (result == 1) {
                return 1;
            } else {
                return 0;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @WebMethod(operationName = "deleteProduct")
    public int deleteProduct(
            @WebParam(name = "token") String access_token,
            @WebParam(name = "product_id") int id) throws Exception {
            try {
                //CONNECT SQL
                Connection con = ConnectSQL.getConnection();
                String sql = "UPDATE item SET isDeleted=1 WHERE item_ID=?;";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, id);
                int result = stmt.executeUpdate();
                if (result == 1) {
                    return 1;
                } else {
                    return 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
    }

    @WebMethod(operationName = "updateProduct")
    public int updateProduct(
            @WebParam(name = "token") String access_token,
            @WebParam(name = "product_id") int id,
            @WebParam(name = "product_name") String name,
            @WebParam(name = "product_description") String description,
            @WebParam(name = "product_price") int price) throws Exception {
        
        try {
                //CONNECT SQL
                Connection con = ConnectSQL.getConnection();
                PreparedStatement stmt = con.prepareStatement("UPDATE item SET item_name = ?, item_desc = ?, item_price = ? WHERE item_ID = ?");
                
                stmt.setString(1, name);
                stmt.setString(2, description);
                stmt.setInt(3, price);
                stmt.setString(4, access_token);
              
                int result = stmt.executeUpdate();
                stmt.close();
                if (result == 1) {
                    return 1;
                } else {
                    return 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        
//        return 0;
    }

    @WebMethod(operationName = "getYourProducts")
    public Product[] getYourProducts(@WebParam(name = "token") String access_token) throws Exception {
        try {
            //CONNECT SQL
            Connection con = ConnectSQL.getConnection();

            Statement statement1 = con.createStatement();
            ResultSet resultSet = statement1.executeQuery("SELECT \n"
                    + "	item.item_ID AS item_ID,\n"
                    + "    item.item_name AS item_name,\n"
                    + "    item.item_desc AS item_desc,\n"
                    + "    FORMAT(item.item_price, 0) item_price,\n"
                    + "    item.item_purchases AS item_purchases,\n"
                    + "    item.item_image AS item_image,\n"
                    + "    user.Username AS item_owner,\n"
                    + "    DATE_FORMAT(item.date_added, '%W, %e %M %Y') dateadd,\n"
                    + "    DATE_FORMAT(item.date_added, '%H.%i') time_added,\n"
                    + "    item.isDeleted AS isDeleted,\n"
                    + "    COUNT(*) AS jumlahlikes\n"
                    + "FROM\n"
                    + "	item LEFT OUTER JOIN likes ON item.item_ID = likes.item_ID\n"
                    + "	LEFT OUTER JOIN user ON user.active_ID = item.item_owner\n"
                    + "WHERE\n"
                    + "	(isDeleted IS NULL or isDeleted=0) and item_owner='" + access_token + "'\n"
                    + "GROUP BY\n"
                    + "	item.item_ID");
            ArrayList<Product> products = new ArrayList<Product>();
            while (resultSet.next()) {
                Product p = new Product();
                p.id = resultSet.getInt("item_ID");
                p.name = resultSet.getString("item_name");
                p.like_count = resultSet.getInt("jumlahlikes");
                p.purchase_count = resultSet.getInt("item_purchases");
                p.description = resultSet.getString("item_desc");
                p.price = resultSet.getInt("item_price");
                p.date_add = resultSet.getString("dateadd");
                p.time_add = resultSet.getString("time_added");
                p.owner = resultSet.getString("item_owner");
                p.image = resultSet.getBytes("item_image");
                products.add(p);
            }
            Product[] prod = new Product[products.size()];
            for (int i = 0; i < products.size(); i++) {
                prod[i] = products.get(i);
            }
            return prod;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @WebMethod(operationName = "getLike")
    public int getLike(@WebParam(name = "token") String access_token, @WebParam(name = "product_id") int product_id) throws Exception {
        Connection con = ConnectSQL.getConnection();
        Statement statement1;
        try {
            statement1 = con.createStatement();
            ResultSet resultSet = statement1.executeQuery("SELECT COUNT(*) FROM likes WHERE item_ID ='" + product_id + "'");
            if (resultSet.next()) {
                int jlhlike = resultSet.getInt("count(*)");
                return jlhlike;
            } else {
                return 0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
    
    @WebMethod(operationName = "like")
    public int like(@WebParam(name = "token") String access_token, @WebParam(name = "product_id") int product_id) throws Exception {
        try {
            //CONNECT SQL
            Connection con = ConnectSQL.getConnection();

            PreparedStatement statement1 = con.prepareStatement("INSERT INTO likes VALUES (?,?)");
            statement1.setString(1, access_token);
            statement1.setInt(2, product_id);
            
            int result= statement1.executeUpdate();
            statement1.close();
            if (result == 1) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    @WebMethod(operationName = "unlike")
    public int unlike(@WebParam(name = "token") String access_token, @WebParam(name = "product_id") int product_id) throws Exception {
        try {
            //CONNECT SQL
            Connection con = ConnectSQL.getConnection();

            PreparedStatement statement1 = con.prepareStatement("DELETE FROM likes WHERE (active_ID = ? AND item_ID = ?)");
            statement1.setString(1, access_token);
            statement1.setInt(2, product_id);
            
            int result= statement1.executeUpdate();
            statement1.close();
            if (result == 1) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    @WebMethod(operationName = "purchase")
    public int purchase(
            @WebParam(name = "token") String access_token,
            @WebParam(name = "product_id") int product_id,
            @WebParam(name = "quantity") int quantity,
            @WebParam(name = "consignee") String consignee,
            @WebParam(name = "full_address") String full_address,
            @WebParam(name = "postal_code") int postal_code,
            @WebParam(name = "phone_number") int phone_number) throws Exception {
//            @WebParam(name = "credit_card_number") int credit_card_number,
//            @WebParam(name = "credit_card_verification") int credit_card_verification) 
        
        try {
            //CONNECT SQL
            Connection con = ConnectSQL.getConnection();
            String sql = "UPDATE item SET item_purchases = item_purchases + 1 WHERE item_id = '" + product_id + "'";
            
            Statement StmtProd = con.createStatement(); 
            ResultSet res = StmtProd.executeQuery("SELECT item_name, item_price FROM item WHERE item_id = '" + product_id + "'");
            res.next();
            String product_name = res.getString("item_name");
            int product_price = res.getInt("item_price");
            
            String sql1 = "INSERT INTO purchase (item_ID, item_name, item_price, cust_ID, deliv_name, deliv_address, deliv_postalcode, deliv_phone, quantity) VALUES (?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement stmt = con.prepareStatement(sql);
            PreparedStatement stmt1 = con.prepareStatement(sql1);
            stmt1.setInt(1, product_id);
            stmt1.setString(2, product_name);
            stmt1.setInt(3, product_price);
            stmt1.setString(4, access_token);
            stmt1.setString(5,  consignee);
            stmt1.setString(6, full_address);
            stmt1.setInt(7, postal_code);
            stmt1.setInt(8, phone_number);
            stmt1.setInt(9, quantity);
            int result = stmt.executeUpdate();
            int result1 = stmt1.executeUpdate();
            if (result == 1 && result1 == 1) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @WebMethod(operationName = "getPurchaseHistory")
    public Transaction[] getPurchaseHistory(
            @WebParam(name = "token") String access_token
    ) throws Exception {
        try {
            //CONNECT SQL
            Connection con = ConnectSQL.getConnection();
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT item_name, item_price, Username, "
                    + "quantity, deliv_name, deliv_address, deliv_postalcode, "
                    + "DATE_FORMAT(purchase.order_date, '%W, %e %M %Y') dateadd,"
                    + "DATE_FORMAT(purchase.order_date, '%H.%i') time_added, deliv_phone\n" +
                    "FROM purchase JOIN user ON purchase.cust_ID = user.active_ID\n" +
                    "WHERE cust_ID = '" + access_token + "'");
            ArrayList<Transaction> transactions = new ArrayList<Transaction>();
            while (res.next()) {
                Transaction t = new Transaction();
                t.product_name = res.getString("item_name");
                t.product_price = res.getInt("item_price");
                t.buyer_username = res.getString("Username");
                t.quantity = res.getInt("quantity");
                t.consignee = res.getString("deliv_name");
                t.full_address = res.getString("deliv_address");
                t.postal_code = res.getInt("deliv_postalcode");
                t.phone_number = res.getString("deliv_phone");
                t.date_add = res.getString("dateadd");
                t.time_add = res.getString("time_added");
                transactions.add(t);
            }
            Transaction[] trans = new Transaction[transactions.size()];
            for (int i = 0; i < transactions.size(); i++) {
                trans[i] = transactions.get(i);
            }
            return trans;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @WebMethod(operationName = "getSalesHistory")
    public Transaction[] getSalesHistory(
            @WebParam(name = "token") String access_token
    ) throws Exception {
        try {
            //CONNECT SQL
            Connection con = ConnectSQL.getConnection();
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT purchase.item_name, "
                    + "purchase.item_price, Username, quantity, deliv_name, "
                    + "deliv_address, deliv_postalcode, "
                    + "DATE_FORMAT(purchase.order_date, '%W, %e %M %Y') dateadd,"
                    + "DATE_FORMAT(purchase.order_date, '%H.%i') time_added, deliv_phone\n" +
                    "FROM (purchase join item ON item.item_ID = purchase.item_ID) "
                    + "JOIN user ON purchase.cust_ID = user.active_ID \n" +
                    "WHERE item_owner = '" + access_token + "'");
            ArrayList<Transaction> transactions = new ArrayList<Transaction>();
            while (res.next()) {
                Transaction t = new Transaction();
                t.product_name = res.getString("item_name");
                t.product_price = res.getInt("item_price");
                t.buyer_username = res.getString("Username");
                t.quantity = res.getInt("quantity");
                t.consignee = res.getString("deliv_name");
                t.full_address = res.getString("deliv_address");
                t.postal_code = res.getInt("deliv_postalcode");
                t.phone_number = res.getString("deliv_phone");
                t.date_add = res.getString("dateadd");
                t.time_add = res.getString("time_added");
                transactions.add(t);
            }
            Transaction[] trans = new Transaction[transactions.size()];
            for (int i = 0; i < transactions.size(); i++) {
                trans[i] = transactions.get(i);
            }
            return trans;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @WebMethod(operationName = "getCatalog")
    public Product[] getCatalog(@WebParam(name = "token") String access_token, 
            @WebParam(name = "keyword") String keyword, 
            @WebParam(name = "prodOrStore") String prodOrStore) throws Exception {
        if (keyword == null) {
            keyword = "";
        }
        if (prodOrStore == null) {
            prodOrStore = "";
        }
        try {

            //CONNECT SQL
            Connection con = ConnectSQL.getConnection();

            Statement statement1 = con.createStatement();
            ResultSet resSet;
            if (prodOrStore.equals("product")) {
                resSet = statement1.executeQuery("SELECT \n"
                        + "	item.item_ID AS item_ID,\n"
                        + "    item.item_name AS item_name,\n"
                        + "    item.item_desc AS item_desc,\n"
                        + "    FORMAT(item.item_price, 0) item_price,\n"
                        + "    item.item_purchases AS item_purchases,\n"
                        + "    item.item_image AS item_image,\n"
                        + "    user.Username AS item_owner,\n"
                        + "    DATE_FORMAT(item.date_added, '%W, %e %M %Y') dateadd,\n"
                        + "    DATE_FORMAT(item.date_added, '%H.%i') time_added,\n"
                        + "    item.isDeleted AS isDeleted,\n"
                        + "    COUNT(*) AS jumlahlikes\n"
                        + "FROM\n"
                        + "	item LEFT OUTER JOIN likes ON item.item_ID = likes.item_ID\n"
                        + "	LEFT OUTER JOIN user ON user.active_ID = item.item_owner\n"
                        + "WHERE\n"
                        + "	(isDeleted IS NULL or isDeleted=0) and item_name LIKE '%" + keyword + "%'\n"
                        + "GROUP BY\n" + "item.item_ID");
            } else { //search by store
                resSet = statement1.executeQuery("SELECT \n"
                        + "	item.item_ID AS item_ID,\n"
                        + "    item.item_name AS item_name,\n"
                        + "    item.item_desc AS item_desc,\n"
                        + "    FORMAT(item.item_price, 0) item_price,\n"
                        + "    item.item_purchases AS item_purchases,\n"
                        + "    item.item_image AS item_image,\n"
                        + "    user.Username AS item_owner,\n"
                        + "    DATE_FORMAT(item.date_added, '%W, %e %M %Y') dateadd,\n"
                        + "    DATE_FORMAT(item.date_added, '%H.%i') time_added,\n"
                        + "    item.isDeleted AS isDeleted,\n"
                        + "    COUNT(*) AS jumlahlikes,\n"
                        + "    user.Username AS username\n"
                        + "FROM\n"
                        + "	((item INNER JOIN user ON item.item_owner = user.active_ID) LEFT OUTER JOIN likes ON item.item_ID = likes.item_ID)\n"
                        + "WHERE\n"
                        + "	(isDeleted IS NULL or isDeleted=0) and (username LIKE '%" + keyword + "%')\n"
                        + "GROUP BY\n" + "item.item_ID");
            }
            ArrayList<Product> products = new ArrayList<Product>();
            while (resSet.next()) {
                Product p = new Product();
                p.id = resSet.getInt("item_ID");
                p.name = resSet.getString("item_name");
                p.like_count = resSet.getInt("jumlahlikes");
                p.purchase_count = resSet.getInt("item_purchases");
                p.description = resSet.getString("item_desc");
                p.price = resSet.getInt("item_price");
                p.date_add = resSet.getString("dateadd");
                p.time_add = resSet.getString("time_added");
                p.owner = resSet.getString("item_owner");
                p.image = resSet.getBytes("item_image");
                products.add(p);
            }
            Product[] prod = new Product[products.size()];
            for (int i = 0; i < products.size(); i++) {
                prod[i] = products.get(i);
            }
            return prod;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @WebMethod(operationName = "getUserData")
    public User getUserData(@WebParam(name = "token") String access_token) throws Exception {
        try {
            //CONNECT SQL
            Connection con = ConnectSQL.getConnection();

            Statement statement1 = con.createStatement();
            ResultSet resSet;
            resSet = statement1.executeQuery("SELECT FullName, Username, Email, Password"
                    + "FullAddress, PostalCode, PhoneNumber FROM user WHERE active_ID = '" + access_token + "'");
            User u = new User();
            while (resSet.next()) {    
                u.fullname = resSet.getString("FullName");
                u.username = resSet.getString("Username");
                u.email = resSet.getString("Email");
                u.password = resSet.getString("Password");
                u.full_address = resSet.getString("FullAddress");
                u.postal_code = resSet.getInt("PostalCode");
                u.phone_number = resSet.getString("PhoneNumber");
            }
            return u;   
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }    
    
    @WebMethod(operationName = "getProductData")
    public Product getProductData(@WebParam(name = "product_id") int product_id) throws Exception{
        try {
            //CONNECT SQL
            Connection con = ConnectSQL.getConnection();

            Statement statement1 = con.createStatement();
            ResultSet resSet;
            resSet = statement1.executeQuery("SELECT item_ID, item_name, item_desc, item_price FROM item WHERE isDeleted = 0 AND item_ID = '" + product_id + "'");
            Product p = new Product();
            while (resSet.next()) {    
                p.id = resSet.getInt("item_ID");
                p.name = resSet.getString("item_name");
                p.description = resSet.getString("item_desc");
                p.price = resSet.getInt("item_price");
            }
            return p;   
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
