/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Utilities.DBConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class _ScannerController implements Initializable {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private TextField fname;
    @FXML
    private TextField fage;
    @FXML
    private TextField fgender;
    @FXML
    private TextField fadd;
    @FXML
    private TextField fhs;
    @FXML
    private ImageView imgview;
    @FXML
    private TextField ctrlno;
    @FXML
    private TextField ffrom;
    @FXML
    private TextField fto;
    @FXML
    private ComboBox<String> fhs_now;
    @FXML
    private ComboBox<String> fhslist;
    @FXML
    private Separator l1;
    @FXML
    private Separator l2;
    @FXML
    private TextField freason;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        fhslist.setVisible(false);
        l1.setVisible(false);
        l2.setVisible(false);
        ObservableList<String> values = FXCollections.observableArrayList("STABLE", "NON-STABLE");
        fhs_now.setItems(values);

        bindToTime();
        date.setText(DateFormat.getDateInstance().format(new java.util.Date()));
        Date now = new Date();
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
        simpleDateformat = new SimpleDateFormat("E");
        System.out.println(simpleDateformat.format(now));
    }

    private void loadData() throws Exception {
        conn = (Connection) DBConnection.Database_Con();
        String _ctrl = ctrlno.getText();
        String sql = "Select * from residence where ctrl_id ='" + _ctrl + "'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                fname.setText(rs.getString("fullname").toUpperCase());
                fage.setText(rs.getString("age").toUpperCase());
                fgender.setText(rs.getString("gender").toUpperCase());
                fadd.setText(rs.getString("address") + " " + rs.getString("municipality") + ", " + rs.getString("province").toUpperCase());
                fhs.setText(rs.getString("rh_status").toUpperCase());
                System.out.println(rs.getString("rh_status").toUpperCase());
            } else {
                System.out.println("NO DATA FOUND!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadtoHist() throws Exception {
        conn = (Connection) DBConnection.Database_Con();
        String cn = ctrlno.getText();
        String loadname = fname.getText();
        String from = ffrom.getText();
        String to = fto.getText();
        String reason = freason.getText();
        String sql = "Insert into travel_history (cn, fnm, ffrom, tto, reason, fhs, date_trav) values (?,?,?,?,?,?,now())";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, cn);
            pst.setString(2, loadname);
            pst.setString(3, from);
            pst.setString(4, to);
            pst.setString(5, reason);
            if (fhs_now.getSelectionModel().isSelected(0)) {
                pst.setString(6, fhs_now.getSelectionModel().getSelectedItem());
            } else if (fhs_now.getSelectionModel().isSelected(1)) {
                pst.setString(6, fhslist.getSelectionModel().getSelectedItem());
            }
            pst.execute();
            System.out.println("Data Addedd Successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void bindToTime() {
        //System.out.println(new SimpleDateFormat("hh:mm a").format(new Date()));
        Timeline clock = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            LocalTime currentTime = LocalTime.now();
            time.setText(currentTime.plusHours(1).format(formatter));
            //System.out.println(currentTime.plusHours(1).format(formatter));
            //String ampmString = Calendar.AM_PM == Calendar.AM ? "AM" : "PM";
            //String final_time = currentTime.getHour() + 1 + "" + ":" + currentTime.getMinute() + ":" + currentTime.getSecond() + " " + ampmString;
            //System.out.println(final_time);
            //time.setText(currentTime.getHour() + 1 + "" + ":" + currentTime.getMinute() + ":" + currentTime.getSecond() + " " + ampmString);
        }),
                new KeyFrame(javafx.util.Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        /*Timeline timeline = new Timeline(
                new KeyFrame(javafx.util.Duration.seconds(0), (ActionEvent actionEvent) -> {
                    Calendar time1 = Calendar.getInstance();
                    String hourString = StringUtilities.pad(2, ' ', time1.get(Calendar.HOUR) == 0 ? "12" : time1.get(Calendar.HOUR) + "");
                    String minuteString = StringUtilities.pad(2, '0', time1.get(Calendar.MINUTE) + "");
                    String secondString = StringUtilities.pad(2, '0', time1.get(Calendar.SECOND) + "");
                    String ampmString = time1.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
                    time.setText(hourString + ":" + minuteString + ":" + secondString + " " + ampmString);
                }),
                new KeyFrame(javafx.util.Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();*/
    }

    @FXML
    private void select_stat(ActionEvent event) {
        if (fhs_now.getSelectionModel().isSelected(1)) {
            ObservableList<String> non_slist = FXCollections.observableArrayList(
                    "Ubo",
                    "Sip-on",
                    "Hilanat/Kalintura");
            l1.setVisible(true);
            l2.setVisible(true);
            fhslist.setVisible(true);
            fhslist.setItems(non_slist);
            fhslist.getSelectionModel().selectFirst();
        } else {
            fhslist.setVisible(false);
            l1.setVisible(false);
            l2.setVisible(false);
        }
    }

    @FXML
    private void on_search(KeyEvent event) throws Exception {
        if (ctrlno.getText().isEmpty()) {
            fname.setText("");
            fage.setText("");
            fgender.setText("");
            fadd.setText("");
            fhs.setText("");
        } else {
            loadData();
        }
    }

    @FXML
    private void load_data(ActionEvent event) throws Exception {
        loadtoHist();
    }
}

class StringUtilities {

    public static String pad(int fieldWidth, char padChar, String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = s.length(); i < fieldWidth; i++) {
            sb.append(padChar);
        }
        sb.append(s);

        return sb.toString();
    }
}
