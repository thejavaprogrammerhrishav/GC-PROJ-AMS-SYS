/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.dashboard.node;

import com.attendance.faculty.dao.FacultyDao;
import com.attendance.main.Start;
import com.attendance.student.dao.StudentDao;
import com.attendance.util.Fxml;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.List;
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

    private FacultyDao dao;

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
        dao = (FacultyDao) Start.app.getBean("facultyregistration");

        department.getItems().setAll(SystemUtils.getDepartments());

        department.getSelectionModel().selectedItemProperty().addListener((ol, o, n) -> {
            String SQL = "Select count(*) from faculty where department = '" + department.getSelectionModel().getSelectedItem() + "'";

            List<Integer> get = dao.get(SQL, Integer.class);
            total.setText("" + get.get(0));
        });

    }

}
