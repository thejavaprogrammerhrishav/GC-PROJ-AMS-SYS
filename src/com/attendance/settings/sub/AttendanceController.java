/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings.sub;

import com.attendance.main.Start;
import com.attendance.student.dao.StudentDao;
import com.attendance.student.model.Student;
import com.attendance.studentattendance.dao.ClassDetailsDao;
import com.attendance.studentattendance.model.Attendance;
import com.attendance.studentattendance.model.ClassDetails;
import com.attendance.util.AttendanceUtilModel;
import com.attendance.util.Fxml;
import com.attendance.util.RootFactory;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author Programmer Hrishav
 */
public class AttendanceController extends AnchorPane {

    @FXML
    private TableView<AttendanceUtilModel> table;

    @FXML
    private TableColumn<AttendanceUtilModel, String> tstudentname;

    @FXML
    private TableColumn<AttendanceUtilModel, Integer> troll;

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

    private StudentDao sdao;

    private FXMLLoader fxml;
    private ClassDetailsDao cdao;

    private Parent parent;

    public AttendanceController(Parent parent) {
        this.parent = parent;
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
        sdao = (StudentDao) Start.app.getBean("studentregistration");
        cdao = (ClassDetailsDao) Start.app.getBean("classdetails");

        initFilters();
        initTable();
        populateTable(null);

        applyfilters.setOnAction(this::applyfilters);
        refresh.setOnAction(this::populateTable);
        close.setOnAction(e->SwitchRoot.switchRoot(Start.st, parent));
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
        coursetype.getItems().setAll("Honours", "Pass");
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
        tstudentname.setCellValueFactory(new PropertyValueFactory<AttendanceUtilModel, String>("name"));
        troll.setCellValueFactory(new PropertyValueFactory<AttendanceUtilModel, Integer>("roll"));
    }

    private void populateTable(ActionEvent evt) {
        int count = 0,x=0;
        List<Data> list = new ArrayList<>();
        List<ClassDetails> all = cdao.findByDepartment(SystemUtils.getDepartment());
        for (ClassDetails c : all) {
            for (Attendance a : c.getAttendance()) {
                list.add(new Data(c.getClassId(), a));
            }
        }
        List<Student> student = sdao.findByDepartment(SystemUtils.getDepartment());
        Map<String, Student> students = student.parallelStream().collect(Collectors.toMap(Student::getId, Function.identity()));
        List<AttendanceUtilModel> nlist = list.stream().map(a -> {
            AttendanceUtilModel at = new AttendanceUtilModel();
            Student sss = students.get(a.getAttendance().getStudentId());
            at.setStatus(a.getAttendance().getStatus());
            at.setStudentId(a.getAttendance().getStudentId());
            at.setName(sss.getName());
            at.setRoll(sss.getRollno());

            String s = a.getClassid();
            String[] ss = s.split("@");
            at.setDate(ss[0].split("/")[1]);

            ss = ss[1].split("#");
            at.setTime(ss[0]);

            ss = ss[1].split("__");
            at.setAcadamicyear(ss[0]);
   
            ss = ss[1].split("_");
            at.setSemester(ss[0] + " Semester");

            ss = ss[1].split("&");
            at.setYear(ss[0]);

            if (a.getClassid().charAt(a.getClassid().length() - 1) == 'H') {
                at.setCoursetype("Honours");
            }

            if (a.getClassid().charAt(a.getClassid().length() - 1) == 'P') {
                at.setCoursetype("Pass");
            }

            return at;
        }).collect(Collectors.toList());
        table.getItems().setAll(nlist);
    }

    private void applyfilters(ActionEvent evt) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
        populateTable(evt);
        List<AttendanceUtilModel> nlist = table.getItems();

        if (filterbyacadamicyear.isSelected()) {
            nlist = nlist.stream().filter(s -> s.getAcadamicyear().equals(acadamicyear.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }
        if (filterbyid.isSelected()) {
            nlist = nlist.stream().filter(s -> s.getStudentId().startsWith(studentid.getText())).collect(Collectors.toList());
        }
        if (filterbymonth.isSelected()) {
            int mm = month.getSelectionModel().getSelectedIndex() + 1;

            nlist = nlist.stream().filter(f -> DateTime.parse(f.getDate(), dtf).getMonthOfYear() == mm).collect(Collectors.toList());
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

        if (filterbycoursetype.isSelected()) {
            nlist = nlist.stream().filter(s -> s.getCoursetype().equals(coursetype.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }

        table.getItems().setAll(nlist);
    }
    
    private class Data{
        private String classid;
        private Attendance attendance;

        public Data(String classid, Attendance attendance) {
            this.classid = classid;
            this.attendance = attendance;
        }

        public void setAttendance(Attendance attendance) {
            this.attendance = attendance;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public Attendance getAttendance() {
            return attendance;
        }

        public String getClassid() {
            return classid;
        }
        
    }
}
