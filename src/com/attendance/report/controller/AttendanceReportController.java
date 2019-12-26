/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.report.controller;

import com.attendance.login.dao.Login;
import com.attendance.main.Start;
import com.attendance.papers.dao.PapersDao;
import com.attendance.papers.model.Paper;
import com.attendance.report.model.AttendanceDetails;
import com.attendance.student.dao.StudentDao;
import com.attendance.student.model.Student;
import com.attendance.studentattendance.dao.ClassDetailsDao;
import com.attendance.studentattendance.model.Attendance;
import com.attendance.studentattendance.model.ClassDetails;
import com.attendance.util.ExportAttendancereport;
import com.attendance.util.Fxml;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.attendance.util.Utils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Programmer Hrishav
 */
public class AttendanceReportController extends AnchorPane {

    @FXML
    private TableView<AttendanceDetails> table;

    @FXML
    private TableColumn<AttendanceDetails, String> studentname;

    @FXML
    private TableColumn<AttendanceDetails, Integer> studentrollno;

    @FXML
    private TableColumn<AttendanceDetails, String> studentsemester;

    @FXML
    private TableColumn<AttendanceDetails, Integer> studentyear;

    @FXML
    private TableColumn<AttendanceDetails, String> facultyname;

    @FXML
    private TableColumn<AttendanceDetails, Integer> totalclasses;

    @FXML
    private TableColumn<AttendanceDetails, Integer> totalpresent;

    @FXML
    private TableColumn<AttendanceDetails, Integer> totalabsent;

    @FXML
    private TableColumn<AttendanceDetails, Double> presentpercentage;

    @FXML
    private TableColumn<AttendanceDetails, Double> absentpercentage;

    @FXML
    private JFXCheckBox filterbyname;

    @FXML
    private JFXComboBox<String> name;

    @FXML
    private JFXComboBox<String> semester;

    @FXML
    private JFXComboBox<String> year;

    @FXML
    private JFXButton generatereport;

    @FXML
    private JFXButton clear;

    @FXML
    private JFXButton export2excel;

    @FXML
    private JFXComboBox<String> paper;

    @FXML
    private JFXButton cancel;

    @FXML
    private TextField filename;

    @FXML
    private JFXComboBox<String> acadamicyear;

    @FXML
    private JFXButton excelReport;

    @FXML
    private JFXComboBox<String> coursetype;

    @FXML
    private Label department;

    private FXMLLoader loader;
    private Parent parent;

    private DecimalFormat dec;

    private ExportAttendancereport export;

    private StudentDao studentdao;
    private PapersDao paperdao;
    private ClassDetailsDao classdao;
    private Login dao;

    public AttendanceReportController(Parent parent) {
        this.parent = parent;
        loader = Fxml.getAttendanceReportFXML();
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AttendanceReportController.class.getName()).log(Level.SEVERE, null, ex);
        }

        dec = new DecimalFormat("#.##");
    }

    @FXML
    private void initialize() {
        department.setText(SystemUtils.getDepartment());
        studentdao = (StudentDao) Start.app.getBean("studentregistration");
        paperdao = (PapersDao) Start.app.getBean("papers");
        classdao = (ClassDetailsDao) Start.app.getBean("classdetails");
        dao = (Login) Start.app.getBean("userlogin");
        cancel.setOnAction(this::proceed);
        initFilters();
        initTable();
        generatereport.setOnAction(this::generate);
        clear.setOnAction(this::clear);
        export2excel.setOnAction(this::export2Excel);
        excelReport.setOnAction(this::generateExcel);
    }

    private void proceed(ActionEvent evt) {
        SwitchRoot.switchRoot(Start.st, parent);
    }

    private void initFilters() {
        name.disableProperty().bind(filterbyname.selectedProperty().not());

        acadamicyear.getItems().setAll("1st", "2nd", "3rd");
        coursetype.getItems().setAll("Honours", "Pass");

        acadamicyear.getSelectionModel().selectedItemProperty().addListener((ol, o, n) -> {
            if (n.equals("1st")) {
                semester.getItems().setAll("1st Semester", "2nd Semester");
            } else if (n.equals("2nd")) {
                semester.getItems().setAll("3rd Semester", "4th Semester");
            } else if (n.equals("3rd")) {
                semester.getItems().setAll("5th Semester", "6th Semester");
            } else {
                semester.getItems().setAll("Unknown");
            }

        });
        List<String> years = studentdao.get("select distinct(year) from student order by year", String.class);
        year.getItems().setAll(years);

        List<String> HODname = Utils.util.getHODUsers(SystemUtils.getDepartment()).stream().map(m -> m.getDetails().getName()).collect(Collectors.toList());
        List<String> facultyname = Utils.util.getFacultyUsers(SystemUtils.getDepartment()).stream().map(m -> m.getDetails().getName()).collect(Collectors.toList());

        List<String> names = Stream.concat(HODname.stream(), facultyname.stream()).collect(Collectors.toList());
        name.getItems().setAll(names);
        semester.getSelectionModel().selectedItemProperty().addListener((ol, o, n) -> {
            String sem = n.replace(" Semester", "");
            List<Paper> papers = paperdao.findBySemester(sem);
            paper.getItems().setAll(papers.stream().map(p -> p.getPaperCode()).collect(Collectors.toList()));
        });
    }

    private void initTable() {
        studentname.setCellValueFactory(new PropertyValueFactory<AttendanceDetails, String>("studentname"));
        studentrollno.setCellValueFactory(new PropertyValueFactory<AttendanceDetails, Integer>("rollno"));
        studentsemester.setCellValueFactory(new PropertyValueFactory<AttendanceDetails, String>("semester"));
        studentyear.setCellValueFactory(new PropertyValueFactory<AttendanceDetails, Integer>("year"));
        facultyname.setCellValueFactory(new PropertyValueFactory<AttendanceDetails, String>("facultyname"));
        totalclasses.setCellValueFactory(new PropertyValueFactory<AttendanceDetails, Integer>("totalclasses"));
        totalpresent.setCellValueFactory(new PropertyValueFactory<AttendanceDetails, Integer>("totalpresent"));
        totalabsent.setCellValueFactory(new PropertyValueFactory<AttendanceDetails, Integer>("totalabsent"));
        presentpercentage.setCellValueFactory(new PropertyValueFactory<AttendanceDetails, Double>("presentpercentage"));
        absentpercentage.setCellValueFactory(new PropertyValueFactory<AttendanceDetails, Double>("absentpercentage"));
    }

    private void generate(ActionEvent evt) {
        String acayear = acadamicyear.getSelectionModel().getSelectedItem();
        String sem = semester.getSelectionModel().getSelectedItem().replace(" Semester", "");
        int yr = Integer.parseInt(year.getSelectionModel().getSelectedItem());
        String papercode = paper.getSelectionModel().getSelectedItem();
        String course = coursetype.getSelectionModel().getSelectedItem();

        List<Student> student=studentdao.findByAcadamicYearAndYear(acayear, yr).stream().filter(f->f.getDepartment().equals(SystemUtils.getDepartment()) && f.getCourseType().equals(course)).collect(Collectors.toList());
        Map<String, Student> students = student.parallelStream().collect(Collectors.toMap(Student::getId, Function.identity()));
        
        List<ClassDetails> list = classdao.findAll(SystemUtils.getDepartment(), acayear, sem, yr, papercode, course);
        if(filterbyname.isSelected()){
            list=list.stream().filter(f->f.getFacultyName().equals(name.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }
        List<Attendance> attendanceList = new ArrayList<>();
        for (ClassDetails details : list) {
            attendanceList.addAll(details.getAttendance());
        }
        Map<String, List<String>> attendance = attendanceList.parallelStream().collect(Collectors.groupingBy(Attendance::getStudentId,Collectors.mapping(Attendance::getStatus, Collectors.toList())));
        
        String facultyName = list.stream().map(ClassDetails::getFacultyName).collect(Collectors.toSet()).stream().collect(Collectors.joining(", "));
        
        List<AttendanceDetails> reportList = attendance.entrySet().stream().map(entry->{
            AttendanceDetails details=new AttendanceDetails();
            Student currentstudent=students.get(entry.getKey());
            details.setStudentname(currentstudent.getName());
            details.setRollno(currentstudent.getRollno());
            details.setSemester(sem);
            details.setYear(currentstudent.getYear());
            details.setFacultyname(facultyName);
            
            int totpre = entry.getValue().stream().filter(f->f.equals("Present")).collect(Collectors.counting()).intValue();
            int totabs = entry.getValue().stream().filter(f->f.equals("Absent")).collect(Collectors.counting()).intValue();
            
            int total=entry.getValue().size();
            
            details.setTotalabsent(totabs);
            details.setTotalclasses(total);
            details.setTotalpresent(totpre);
            
            double preper=(double)totpre/total;
            double absper=(double)totabs/total;
            
            details.setAbsentpercentage(Double.parseDouble(dec.format(absper)));
            details.setPresentpercentage(Double.parseDouble(dec.format(preper)));
            
            return details;
        }).collect(Collectors.toList());
        
        Collections.sort(reportList, (r1,r2)->Integer.compare(r1.getRollno(), r2.getRollno()));
        
        table.getItems().setAll(reportList);
    }

    private void clear(ActionEvent evt) {
        filterbyname.setSelected(false);
        table.getItems().clear();
    }

    private void export2Excel(ActionEvent evt) {
        export = new ExportAttendancereport(table);
        try {
            export.createFile().convertToExcel(filename.getText()).exportToFile();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(AttendanceReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generateExcel(ActionEvent evt) {
        export = new ExportAttendancereport(table);
        try {
            export.createFile().generateReport(filename.getText()).exportToFile();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(AttendanceReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
