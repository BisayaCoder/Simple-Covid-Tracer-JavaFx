/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Administrator
 */
public class Personal_Information {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    private String fullname;
    private int age;
    private String gender;
    private String addr;
    private String datereg;
    private String heathStat;
    private String travelfrom;
    private String travelto;
    private String reason;

    public Personal_Information() {
    }

    public Personal_Information(String fullname, int age, String gender, String addr, String datereg, String heathStat, String travelfrom, String travelto, String reason) {
        this.fullname = fullname;
        this.age = age;
        this.gender = gender;
        this.addr = addr;
        this.datereg = datereg;
        this.heathStat = heathStat;
        this.travelfrom = travelfrom;
        this.travelto = travelto;
        this.reason = reason;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public PreparedStatement getPst() {
        return pst;
    }

    public void setPst(PreparedStatement pst) {
        this.pst = pst;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getDatereg() {
        return datereg;
    }

    public void setDatereg(String datereg) {
        this.datereg = datereg;
    }

    public String getHeathStat() {
        return heathStat;
    }

    public void setHeathStat(String heathStat) {
        this.heathStat = heathStat;
    }

    public String getTravelfrom() {
        return travelfrom;
    }

    public void setTravelfrom(String travelfrom) {
        this.travelfrom = travelfrom;
    }

    public String getTravelto() {
        return travelto;
    }

    public void setTravelto(String travelto) {
        this.travelto = travelto;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    
}
