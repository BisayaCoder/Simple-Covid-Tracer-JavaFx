/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PIDAO;

import Utilities.DBConnection;
import Utilities.Personal_Information;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Administrator
 */
public class Personal_Information_DAO {

    static Connection conn = null;
    static PreparedStatement pst = null;
    static ResultSet rs = null;

    private static ObservableList<Personal_Information> getPerObject(ResultSet rsz) throws ClassNotFoundException, SQLException {
        try {
            ObservableList<Personal_Information> perlist1 = FXCollections.observableArrayList();
            while (rsz.next()) {
                Personal_Information pi = new Personal_Information();
                pi.setFullname(rs.getString("fullname"));
                pi.setAge(Integer.parseInt(rs.getString("age")));
                pi.setGender(rs.getString("gender"));
                pi.setAddr(rs.getString("address") + " " + rs.getString("municipality") + ", " + rs.getString("province"));
                pi.setDatereg(rs.getString("date_reg"));
                pi.setHeathStat(rs.getString("rh_status"));
                perlist1.add(pi);
            }
            return perlist1;
        } catch (SQLException e) {
            throw e;
        }
    }

    private static ObservableList<Personal_Information> getPerObjectByDate(ResultSet rsz) throws ClassNotFoundException, SQLException {
        try {
            ObservableList<Personal_Information> perlist2 = FXCollections.observableArrayList();
            while (rsz.next()) {
                Personal_Information pi = new Personal_Information();
                pi.setFullname(rs.getString("fnm"));
                //pi.setAddr(rs.getString("address") + " " + rs.getString("municipality") + ", " + rs.getString("province"));
                //pi.setAge(Integer.parseInt(rs.getString("age")));
                //pi.setGender(rs.getString("gender"));
                pi.setTravelfrom(rs.getString("ffrom"));
                pi.setTravelto(rs.getString("tto"));
                pi.setReason(rs.getString("reason"));
                pi.setHeathStat(rs.getString("fhs"));
                perlist2.add(pi);
            }
            return perlist2;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static ObservableList<Personal_Information> SearchData(String _fullname) throws ClassNotFoundException, SQLException {

        String sql = "Select * from residence where fullname like '%" + _fullname + "%'";
        try {
            conn = (Connection) DBConnection.Database_Con();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            ObservableList<Personal_Information> list = getPerObject(rs);
            return list;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static ObservableList<Personal_Information> SearchDataByDate(String date_travel) throws ClassNotFoundException, SQLException {

        String sql = "Select * from travel_history where date_trav like '%"+ date_travel +"%'";
        try {
            conn = (Connection) DBConnection.Database_Con();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            ObservableList<Personal_Information> list = getPerObjectByDate(rs);
            return list;
        } catch (SQLException e) {
            System.err.println(e);
            throw e;
        }
    }
}
