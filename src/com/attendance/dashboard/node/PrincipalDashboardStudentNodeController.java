/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.dashboard.node;

import com.attendance.main.Start;
import com.attendance.student.dao.StudentDao;
import com.attendance.student.service.StudentService;
import com.attendance.util.Fxml;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
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

    @FXML
    private JFXButton refresh;

    private FXMLLoader fxml;

    private StudentService dao;

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
        dao = (StudentService) Start.app.getBean("studentservice");
        dao.setParent(this);
        department.getItems().setAll("All");
        department.getItems().addAll(SystemUtils.getDepartments());
        
        total.setText("");

        List<String> years = dao.findAllYear();
        year.getItems().setAll("All");
        year.getItems().addAll(years);
        refresh.setOnAction(this::load);
    }

    private void load(ActionEvent evt) {
        String query = "";
        if (department.getSelectionModel().getSelectedIndex() <= 0 && year.getSelectionModel().getSelectedIndex() <= 0) {
            query = "select count(*) from student";
        } else if (department.getSelectionModel().getSelectedIndex() > 0) {
            query = "select count(*) from student where department = '" + department.getSelectionModel().getSelectedItem() + "'";
        } else if (year.getSelectionModel().getSelectedIndex() > 0) {
            query = "select count(*) from student where year = " + Integer.parseInt(year.getSelectionModel().getSelectedItem());
        } else {
            query = "Select count(*) from student where department = '" + department.getSelectionModel().getSelectedItem() + "' and year = " + Integer.parseInt(year.getSelectionModel().getSelectedItem());
        }
        List<Integer> get = dao.get(query, Integer.class);
        total.setText("" + get.get(0));
    }
}
