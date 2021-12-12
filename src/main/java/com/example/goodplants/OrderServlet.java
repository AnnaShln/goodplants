package com.example.goodplants;

import java.io.*;
import java.sql.*;
import java.util.Objects;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "orderServlet", value = "/order-servlet")
public class OrderServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            /*BufferedReader reader = new BufferedReader(new FileReader("../files/properties.txt"));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String[] properties = sb.toString().split(",");*/
            Class.forName("com.mysql.jdbc.Driver");
            final String user = "root";
            final String pass = "santaleo1402";
            final String DB_URL = "jdbc:mysql://localhost/goodplant";
            Connection conn = DriverManager.getConnection(DB_URL, user, pass);
            Statement stmt = conn.createStatement();
            String sql_text = "select orders.id, orders.name, phone, datetime, plant.name as plant " +
                        "from orders, plant " +
                        "where plant_id = plant.id;";
            ResultSet rs = stmt.executeQuery(sql_text);
            StringBuilder allOrders = new StringBuilder();
            if (!rs.isBeforeFirst() ) {
                allOrders.append("null");
            } else {
                while (rs.next()) {
                    allOrders.append(rs.getString("id")).append(";").append(rs.getString("name"))
                            .append(";").append(rs.getString("phone"))
                            .append(";").append(rs.getString("datetime"))
                            .append(";").append(rs.getString("plant")).append("|");
                }
                rs.close();
                allOrders.setLength(allOrders.length() - 1);
            }
            stmt.close();
            conn.close();
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(allOrders.toString());
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
