/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Utilities.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class _LoginController implements Initializable {
    
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    @FXML
    private TextField fuser;
    @FXML
    private PasswordField fpass;
    @FXML
    private Button login;
    @FXML
    private Label prompt_user;
    @FXML
    private Label prompt_pass;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void sign_in(ActionEvent event) throws Exception {
        signIn();
    }
    
    private void signIn() throws Exception {
        String _username = fuser.getText();
        String _password = fpass.getText().trim();
        String pass;
        conn = (Connection) DBConnection.Database_Con();
        if (checkifEmpty(_username, _password)) {
            prompt_pass.setText("Please fill all the fields");
        } else {
            String in = "Select * from admin_table where usern = ?";
            try {
                pst = conn.prepareStatement(in);
                pst.setString(1, _username);
                rs = pst.executeQuery();
                if (rs.next()) {
                    pass = rs.getString("pass");
                    
                    if (_password.equals(pass)) {
                        System.out.println("Successfully Login!");
                        MainPanel();
                    } else {
                        System.out.println("Invalid Password!");
                    }
                } else {
                    System.out.println("Cannot find user!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private boolean checkifEmpty(String user, String pass) {
        return fuser.getText().isEmpty() || fpass.getText().isEmpty();
    }
    
    private void MainPanel() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interface/Dashboard.fxml"));
            Parent r1 = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Main Panel");
            stage.setScene(new Scene(r1));
            stage.initStyle(StageStyle.DECORATED);
            stage.setResizable(false);
            stage.show();
           // stage.setOnCloseRequest(e -> Platform.exit());
            stage = (Stage) login.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            throw e;
        }
    }
}
