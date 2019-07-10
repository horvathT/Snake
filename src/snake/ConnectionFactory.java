/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author tibor_horvath
 */
public class ConnectionFactory {

    private static Connection conn;

    private ConnectionFactory() {
    }

    public static Connection getConnection()
            throws ClassNotFoundException, SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/snake", "tanulo", "asd123");
        }
        return conn;
    }
}
