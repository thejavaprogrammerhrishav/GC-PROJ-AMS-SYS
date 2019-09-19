/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.dashboard.node;

import com.attendance.main.Start;
import com.attendance.student.dao.StudentDao;
import com.attendance.util.Fxml;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author pc
 */
public class PrincipalDashboardStudentNodeController extends AnchorPane {

    @FXML
    private JFXComboBox<String> department;

    @FXML
    private JFXComboBox<String> year;

    @FXML
    private Label total;

    private FXMLLoader fxml;

    private StudentDao dao;

    public PrincipalDashboardStudentNodeController() {
        fxml = Fxml.getPrincipalDashboardStudentNodeFXML();
        fxml.setController(this);
        fxml.setRoot(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalDashboardStudentNodeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void initialize() {
        dao = (StudentDao) Start.app.getBean("studentregistration");

        department.getItems().setAll(SystemUtils.getDepartments());

        department.getSelectionModel().selectedItemProperty().addListener((ol, o, n) -> {
            String SQL = "";
            if (year.getSelectionModel().getSelectedIndex() > 0) {
                SQL = "Select count(*) from student where department = '" + department.getSelectionModel().getSelectedItem() + "' and year = " + Integer.parseInt(year.getSelectionModel().getSelectedItem());
            } else {
                SQL = "Select count(*) from student where department = '" + department.getSelectionModel().getSelectedItem() + "'";
            }

            List<Integer> get = dao.get(SQL, Integer.class);
            total.setText("" + get.get(0));
        });
        
         List<String> years = dao.get("select distinct(year) from student order by year", String.class);
        year.getItems().setAll(years);

        year.getSelectionModel().selectedItemProperty().addListener((ol, o, n) -> {
            String SQL = "";
            if (department.getSelectionModel().getSelectedIndex() > 0) {
                SQL = "Select count(*) from student where department = '" + department.getSelectionModel().getSelectedItem() + "' and year = " + Integer.parseInt(year.getSelectionModel().getSelectedItem());
            } else {
                SQL = "Select count(*) from student where year = " + Integer.parseInt(year.getSelectionModel().getSelectedItem());
            }

            List<Integer> get = dao.get(SQL, Integer.class);
            total.setText("" + get.get(0));
        });
    }

}
