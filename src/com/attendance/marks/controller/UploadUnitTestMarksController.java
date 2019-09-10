/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.marks.controller;

import com.attendance.main.Start;
import com.attendance.marks.dao.UnitTestDao;
import com.attendance.marks.model.UnitTest;
import com.attendance.student.dao.StudentDao;
import com.attendance.student.model.Student;
import com.attendance.util.Fxml;
import com.attendance.util.Message;
import com.attendance.util.MessageUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Programmer Hrishav
 */
public class UploadUnitTestMarksController extends AnchorPane {

    @FXML
    private JFXButton load;

    @FXML
    private JFXButton clear;

    @FXML
    private JFXButton save;

    @FXML
    private JFXButton cancel;

    @FXML
    private TextField totalmarks;

    @FXML
    private TextField passingmarks;

    @FXML
    private JFXComboBox<String> ut;

    @FXML
    private VBox studentlist;

    @FXML
    private JFXComboBox<String> sem;

    @FXML
    private JFXComboBox<String> acayear;

    @FXML
    private JFXComboBox<String> year;

    @FXML
    private JFXComboBox<String> coursetype;

    private FXMLLoader fxml;
    private UnitTestDao dao;
    private StudentDao sdao;

    private String acadamicyear;
    private int yyear;

    public UploadUnitTestMarksController() {
        fxml = Fxml.getUploadMarksFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(UploadUnitTestMarksController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao = (UnitTestDao) Start.app.getBean("unittest");
        sdao = (StudentDao) Start.app.getBean("studentregistration");
        ut.getItems().setAll("Unit Test 1", "Unit Test 2");

        load.setOnAction(this::loadStudents);
        cancel.setOnAction(evt -> ((Node) evt.getSource()).getScene().getWindow().hide());
        clear.setOnAction(this::clear);
        save.setOnAction(this::save);
    }

    public void loadStudents(ActionEvent evt) {
        try {
            if (acayear.getSelectionModel().getSelectedIndex() < 0 || year.getSelectionModel().getSelectedIndex() < 0 || coursetype.getSelectionModel().getSelectedIndex() < 0) {
                throw new ArrayIndexOutOfBoundsException();
            } else {
                acadamicyear = acayear.getSelectionModel().getSelectedItem();
                if (acadamicyear.equals("1st")) {
                    sem.getItems().setAll("1st", "2nd");
                } else if (acadamicyear.equals("2nd")) {
                    sem.getItems().setAll("3rd", "4th");
                } else {
                    sem.getItems().setAll("5th", "6th");
                }
                yyear = Integer.parseInt(year.getSelectionModel().getSelectedItem());

                List<Student> list = new ArrayList<>(sdao.findByAcadamicYearAndYear(acadamicyear, yyear));
                list=list.stream().filter(p->p.getCourseType().equals(coursetype.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
                List<UnitTestNode> nodes = list.stream().map(new Function<Student, UnitTestNode>() {
                    int count = 1;

                    @Override
                    public UnitTestNode apply(Student st) {
                        return new UnitTestNode(count++, st);
                    }
                }).collect(Collectors.toList());
                studentlist.getChildren().setAll(nodes);
                sortStudentList(studentlist);

            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            MessageUtil.showError(Message.ERROR, "Unit Test ------> Load Student", "Please fill the empty loading fields", ((Node) evt.getSource()).getScene().getWindow());
        }
    }

    private void sortStudentList(VBox vb) {
        List<UnitTestNode> sorted = vb.getChildren().stream().map(n -> (UnitTestNode) n)
                .sorted((i1, i2) -> i1.getRollNo().compareTo(i2.getRollNo()))
                .collect(Collectors.toList());
        vb.getChildren().setAll(sorted);
    }

    private void clear(ActionEvent evt) {
        studentlist.getChildren().clear();
        passingmarks.setText("");
        totalmarks.setText("");
        ut.getSelectionModel().select(-1);
        sem.getSelectionModel().select(-1);
        sem.getItems().clear();
    }

    private void save(ActionEvent evt) {
        List<UnitTestNode> list = studentlist.getChildren().stream().map(n -> (UnitTestNode) n).collect(Collectors.toList());
        list.stream().map(it -> {
            UnitTest test = new UnitTest();
            test.setAcadamicYear(acadamicyear);
            test.setMarksObtained(it.getMarksObtained());
            test.setName(it.getStudent().getName());
            test.setPassingMarks(Integer.parseInt(passingmarks.getText()));
            test.setRollno(it.getStudent().getRollno());
            test.setSemester(sem.getSelectionModel().getSelectedItem());
            test.setTotalMarks(Integer.parseInt(totalmarks.getText()));
            test.setUnitTest(ut.getSelectionModel().getSelectedItem());
            test.setYear(yyear);
            test.setCoursetype(coursetype.getSelectionModel().getSelectedItem());
            test.setHonoursSubject(it.getStudent().getHonoursSubject());
            return test;
        }).forEach(dao::save);
        String t = "Marks Uploaded Successfully\nUnit Test:  " + ut.getSelectionModel().getSelectedItem() + "\nSemester:   " + sem.getSelectionModel().getSelectedItem();
        MessageUtil.showInformation(Message.INFORMATION, "Upload Unit Test Marks", t, ((Node) evt.getSource()).getScene().getWindow());

    }

}