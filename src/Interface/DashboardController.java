/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Stages.Stages;
import Utilities.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class DashboardController implements Initializable {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private Stage stage;
    @FXML
    private BorderPane bpane;
    @FXML
    private MenuItem scan_id;
    private Stage _ScStage1;
    private Stage _ScStage2;
    @FXML
    private PasswordField admin_pass;
    @FXML
    private MenuBar men_bar;
    @FXML
    private Label trigger;
    @FXML
    private Label promt_text;
    @FXML
    private Button go;
    @FXML
    private Text l1;
    @FXML
    private Label lx1;
    @FXML
    private Label lx2;
    @FXML
    private Label lx3;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lx1.setVisible(false);
        lx2.setVisible(false);
        lx3.setVisible(false);
        men_bar.setVisible(false);
        promt_text.setVisible(false);
        go.setVisible(false);

        lx1.setText(DateFormat.getDateInstance().format(new java.util.Date()));
        Date now = new Date();
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
        lx2.setText(simpleDateformat.format(now).toUpperCase());

        bindToTime();
        //this.stage.setOnCloseRequest(e -> Platform.exit());
        /*try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/Interface/_Login.fxml"));
            bpane.getChildren().addAll(pane);
            pane.setCenterShape(true);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    public void bindToTime() {
        Timeline clock = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            LocalTime currentTime = LocalTime.now();
            lx3.setText(currentTime.plusHours(1).format(formatter));
        }),
                new KeyFrame(javafx.util.Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    @FXML
    private void init_scanner(ActionEvent event) throws IOException {
        if (_ScStage1 == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interface/_Scanner.fxml"));
            Parent r1 = (Parent) loader.load();
            _ScStage1 = new Stage();
            _ScStage1.setScene(new Scene(r1));
            _ScStage1.initStyle(StageStyle.DECORATED);
            _ScStage1.setResizable(false);
            _ScStage1.show();
        } else if (_ScStage1.isShowing() || _ScStage1.isIconified()) {
            _ScStage1.toFront();
        } else {
            _ScStage1.show();
        }

    }

    @FXML
    private void trav_hist(ActionEvent event) throws IOException {
        if (_ScStage2 == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interface/_Trav_Hist.fxml"));
            Parent r1 = (Parent) loader.load();
            _ScStage2 = new Stage();
            _ScStage2.setScene(new Scene(r1));
            _ScStage2.initStyle(StageStyle.DECORATED);
            _ScStage2.setResizable(false);
            Platform.setImplicitExit(true);
            _ScStage2.show();
        } else if (_ScStage2.isShowing()) {
            _ScStage2.toFront();
        } else if (_ScStage2.isIconified()) {
            System.out.println("Performed!");
            _ScStage2.toFront();
        } else {
            _ScStage2.show();
        }
        _ScStage2.setOnCloseRequest((t) -> {
            _Trav_HistController th = new _Trav_HistController();
            System.out.println("CLOSED!");
            th.timers = new Timer(true);
            th.timers.cancel();
            //com.sun.javafx.application.PlatformImpl.tkExit();
            //System.exit(1);

        });
    }

    private void signIn() throws Exception {
        String _password = admin_pass.getText().trim();
        String pass;
        conn = (Connection) DBConnection.Database_Con();
        if (checkifEmpty(_password)) {
            promt_text.setVisible(true);
            promt_text.setText("Please fill all the fields");
        } else {
            String in = "Select * from admin_table where pass = ?";
            try {
                pst = conn.prepareStatement(in);
                pst.setString(1, _password);
                rs = pst.executeQuery();
                if (rs.next()) {
                    pass = rs.getString("pass");
                    promt_text.setVisible(false);
                    men_bar.setVisible(true);
                    admin_pass.setVisible(false);
                    trigger.setVisible(false);
                    l1.setVisible(false);
                    lx1.setVisible(true);
                    lx2.setVisible(true);
                    lx3.setVisible(true);
                } else {
                    promt_text.setVisible(true);
                    promt_text.setText("Invalid Password!");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkifEmpty(String pass) {
        return admin_pass.getText().isEmpty();
    }

    @FXML
    private void fire(ActionEvent event) throws Exception {
        go.fire();
    }

    @FXML
    private void bira(ActionEvent event) throws Exception {
        signIn();
    }

    @FXML
    private void when_typed(KeyEvent event) {
        if (admin_pass.getText().isEmpty()) {
            promt_text.setVisible(true);
            promt_text.setText("Please fill all the fields");
        } else {
        }
    }

}
