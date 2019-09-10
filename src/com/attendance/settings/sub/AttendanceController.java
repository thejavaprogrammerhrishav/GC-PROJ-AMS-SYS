/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings.sub;

import com.attendance.main.Start;
import com.attendance.student.dao.StudentDao;
import com.attendance.studentattendance.dao.AttendanceDao;
import com.attendance.studentattendance.model.Attendance;
import com.attendance.util.AttendanceUtilModel;
import com.attendance.util.Fxml;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Programmer Hrishav
 */
public class AttendanceController extends AnchorPane {

    @FXML
    private TableView<AttendanceUtilModel> table;

    @FXML
    private TableColumn<AttendanceUtilModel, String> tclassdate;

    @FXML
    private TableColumn<AttendanceUtilModel, String> tclasstime;

    @FXML
    private TableColumn<AttendanceUtilModel, String> tsemester;

    @FXML
    private TableColumn<AttendanceUtilModel, String> tyear;

    @FXML
    private TableColumn<AttendanceUtilModel, String> tstudentid;

    @FXML
    private TableColumn<AttendanceUtilModel, String> tstatus;

    @FXML
    private TableColumn<AttendanceUtilModel, String> tacadamicyear;

    @FXML
    private JFXCheckBox filterbystatus;

    @FXML
    private JFXCheckBox filterbysemester;

    @FXML
    private JFXCheckBox filterbymonth;

    @FXML
    private JFXComboBox<String> semester;

    @FXML
    private JFXComboBox<String> month;

    @FXML
    private JFXComboBox<String> status;

    @FXML
    private JFXCheckBox filterbyyear;

    @FXML
    private JFXComboBox<String> year;

    @FXML
    private JFXButton applyfilters;

    @FXML
    private JFXButton refresh;

    @FXML
    private JFXButton close;

    @FXML
    private JFXCheckBox filterbyid;

    @FXML
    private TextField studentid;

    @FXML
    private JFXCheckBox filterbyacadamicyear;

    @FXML
    private JFXComboBox<String> acadamicyear;
    
       @FXML
    private JFXCheckBox filterbycoursetype;

    @FXML
    private JFXComboBox<String> coursetype;
    
    private AttendanceDao dao;
    private StudentDao sdao;

    private FXMLLoader fxml;

    public AttendanceController() {
        fxml = Fxml.getAttendanceFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(AttendanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao = (AttendanceDao) Start.app.getBean("attendance");
        sdao = (StudentDao) Start.app.getBean("studentregistration");

        initFilters();
        initTable();
        populateTable(null);

        applyfilters.setOnAction(this::applyfilters);
        refresh.setOnAction(this::populateTable);
        close.setOnAction(e -> ((BorderPane) this.getParent()).setCenter(null));
    }

    private void initFilters() {
        status.disableProperty().bind(filterbystatus.selectedProperty().not());
        semester.disableProperty().bind(filterbysemester.selectedProperty().not());
        year.disableProperty().bind(filterbyyear.selectedProperty().not());
        studentid.disableProperty().bind(filterbyid.selectedProperty().not());
        month.disableProperty().bind(filterbymonth.selectedProperty().not());
        acadamicyear.disableProperty().bind(filterbyacadamicyear.selectedProperty().not());
        coursetype.disableProperty().bind(filterbycoursetype.selectedProperty().not());

        acadamicyear.getItems().setAll("1st", "2nd", "3rd");

        acadamicyear.getSelectionModel().selectedItemProperty().addListener((ol, o, n) -> {
            if (n.equals("1st")) {
                semester.getItems().setAll("1st Semester", "2nd Semester");
            } else if (n.equals("2nd")) {
                semester.getItems().setAll("3rd Semester", "4th Semester");
            } else {
                semester.getItems().setAll("5th Semester", "6th Semester");
            }
        });

          filterbyacadamicyear.selectedProperty().addListener((ol, o, n) -> {
            if (!n) {
                filterbysemester.setSelected(false);
            }
        });
          
        status.getItems().setAll("Present", "Absent");
        month.getItems().setAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        coursetype.getItems().setAll("Honours","Pass");
        List<String> years = sdao.get("select distinct(year) from student order by year", String.class);
        year.getItems().setAll(years);
    }

    private void initTable() {
        tclassdate.setCellValueFactory(new PropertyValueFactory<AttendanceUtilModel, String>("date"));
        tclasstime.setCellValueFactory(new PropertyValueFactory<AttendanceUtilModel, String>("time"));
        tsemester.setCellValueFactory(new PropertyValueFactory<AttendanceUtilModel, String>("semester"));
        tyear.setCellValueFactory(new PropertyValueFactory<AttendanceUtilModel, String>("year"));
        tstudentid.setCellValueFactory(new PropertyValueFactory<AttendanceUtilModel, String>("studentId"));
        tstatus.setCellValueFactory(new PropertyValueFactory<AttendanceUtilModel, String>("status"));
        tacadamicyear.setCellValueFactory(new PropertyValueFactory<AttendanceUtilModel, String>("acadamicyear"));
    }

    private void populateTable(ActionEvent evt) {
        List<Attendance> list = dao.findAll();
        List<AttendanceUtilModel> nlist = list.stream().map(a -> {
            AttendanceUtilModel at = new AttendanceUtilModel();
            at.setStatus(a.getStatus());
            at.setStudentId(a.getStudentId());

            String s = a.getClassId();
            String[] ss = s.split("@");
            at.setDate(ss[0]);

            ss = ss[1].split("#");
            at.setTime(ss[0]);
            
            ss = ss[1].split("__");
            at.setAcadamicyear(ss[0]);

 ss = ss[1].split("_");
            at.setSemester(ss[0] + " Semester");
            System.out.println(ss[1]);
            ss = ss[1].split("&");
            System.out.println(ss.length);
            at.setYear(ss[0]);

            return at;
        }).collect(Collectors.toList());
        table.getItems().setAll(nlist);
    }

    private void applyfilters(ActionEvent evt) {
        List<Attendance> list = dao.findAll();
        List<AttendanceUtilModel> nlist = list.stream().map(a -> {
            AttendanceUtilModel at = new AttendanceUtilModel();
            at.setStatus(a.getStatus());
            at.setStudentId(a.getStudentId());

            String s = a.getClassId();
            String[] ss = s.split("@");
            at.setDate(ss[0]);

            ss = ss[1].split("#");
            at.setTime(ss[0]);
            
            ss = ss[1].split("__");
            at.setAcadamicyear(ss[0]);

            ss = ss[1].split("_");
            at.setSemester(ss[0] + " Semester");
            ss = ss[1].split("&");
            at.setYear(ss[0]);
            
             if (a.getClassId().charAt(a.getClassId().length()-1)=='H') {
                at.setCoursetype("Honours");
            }
            if (a.getClassId().charAt(a.getClassId().length()-1)=='P') {
                at.setCoursetype("Pass");
            }
            return at;
        }).collect(Collectors.toList());

         if (filterbyacadamicyear.isSelected()) {
            nlist = nlist.stream().filter(s -> s.getAcadamicyear().equals(acadamicyear.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }
        if (filterbyid.isSelected()) {
            nlist = nlist.stream().filter(s -> s.getStudentId().startsWith(studentid.getText())).collect(Collectors.toList());
        }
        if (filterbymonth.isSelected()) {
            int mm = month.getSelectionModel().getSelectedIndex() + 1;
            if (mm < 10) {
                nlist = nlist.stream().filter(s -> s.getDate().contains("/0" + mm + "/")).collect(Collectors.toList());
            } else {
                nlist = nlist.stream().filter(s -> s.getDate().contains("/" + mm + "/")).collect(Collectors.toList());
            }
        }
        if (filterbysemester.isSelected()) {
            nlist = nlist.stream().filter(s -> s.getSemester().equals(semester.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }
        if (filterbystatus.isSelected()) {
            nlist = nlist.stream().filter(s -> s.getStatus().equals(status.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }
        if (filterbyyear.isSelected()) {
            nlist = nlist.stream().filter(s -> s.getYear().equals(year.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }
        
        if (filterbycoursetype.isSelected()){
            nlist= nlist.stream().filter(s-> s.getCoursetype().equals(coursetype.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }

        table.getItems().setAll(nlist);
    }
}
