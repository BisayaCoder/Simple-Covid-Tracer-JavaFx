/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Utilities.DBConnection;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import javafx.scene.control.Separator;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class _CheckerController implements Initializable {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private String CONTROL_NO;
    private String gender;
    @FXML
    private TextField fname;
    @FXML
    private TextField fage;
    @FXML
    private TextField fst;
    @FXML
    private ComboBox<String> fmunc;
    @FXML
    private ComboBox<String> fprov;
    @FXML
    private CheckBox jmale;
    @FXML
    private CheckBox jfmale;
    @FXML
    private TextField fbrgy;
    @FXML
    private Button data_submit;
    @FXML
    private ComboBox<String> fstat;
    @FXML
    private DatePicker fdate;
    @FXML
    private ImageView qr_code_view;
    @FXML
    private Button gen_code;
    @FXML
    private ComboBox<String> non_stbl_list;
    @FXML
    private Separator l2;
    @FXML
    private Separator l3;
    @FXML
    private Separator l1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        non_stbl_list.setVisible(false);
        l1.setVisible(false);
        l2.setVisible(false);
        l3.setVisible(false);
        gen_code.setVisible(false);
        ObservableList<String> values = FXCollections.observableArrayList("STABLE", "NON-STABLE");
        fstat.setItems(values);

        ObservableList<String> prov = FXCollections.observableArrayList(
                "Davao Occidental",
                "Davao Oriental",
                "Davaoo De Oro",
                "Davao Del Sur");
        fprov.setItems(prov);

    }

    @FXML
    private void ifclicked(ActionEvent event) {
        if (jmale.isSelected()) {
            gender = "Male";
            jfmale.setSelected(false);
            System.out.println(gender);
        } else {
            System.out.println("Male Not Clicked!");
        }
    }

    @FXML
    private void iffclicked(ActionEvent event) {
        if (jfmale.isSelected()) {
            gender = "Female";
            jmale.setSelected(false);
            System.out.println(gender);
        } else {
            System.out.println("Female Not Clicked");
        }
    }

    @FXML
    private void sel_prov(ActionEvent event) {
        if (fprov.getSelectionModel().isSelected(0)) {
            System.out.println("Checked");
            ObservableList<String> municipalities = FXCollections.observableArrayList(
                    "Malita",
                    "Don Marcelino",
                    "Jose Abad Santos",
                    "Sta. Maria",
                    "Saranggani");
            fmunc.setItems(municipalities);
            fmunc.getSelectionModel().selectFirst();
        } else if (fprov.getSelectionModel().isSelected(1)) {
            ObservableList<String> municipalities = FXCollections.observableArrayList(
                    "Baganga",
                    "Banaybanay",
                    "Boston",
                    "Caraga",
                    "Cateel",
                    "Governor Generoso",
                    "Lupon",
                    "Manay",
                    "Mati",
                    "San Isidro",
                    "Tarragona");
            fmunc.setItems(municipalities);
            fmunc.getSelectionModel().selectFirst();
        } else if (fprov.getSelectionModel().isSelected(2)) {
            ObservableList<String> municipalities = FXCollections.observableArrayList(
                    "Compostela",
                    "Laak",
                    "Mabini",
                    "Maco",
                    "Maragusan",
                    "Mawab",
                    "Monkayo",
                    "Montevista",
                    "Nabunturan",
                    "New Bataan",
                    "Pantukan");
            fmunc.setItems(municipalities);
            fmunc.getSelectionModel().selectFirst();
        } else if (fprov.getSelectionModel().isSelected(3)) {
            ObservableList<String> municipalities = FXCollections.observableArrayList(
                    "Bansalan",
                    "Davao City",
                    "Digos",
                    "Hagonoy",
                    "Kiblawan",
                    "Magsaysay",
                    "Malalag",
                    "Matanao",
                    "Padada",
                    "Santa Cruz",
                    "Sulop");
            fmunc.setItems(municipalities);
            fmunc.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void submit(ActionEvent event) throws SQLException {
        inSert();
    }

    private void inSert() throws SQLException {
        conn = (Connection) DBConnection.Database_Con();
        String _fname = fname.getText();
        String _age = fage.getText();
        String _adds = fst.getText();
        String _addb = fbrgy.getText();
        String _mun = fmunc.getSelectionModel().getSelectedItem();
        String _prov = fprov.getSelectionModel().getSelectedItem();
        String output = non_stbl_list.getSelectionModel().getSelectedItem();
        java.sql.Date getDate = java.sql.Date.valueOf(fdate.getValue());
        String sql = "Insert into residence (fullname, age, gender, address, municipality, province, date_reg, rh_status) values (?,?,?,?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, _fname);
            pst.setString(2, _age);
            pst.setString(3, gender);
            pst.setString(4, _adds + " " + _addb);
            pst.setString(5, _mun);
            pst.setString(6, _prov);
            pst.setString(7, String.valueOf(getDate));
            pst.setString(8, output);
            pst.execute();
            gen_code.setVisible(true);
            data_submit.setDisable(true);
            System.out.println("Added Succesfully");
        } catch (SQLException e) {
            throw e;
        }
        try {
            String Sql = "Select * from residence where fullname = '" + _fname + "'";
            pst = conn.prepareStatement(Sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                CONTROL_NO = rs.getString("ctrl_id");
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    @FXML
    private void generate_code(ActionEvent event) throws WriterException {
        String data = CONTROL_NO;
        /* = "Name: " + fname.getText() + "\n"
                + "Age: " + fage.getText() + "\n"
                + "Gender: " + gender + "\n"
                + "Address: " + fst.getText() + " " + fbrgy.getText() + "\n"
                + "Province: " + fprov.getSelectionModel().getSelectedItem() + "\n"
                + "Municipality: " + fmunc.getSelectionModel().getSelectedItem() + "\n"
                + "Health Status: " + fstat.getSelectionModel().getSelectedItem() + "\n"
                + "Naay: " + non_stbl_list.getSelectionModel().getSelectedItem();*/
        int width = 300;
        int height = 300;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BufferedImage bufferedImage = null;
        BitMatrix byteMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferedImage.createGraphics();
        Graphics2D image = (Graphics2D) bufferedImage.getGraphics();
        image.setColor(Color.WHITE);
        image.fillRect(0, 0, width, height);
        image.setColor(Color.BLACK);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (byteMatrix.get(i, j)) {
                    image.fillRect(i, j, 1, 1);
                }
            }
        }
        System.out.println("QR created successfully....");
        qr_code_view.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
    }

    private void print() {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream("Image.pdf"));
            document.open();
            Image image = Image.getInstance(qr_code_view.toString());
            document.add(image);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void select_hs(ActionEvent event) {
        ObservableList<String> non_slist = FXCollections.observableArrayList(
                "Ubo",
                "Sip-on",
                "Hilanat/Kalintura");
        non_stbl_list.setItems(non_slist);
        non_stbl_list.getSelectionModel().selectFirst();
        if (fstat.getSelectionModel().isSelected(1)) {
            l1.setVisible(true);
            l2.setVisible(true);
            l3.setVisible(true);
            non_stbl_list.setVisible(true);
            non_stbl_list.setItems(non_slist);
        } else {
            non_stbl_list.setVisible(false);
            l1.setVisible(false);
            l2.setVisible(false);
            l3.setVisible(false);
        }
    }
}
