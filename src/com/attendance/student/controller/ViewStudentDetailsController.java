/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.student.controller;

import com.attendance.main.Start;
import com.attendance.student.dao.StudentDao;
import com.attendance.student.model.Student;
import com.attendance.student.service.StudentService;
import com.attendance.util.Fxml;
import com.attendance.util.Message;
import com.attendance.util.MessageUtil;
import com.attendance.util.SwitchRoot;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Programmer Hrishav
 */
public class ViewStudentDetailsController extends AnchorPane {

    @FXML
    private Label department;

    @FXML
    private JFXCheckBox filterbyid;

    @FXML
    private JFXCheckBox filterbysemester;

    @FXML
    private JFXCheckBox filterbyyear;

    @FXML
    private TextField fstudentid;

    @FXML
    private JFXComboBox<String> facadamicyear;

    @FXML
    private JFXComboBox<String> fyear;

    @FXML
    private JFXCheckBox filterbyname;

    @FXML
    private TextField fstudentname;

    @FXML
    private JFXButton search;

    @FXML
    private JFXButton clear;

    @FXML
    private JFXCheckBox filterbycoursetype;

    @FXML
    private JFXComboBox<String> coursetype;

    @FXML
    private TextField studentName;

    @FXML
    private TextField studentRollNumber;

    @FXML
    private TextField studentYear;

    @FXML
    private TextField studentContact;

    @FXML
    private TextField studentSemester;

    @FXML
    private JFXCheckBox genMale;

    @FXML
    private JFXCheckBox genFemale;

    @FXML
    private JFXButton refresh;

    @FXML
    private JFXButton close;

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, String> studentColumnName;

    @FXML
    private TableColumn<Student, Integer> studentColumnYear;

    @FXML
    private TableColumn<Student, String> studentacadamicyear;

    @FXML
    private JFXButton allstudents;

    @FXML
    private TextField curcoursetype;

    private FXMLLoader fxml;
    private StudentService dao;
    private String cdepartment;
    private Parent parent ;

    public ViewStudentDetailsController(String cdepartment,Parent parent) {
        this.cdepartment = cdepartment; 
        this.parent = parent;
        fxml = Fxml.getViewStudentDetailsFXML();
        fxml.setController(this);
        fxml.setRoot(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ViewStudentDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void initialize() {
        dao = (StudentService) Start.app.getBean("studentservice");
        dao.setParent(this);
        department.setText("Department:   " + cdepartment);
        studentColumnName.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        studentColumnYear.setCellValueFactory(new PropertyValueFactory<Student, Integer>("year"));
        studentacadamicyear.setCellValueFactory(new PropertyValueFactory<Student, String>("acadamicyear"));
        listStudents(null);
        filterInit();
        buttonInit();
        filterActions();
        LoadFilterData();
        genderInit();
    }

    private void filterInit() {
        facadamicyear.setDisable(true);
        fstudentid.setDisable(true);
        fstudentname.setDisable(true);
        fyear.setDisable(true);
        coursetype.setDisable(true);
    }

    private void filterActions() {
        filterbyid.selectedProperty().addListener((ol, oldValue, newValue) -> {
            if (newValue == true) {
                fstudentid.setDisable(false);
            } else {
                fstudentid.setDisable(true);
            }
        });

        filterbyname.selectedProperty().addListener((ol, oldValue, newValue) -> {
            if (newValue == true) {
                fstudentname.setDisable(false);
            } else {
                fstudentname.setDisable(true);
            }
        });

        filterbysemester.selectedProperty().addListener((ol, oldValue, newValue) -> {
            if (newValue == true) {
                facadamicyear.setDisable(false);
            } else {
                facadamicyear.setDisable(true);
            }
        });
        filterbyyear.selectedProperty().addListener((ol, oldValue, newValue) -> {
            if (newValue == true) {
                fyear.setDisable(false);
            } else {
                fyear.setDisable(true);
            }
        });

        filterbycoursetype.selectedProperty().addListener((ol, oldValue, newValue) -> {
            if (newValue == true) {
                coursetype.setDisable(false);
            } else {
                coursetype.setDisable(true);
            }
        });
    }

    private void LoadFilterData() {
        facadamicyear.getItems().addAll("1st", "2nd", "3rd");
        coursetype.getItems().addAll("Honours", "Pass");
        List<Integer> years = dao.get("select distinct(year) from student", Integer.class);
        Collections.sort(years);
        years.stream().map(y -> Integer.toString(y)).forEach(fyear.getItems()::add);
    }

    private void buttonInit() {
        close.setOnAction(e -> SwitchRoot.switchRoot(Start.st, parent));
        studentTable.setOnMouseClicked(this::LoadStudentDetails);
        allstudents.setOnAction(this::listStudents);
        clear.setOnAction(this::clearData);
        search.setOnAction(this::searchUsingFilter);
        refresh.setOnAction(this::searchUsingFilter);
    }

    private void clearData(ActionEvent e) {
        studentName.setText("");
        studentRollNumber.setText("");
        studentSemester.setText("");
        studentYear.setText("");
        studentContact.setText("");
        curcoursetype.setText("");
        genFemale.setSelected(false);
        genMale.setSelected(false);

        filterbyid.setSelected(false);
        filterbyname.setSelected(false);
        filterbysemester.setSelected(false);
        filterbyyear.setSelected(false);
        filterbycoursetype.setSelected(false);

        listStudents(null);
    }

    private void LoadStudentDetails(MouseEvent evt) {
        Student s = studentTable.getSelectionModel().getSelectedItem();
        if (s == null) {
            System.out.println("No Item Selected");
        } else {
            studentName.setText(s.getName());
            studentRollNumber.setText("" + s.getRollno());
            studentYear.setText("" + s.getYear());
            studentSemester.setText(s.getAcadamicyear() + " Year");
            studentContact.setText(s.getContact());
            if (s.getGender().equalsIgnoreCase("Male")) {
                genMale.setSelected(true);
                genFemale.setSelected(false);
            } else {
                genFemale.setSelected(true);
                genMale.setSelected(false);
            }
            curcoursetype.setText(s.getCourseType());
        }
    }

    private void searchUsingFilter(ActionEvent e) {
        List<Student> list = new ArrayList<>(dao.findAll());
        list = list.stream().filter(p -> p.getDepartment().equals(cdepartment)).collect(Collectors.toList());
        if (filterbyid.isSelected()) {
            list = list.stream().filter(s -> s.getId().contains(fstudentid.getText())).collect(Collectors.toList());
        }

        if (filterbyname.isSelected()) {
            list = list.stream().filter(s -> s.getName().contains(fstudentname.getText())).collect(Collectors.toList());
        }

        if (filterbysemester.isSelected()) {
            if (facadamicyear.getSelectionModel().getSelectedIndex() == -1) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setContentText("Invalid Semester Or Year");
                al.show();
            } else {
                list = list.stream().filter(s -> s.getAcadamicyear().equals(facadamicyear.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
            }
        }
        if (filterbyyear.isSelected()) {
            if (fyear.getSelectionModel().getSelectedIndex() == -1) {
                MessageUtil.showError(Message.ERROR, "View Student Details", "Invalid Acadamic Year Or Year Of Admission", ((Node) e.getSource()).getScene().getWindow());
            } else {
                list = list.stream().filter(s -> s.getYear() == Integer.parseInt(fyear.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
            }
        }

        if (filterbycoursetype.isSelected()) {
            if (coursetype.getSelectionModel().getSelectedIndex() == -1) {
                MessageUtil.showError(Message.ERROR, "View Student Details", "Invalid Course Type", ((Node) e.getSource()).getScene().getWindow());
            } else {
                list = list.stream().filter(s -> s.getCourseType().equals(coursetype.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
            }
        }

        UpdateTable(list);
    }

    private void listStudents(ActionEvent e) {
        List<Student> list = new ArrayList<>(dao.findAll());
        list = list.stream().filter(p -> p.getDepartment().equals(cdepartment)).collect(Collectors.toList());

        UpdateTable(list);
    }

    private void UpdateTable(List<Student> list) {
        studentTable.setItems(FXCollections.observableArrayList(list));
    }

    private void genderInit() {
        genMale.selectedProperty().addListener((ol,o,n)->{
            if(n) {
                genMale.setSelected(true);
                genFemale.setSelected(false);
            }
        });
        
        genFemale.selectedProperty().addListener((ol,o,n)->{
            if(n) {
                genMale.setSelected(false);
                genFemale.setSelected(true);
            }
        });
    }
}
