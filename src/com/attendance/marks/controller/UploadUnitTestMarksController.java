/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.marks.controller;

import com.attendance.main.Start;
import com.attendance.marks.dao.UnitTestDao;
import com.attendance.marks.model.UnitTest;
import com.attendance.marks.service.MarksService;
import com.attendance.student.dao.StudentDao;
import com.attendance.student.model.Student;
import com.attendance.student.service.StudentService;
import com.attendance.util.ExceptionDialog;
import com.attendance.util.Fxml;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
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
import javafx.scene.Parent;
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
    private MarksService dao;
    private StudentService sdao;
    private ExceptionDialog dialog;

    private String acadamicyear;
    private int yyear;
    private Parent parent;

    public UploadUnitTestMarksController(Parent parent) {
        this.parent = parent;
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
        dao = (MarksService) Start.app.getBean("unittestservice");
        sdao = (StudentService) Start.app.getBean("studentservice");
        
        dao.setParent(this);
        dialog=dao.getEx();
        
        ut.getItems().setAll("Unit Test 1", "Unit Test 2");
        acayear.getItems().setAll("1st","2nd","3rd");
        coursetype.getItems().setAll("Honours","Pass");

        List<String> years = sdao.findAllYear();
        year.getItems().setAll(years);
        
        load.setOnAction(this::loadStudents);
        cancel.setOnAction(evt -> SwitchRoot.switchRoot(Start.st, parent));
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

                List<Student> list = new ArrayList<>(sdao.findByAcadamicYearAndyear(acadamicyear, yyear));
                list=list.stream().filter(f->f.getDepartment().equals(SystemUtils.getDepartment())).filter(p->p.getCourseType().equals(coursetype.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
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
            dialog.showError(this,"Unit Test Load Student", "Please fill the empty loading fields");
        }
    }

    private void sortStudentList(VBox vb) {
        List<UnitTestNode> sorted = vb.getChildren().stream().map(n -> (UnitTestNode) n)
                .sorted((i1, i2) -> i1.getSerial().compareTo(i2.getSerial()))
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
        try{
        List<UnitTestNode> list = studentlist.getChildren().stream().map(n -> (UnitTestNode) n).collect(Collectors.toList());
        List<UnitTest> collect = list.stream().map(it -> {
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
            test.setDepartment(it.getStudent().getDepartment());
            return test;
        }).collect(Collectors.toList());
        boolean s = validate(collect);
        String t = "Marks Uploaded Successfully\nUnit Test:  " + ut.getSelectionModel().getSelectedItem() + "\nSemester:   " + sem.getSelectionModel().getSelectedItem();
        if(s){
                dialog.showSuccess(this, "Upload Unit Test Marks", t);
        }else{
                dialog.showError(this, "Upload Unit Test Marks", "Marks Upload Failed");
        }
        }catch(Exception e){
            dialog.showError(this, "Upload Unit Test Marks", e.getMessage());
        }
    }
    
    private boolean validate(List<UnitTest> list) throws Exception{
        for(UnitTest unit:list){
            boolean exp1=unit.getMarksObtained()<=unit.getTotalMarks() && unit.getPassingMarks()>0 && unit.getPassingMarks()<=unit.getTotalMarks();
            if(!exp1){
                throw new Exception("Marks Mismatch Of Student\nRonn No: "+unit.getRollno());
            }
        }
        return dao.saveAllMarks(list);
    }

}
