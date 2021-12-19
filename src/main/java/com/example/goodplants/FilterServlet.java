package com.example.goodplants;

import java.io.*;
import java.sql.*;
import java.util.Objects;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


@WebServlet(name = "filterServlet", value = "/filter-servlet")  //послении версии - пути через value

public class FilterServlet extends HttpServlet {

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
            String all = request.getParameter("allPlants");  //true / false - вывод всех данных или нет
            String water_fil = request.getParameter("filterWater"); //передача значений radiobutton (передает все параметры по кнопкам)
            String care_fil = request.getParameter("filterCare");
            String sun_fil = request.getParameter("filterSun");
            String toxicity_fil = request.getParameter("filterToxicity");
            String plant_id = request.getParameter("plantId"); //для вывода информации по popup
            String sql_text;
            String condition = " ";
            if(Objects.equals(all, "true")) {
                if (plant_id != null){
                    sql_text = "select plant.id, name, family, plant.desc, img, water, sun, care, toxicity " +
                            "from plant, water, sun, care, toxicity " +
                            "where water_id = water.id and sun_id = sun.id and care_id = care.id and toxicity_id = toxicity.id " +
                            "and plant.id = "+ plant_id +";";
                }else
                sql_text = "select plant.id, name, family, plant.desc, img, water, sun, care, toxicity " +
                        "from plant, water, sun, care, toxicity " +
                        "where water_id = water.id and sun_id = sun.id and care_id = care.id and toxicity_id = toxicity.id;";
            } else {
                if (water_fil != null){
                    condition += " and water_id = " + water_fil;
                }
                if (care_fil != null){
                    condition += " and care_id = " + care_fil;
                }
                if (sun_fil != null){
                    condition += " and sun_id = " + sun_fil;
                }
                if (toxicity_fil != null){
                    condition += " and toxicity_id = " + toxicity_fil;
                }
                sql_text = "select plant.id, name, family, plant.desc, img, water, sun, care, toxicity " +
                        "from plant, water, sun, care, toxicity " +
                        "where water_id = water.id and sun_id = sun.id and care_id = care.id and toxicity_id = toxicity.id"
                + condition + ";";
            }
            ResultSet rs = stmt.executeQuery(sql_text);
            StringBuilder allPlants = new StringBuilder();
            if (!rs.isBeforeFirst() ) {
                allPlants.append("null");
            } else {

                while (rs.next()) {
                    allPlants.append(rs.getString("id")).append(";").append(rs.getString("name"))  //вывод полученных из бд данных строкой, потом разбивка по точке с запятой
                            .append(";").append(rs.getString("family"))
                            .append(";").append(rs.getString("desc"))
                            .append(";").append(rs.getString("img"))
                            .append(";").append(rs.getString("water"))
                            .append(";").append(rs.getString("sun"))
                            .append(";").append(rs.getString("care"))
                            .append(";").append(rs.getString("toxicity")).append("|");
                }
                rs.close();
                allPlants.setLength(allPlants.length() - 1);

            }
            stmt.close();
            conn.close();
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(allPlants.toString());
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
