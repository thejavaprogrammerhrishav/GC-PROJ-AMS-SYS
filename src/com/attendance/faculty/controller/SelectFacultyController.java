/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.faculty.controller;

import com.attendance.login.dao.Login;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.personal.dao.PersonalDetailsDao;
import com.attendance.personal.model.PersonalDetails;
import com.attendance.util.Fxml;
import com.attendance.util.RootFactory;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Programmer Hrishav
 */
public class SelectFacultyController extends AnchorPane {

    @FXML
    private JFXComboBox<String> facultylist;

    @FXML
    private JFXButton proceed;

    @FXML
    private JFXButton cancel;

    private FXMLLoader fxml;
    private Login dao;
    private PersonalDetailsDao pdao;

    private String type;

    public SelectFacultyController(String type) {
        this.type = type;
        fxml = Fxml.getSelectFacultyFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(SelectFacultyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao = (Login) Start.app.getBean("userlogin");
        pdao=(PersonalDetailsDao) Start.app.getBean("personal");
        List<User> list = new ArrayList<>(dao.findByDepartment(SystemUtils.getDepartment()));
        List<PersonalDetails> faculties = list.stream().map(l->pdao.findById(l.getPersonalid())).collect(Collectors.toList());
        faculties.stream().map(f -> f.getName()).forEach(facultylist.getItems()::add);

        proceed.setOnAction(this::proceed);
        cancel.setOnAction(this::close);
    }

    private void close(ActionEvent evt) {
        ((Node) evt.getSource()).getScene().getWindow().hide();
    }

    private void proceed(ActionEvent evt) {
        close(evt);
        if (facultylist.getSelectionModel().getSelectedIndex() != -1 && type.equals("attendance")) {
            SwitchRoot.switchRoot(Start.st, RootFactory.getStudentAttendanceRoot(Start.st.getScene().getRoot(), facultylist.getSelectionModel().getSelectedItem()));
        } else if (facultylist.getSelectionModel().getSelectedIndex() != -1 && type.equals("uploadnotes")) {
            SwitchRoot.switchRoot(Start.st, RootFactory.getUploadNotesRoot(Start.st.getScene().getRoot(), facultylist.getSelectionModel().getSelectedItem()));
        }
    }
}
