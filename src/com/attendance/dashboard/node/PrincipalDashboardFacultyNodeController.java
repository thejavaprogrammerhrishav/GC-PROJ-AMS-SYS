/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.dashboard.node;

import com.attendance.login.dao.Login;
import com.attendance.main.Start;
import com.attendance.util.Fxml;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author pc
 */
public class PrincipalDashboardFacultyNodeController extends AnchorPane {

    @FXML
    private JFXComboBox<String> department;

    @FXML
    private Label total;

    private FXMLLoader fxml;

    private Login dao;

    public PrincipalDashboardFacultyNodeController() {
        fxml = Fxml.getPrincipalDashboardFacultyNodeFXML();
        fxml.setController(this);
        fxml.setRoot(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalDashboardFacultyNodeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao = (Login) Start.app.getBean("userlogin");

        department.getItems().setAll(SystemUtils.getDepartments());

        department.getSelectionModel().selectedItemProperty().addListener((ol, o, n) -> {
            String SQL = "Select count(*) from loginuser where department = '" + department.getSelectionModel().getSelectedItem() + "' and type='HOD' or type='Faculty'";
            int get = dao.count(SQL);
            total.setText("" + get);
        });

    }

}
