/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author tibor_horvath
 */
public class DataManipulator {

    public void insertData(String name, int score) {
        Connection con = null;
        Statement st = null;
        try {
            con = ConnectionFactory.getConnection();
            assert con != null;
            st = con.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO HighScore (username, score) VALUES('" + name + "', " + score + ")";
        try {
            con.createStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public ArrayList getData() {
        ArrayList<User> users = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        
        try {
            con = ConnectionFactory.getConnection();
            assert con != null;
            st = con.createStatement();
            st.setMaxRows(10);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }

        String sql = ("SELECT * FROM HighScore ORDER BY score DESC ");
        ResultSet rs = null;
        try {
            assert st != null;
            rs = st.executeQuery(sql);
            
            assert rs != null;
            while (rs.next()) {
                String name = rs.getString("username");
                int score = rs.getInt("score");
                users.add(new User(name, score));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
