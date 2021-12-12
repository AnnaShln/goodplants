package com.example.goodplants;

import java.io.*;
import java.sql.*;
import java.util.Objects;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "createPlantServlet", value = "/createPlant-servlet")
public class CreatePlantServlet extends HttpServlet {

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
            String name = request.getParameter("name");
            String family = request.getParameter("family");
            String desc = request.getParameter("desc");
            String img = request.getParameter("img");
            String water = request.getParameter("water");
            String sun = request.getParameter("sun");
            String care = request.getParameter("care");
            String toxicity = request.getParameter("toxicity");
            String sql_text = "INSERT INTO plant (name, family, plant.desc, img, water_id, sun_id, care_id, toxicity_id)" +
                    " VALUES ('" + name + "', '" + family + "', '" + desc + "', '" + img + "'," + water + "," + sun + "," + care +
                    "," + toxicity + ");";
            stmt.executeUpdate(sql_text);
            stmt.close();
            conn.close();
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
