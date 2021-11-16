/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import PIDAO.Personal_Information_DAO;
import Utilities.DBConnection;
import Utilities.Personal_Information;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class _Trav_HistController implements Initializable {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private Thread thread;
    @FXML
    private Label Lstable;
    public Timer timers;
    @FXML
    private MenuItem help_butt;
    @FXML
    private TableColumn<?, ?> _fname;
    @FXML
    private TableColumn<?, ?> _fadd;
    @FXML
    private TableColumn<?, ?> f_age;
    @FXML
    private TableColumn<?, ?> _fgender;
    @FXML
    private TableColumn<?, ?> _fhs;
    @FXML
    private TableColumn<?, ?> _ffrom;
    @FXML
    private TableColumn<?, ?> _fto;
    @FXML
    private TableColumn<?, ?> _freason;
    @FXML
    private TableView<Personal_Information> tableView;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField rec;

    ObservableList<Personal_Information> Information = FXCollections.observableArrayList();
    @FXML
    private TextField search;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rec.setVisible(false);
        _fname.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        //f_age.setCellValueFactory(new PropertyValueFactory<>("age"));
        //_fgender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        //_fadd.setCellValueFactory(new PropertyValueFactory<>("addr"));
        _fhs.setCellValueFactory(new PropertyValueFactory<>("heathStat"));
        _ffrom.setCellValueFactory(new PropertyValueFactory<>("travelfrom"));
        _fto.setCellValueFactory(new PropertyValueFactory<>("travelto"));
        _freason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        populateTable(Information);

        try {
            timerStart();
        } catch (IllegalAccessException | InstantiationException | InterruptedException ex) {
            Logger.getLogger(_Trav_HistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void populateTable(ObservableList<Personal_Information> information) {
        tableView.setItems(information);
    }

    public void timerStart() throws InterruptedException, InstantiationException, IllegalAccessException {
        timers = new Timer();
        timers.schedule(
                new TimerTask() {
            @Override
            public void run() {
                try {
                    CountStable();
                    //System.out.println("EXECUTED!");
                } catch (Exception ex) {
                    Logger.getLogger(_Trav_HistController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }, 0, 1000);
    }

    private void CountStable() throws Exception {
        conn = (Connection) DBConnection.Database_Con();
        String sql = "Select count(fhs) from travel_history where fhs ='STABLE'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                Lstable.setText(String.valueOf(rs.getInt(1)));
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    @FXML
    private void help(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("READ MORE INFORMATION BELOW");
        /*alert.setContentText("Under sa NON-STABLE na health Status\n"
                + "Naa draa ang Ubo, Sip-on og Hilanat o Kalintura\n"
                + "gina count na pila matag case");*/
        alert.setContentText("Click File->Search by date\n"
                + "under ana kay isearch tanan data through date\n"
                + "makita kinsay nag travel ana nga DATE og ila tanan data\n"
                + "mulabas ang calendar dra sa upper left dra ka mag search via date.\n\n"
                + "Click File -> Search by Health Status\n"
                + "pag iClick na' nimo naa mulabas 2 ka pilianan the\n"
                + "STABLE and NON-STABLE na checkbox,\n"
                + "ma search nimo draa tanan data kung kinsay STABLE og NON-STABLE\n"
                + "Note: Under sa NON-STABLE na health Status\n"
                + "Naa draa ang Ubo, Sip-on og Hilanat o Kalintura\n"
                + "gina count na pila matag case, see on the upper right corner.");
        alert.show();
    }

    //Calendar converter from yyyy/MM/dd to yyyy-MM-dd
    @FXML
    private void toConvert(ActionEvent event) throws ClassNotFoundException, SQLException {
        String pattern = "yyyy-MM-dd";
        datePicker.setPromptText(pattern.toLowerCase());
        datePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        rec.setText(String.valueOf(datePicker.getValue()));
        System.out.println(rec.getText());
        ObservableList<Personal_Information> list = Personal_Information_DAO.SearchData(rec.getText());
        if (list.size() > 0) {
            populateTable(list);
        } else {
            System.out.println("No data found!");
            tableView.refresh();
        }
    }

    @FXML
    private void ngita(KeyEvent event) throws ClassNotFoundException, SQLException {
        ObservableList<Personal_Information> list = Personal_Information_DAO.SearchData(search.getText());
        if (list.size() > 0) {
            populateTable(list);
        } else {
            System.out.println("No data found!");
            tableView.refresh();
        }
    }

}
