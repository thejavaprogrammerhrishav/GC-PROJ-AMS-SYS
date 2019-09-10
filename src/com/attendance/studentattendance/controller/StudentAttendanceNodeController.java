/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.studentattendance.controller;

import com.attendance.student.model.Student;
import com.attendance.util.Fxml;
import com.jfoenix.controls.JFXCheckBox;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Programmer Hrishav
 */
public class StudentAttendanceNodeController extends AnchorPane {

    @FXML
    private Label name;

    @FXML
    private Label rollno;

    @FXML
    private Label contact;

    @FXML
    private Label sid;

    @FXML
    private Label gender;

    @FXML
    private Label acayear;

    @FXML
    private Label year;

    @FXML
    private JFXCheckBox present;
    
    @FXML
    private Label coursetype;

    @FXML
    private Label honourssubject;

    @FXML
    private JFXCheckBox absent;

    private FXMLLoader fxml;

    private final Student stud;

    public StudentAttendanceNodeController(Student stud) {
        this.stud = stud;
        fxml = Fxml.getStudentAttendanceNodeFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(StudentAttendanceNodeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        present.setOnMouseClicked(e -> {
            if (!present.isSelected()) {
                present.setSelected(true);
            }
            absent.setSelected(false);
        });

        absent.setOnMouseClicked(e -> {
            if (!absent.isSelected()) {
                absent.setSelected(true);
            }
            present.setSelected(false);
        });

        init();
    }

    private void init() {
        name.setText(stud.getName());
        rollno.setText("" + stud.getRollno());
        contact.setText(stud.getContact());
        sid.setText(stud.getId());
        year.setText("" + stud.getYear());
        gender.setText(stud.getGender());
        acayear.setText(stud.getAcadamicyear());
        coursetype.setText(stud.getCourseType());
        honourssubject.setText(stud.getDepartment());
    }

    public String getStudentId() {
        return sid.getText();
    }

    public String getAttendanceStatus() {
        if (present.isSelected()) {
            return "Present";
        } else if (absent.isSelected()) {
            return "Absent";
        } else {
            return "";
        }
    }

    public Integer getRollNo() {
        return Integer.parseInt(rollno.getText());
    }

}
