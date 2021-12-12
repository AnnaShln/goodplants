package com.example.goodplants;

import java.io.*;
import java.sql.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


@WebServlet(name = "allPlantsServlet", value = "/allplants-servlet")
public class AllPlantsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            /*BufferedReader reader = new BufferedReader(new FileReader("./files/properties.txt"));
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
            String sql = "select plant.id, name, family, plant.desc, img, water, sun, care, toxicity " +
                    "from plant, water, sun, care, toxicity " +
                    "where water_id = water.id and sun_id = sun.id and care_id = care.id and toxicity_id = toxicity.id;";
            ResultSet rs = stmt.executeQuery(sql);
            StringBuilder allPlants = new StringBuilder();
            while (rs.next()) {
                allPlants.append(rs.getString("id")).append(";").append(rs.getString("name"))
                        .append(";").append(rs.getString("family"))
                        .append(";").append(rs.getString("desc"))
                        .append(";").append(rs.getString("img"))
                        .append(";").append(rs.getString("water"))
                        .append(";").append(rs.getString("sun"))
                        .append(";").append(rs.getString("care"))
                        .append(";").append(rs.getString("toxicity")).append("|");
            }
            rs.close();
            stmt.close();
            conn.close();
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            allPlants.setLength(allPlants.length() - 1);
            writer.write(allPlants.toString());
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}