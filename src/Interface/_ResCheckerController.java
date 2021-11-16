/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import PIDAO.Personal_Information_DAO;
import Utilities.Personal_Information;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class _ResCheckerController implements Initializable {

    @FXML
    private TableView<Personal_Information> table_view;
    @FXML
    private TableColumn<?, ?> fname;
    @FXML
    private TableColumn<?, ?> fage;
    @FXML
    private TableColumn<?, ?> fgender;
    @FXML
    private TableColumn<?, ?> fadd;
    @FXML
    private TableColumn<?, ?> fdatereg;
    @FXML
    private TableColumn<?, ?> fhs;
    @FXML
    private TextField finder;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    ObservableList<Personal_Information> Information = FXCollections.observableArrayList();
    @FXML
    private Button search;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fname.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        fage.setCellValueFactory(new PropertyValueFactory<>("age"));
        fgender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        fadd.setCellValueFactory(new PropertyValueFactory<>("addr"));
        fdatereg.setCellValueFactory(new PropertyValueFactory<>("datereg"));
        fhs.setCellValueFactory(new PropertyValueFactory<>("heathStat"));
        populateTable(Information);
    }

    private void populateTable(ObservableList<Personal_Information> information) {
        table_view.setItems(information);
    }

    @FXML

    private void scan(ActionEvent event) throws ClassNotFoundException, SQLException {
    }

    @FXML
    private void scan_util(KeyEvent event) throws ClassNotFoundException, SQLException {
        ObservableList<Personal_Information> list = Personal_Information_DAO.SearchData(finder.getText());
        if (list.size() > 0) {
            populateTable(list);
        } else {
            System.out.println("No data found!");
            table_view.refresh();
        }
    }

}
