/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jnlp.PrintService;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 *
 * @author Administrator
 */
public class DBConnection {
    private static int port;
    private static String ip;
    private static String dbname;
    private static String username;
    private static String password;

    public static Connection conn;
    private static String val;

    public static Connection Database_Con() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            return conn;
        } else {
            //db pass = zEXEvnqPUVM7mzRn;
            try {
                Inet4Address addr = (Inet4Address) Inet4Address.getLocalHost();
                //System.out.println("Localhost address: " + addr.getHostAddress());
                String hostname = addr.getHostName();
                // System.out.println("Localhost name: " + hostname);
                getConfig();
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://";
                //System.out.println("URL:" + url);
                conn = (Connection) DriverManager.getConnection(url + ip + ":" + port + "/" + dbname, username, password);
                return conn;
            } catch (ClassNotFoundException | UnknownHostException | SQLException e) {
                System.err.println("Server Database is offline");
            }
            System.out.println("Cant connect to server!");
            return null;
        }
    }

    public static void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    public static ResultSet query(String query, boolean isUpdate, boolean verbose) throws SQLException {
        ResultSet rs = null;
        Statement stmnt = Database_Con().createStatement();
        if (verbose) {
            System.out.println("Executing query: " + query);
        }
        if (isUpdate) {
            stmnt.executeUpdate(query);
            if (verbose) {
                System.out.println("Successfully Executed Update Query!");
            }
        } else {
            rs = stmnt.executeQuery(query);
            if (verbose) {
                System.out.println("Successfully Executed Query!");
            }
        }
        return rs;
    }

    private static void getConfig() {
        try {
            Scanner scan = new Scanner(new File("DatabaseConfig.spt"));
            ip = scan.next();
            port = scan.nextInt();
            dbname = scan.next();
            username = scan.next();
            password = scan.next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
