/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.report.controller;

import com.attendance.faculty.dao.FacultyDao;
import com.attendance.main.Start;
import com.attendance.papers.dao.PapersDao;
import com.attendance.papers.model.Paper;
import com.attendance.report.model.AttendanceDetails;
import com.attendance.report.model.StudentCount;
import com.attendance.student.dao.StudentDao;
import com.attendance.student.model.Student;
import com.attendance.studentattendance.dao.AttendanceDao;
import com.attendance.studentattendance.dao.ClassDetailsDao;
import com.attendance.util.ExportAttendancereport;
import com.attendance.util.Fxml;
import com.attendance.util.Message;
import com.attendance.util.MessageUtil;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
    private AttendanceDao attendancedao;
    private ClassDetailsDao classdao;
    private FacultyDao facultydao;

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
        attendancedao = (AttendanceDao) Start.app.getBean("attendance");
        classdao = (ClassDetailsDao) Start.app.getBean("classdetails");
        facultydao = (FacultyDao) Start.app.getBean("facultyregistration");
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

        List<String> faculties = facultydao.findAll().stream().map(f -> f.getName()).collect(Collectors.toList());
        name.getItems().setAll(faculties);

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
        String fsql = "select group_concat(distinct(facultyname) separator ',') as faculty from classdetails where 1";
        String sql = "select *,count(*) as countpresent from attendance,classdetails where attendance.classId=classdetails.classId ";
        String csql = "select count(*) from classdetails where 1";

        boolean b = acadamicyear.getSelectionModel().getSelectedIndex() > -1 && semester.getSelectionModel().getSelectedIndex() > -1
                && year.getSelectionModel().getSelectedIndex() > -1 && paper.getSelectionModel().getSelectedIndex() > -1
                && coursetype.getSelectionModel().getSelectedIndex() > -1;
        if (b) {
            if (filterbyname.isSelected()) {
                sql = sql + " and facultyname='" + name.getSelectionModel().getSelectedItem() + "'";
                csql = csql + " and facultyname='" + name.getSelectionModel().getSelectedItem() + "'";
            }
            sql = sql + " and acadamicyear='" + acadamicyear.getSelectionModel().getSelectedItem() + "'";
            fsql = fsql + " and acadamicyear='" + acadamicyear.getSelectionModel().getSelectedItem() + "'";
            csql = csql + " and acadamicyear='" + acadamicyear.getSelectionModel().getSelectedItem() + "'";

            sql = sql + " and semester='" + semester.getSelectionModel().getSelectedItem().replace(" Semester", "") + "'";
            fsql = fsql + " and semester='" + semester.getSelectionModel().getSelectedItem().replace(" Semester", "") + "'";
            csql = csql + " and semester='" + semester.getSelectionModel().getSelectedItem().replace(" Semester", "") + "'";

            sql = sql + " and year=" + year.getSelectionModel().getSelectedItem();
            fsql = fsql + " and year=" + year.getSelectionModel().getSelectedItem();
            csql = csql + " and year=" + year.getSelectionModel().getSelectedItem();

            sql = sql + " and papercode='" + paper.getSelectionModel().getSelectedItem() + "'";
            fsql = fsql + " and papercode='" + paper.getSelectionModel().getSelectedItem() + "'";
            csql = csql + " and papercode='" + paper.getSelectionModel().getSelectedItem() + "'";

            sql = sql + " and coursetype='" + coursetype.getSelectionModel().getSelectedItem() + "'";
            fsql = fsql + " and coursetype='" + coursetype.getSelectionModel().getSelectedItem() + "'";
            csql = csql + " and coursetype='" + coursetype.getSelectionModel().getSelectedItem() + "'";
            
             sql=sql+" and status='Present' group by studentid";
             


        } else {
            MessageUtil.showError(Message.INFORMATION, "Student Attendance Report", "Enter The Unfilled Values", ((Node) evt.getSource()).getScene().getWindow());
        }
        System.out.println("SQL:   " + sql);
        System.out.println("CSQL:  " + csql);
        System.out.println("FSQL:   " + fsql);

        List<String> faculty_name = classdao.get(fsql, String.class);

        List<Student> stud = studentdao.findByAcadamicYearAndYear(acadamicyear.getSelectionModel().getSelectedItem(), Integer.parseInt(year.getSelectionModel().getSelectedItem()));
        List<Student> students=stud.stream().filter(p->p.getCourseType().equals(coursetype.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        List<StudentCount> vals = attendancedao.getCustom(sql, StudentCount.class);

        int total = classdao.get(csql);

        List<AttendanceDetails> collect = vals.stream().map(c -> {
            Student s = students.stream().filter(p -> p.getId().equals(c.getStudentid())).collect(Collectors.toList()).get(0);
            AttendanceDetails attendance = new AttendanceDetails();
            attendance.setStudentname(s.getName());
            attendance.setRollno(s.getRollno());
            attendance.setSemester(s.getAcadamicyear());
            attendance.setYear(s.getYear());
            attendance.setFacultyname(faculty_name.get(0));
            attendance.setTotalclasses(total);
            attendance.setTotalpresent(c.getCountpresent());
            attendance.setTotalabsent(total - c.getCountpresent());

            double pp, ap;
            pp = ((double) c.getCountpresent() / total) * 100;
            ap = ((double) (total - c.getCountpresent()) / total) * 100;

            attendance.setPresentpercentage(Double.parseDouble(dec.format(pp)));
            attendance.setAbsentpercentage(Double.parseDouble(dec.format(ap)));

            return attendance;
        }).collect(Collectors.toList());
        table.getItems().setAll(collect);

        List<String> ids = students.stream().map(e -> e.getId()).collect(Collectors.toList());
        ids.removeAll(vals.stream().map(e -> e.getStudentid()).collect(Collectors.toList()));

        ids.stream().map(c -> {
            Student s = students.stream().filter(p -> p.getId().equals(c)).collect(Collectors.toList()).get(0);
            AttendanceDetails attendance = new AttendanceDetails();
            attendance.setStudentname(s.getName());
            attendance.setRollno(s.getRollno());
            attendance.setSemester(s.getAcadamicyear());
            attendance.setYear(s.getYear());
            attendance.setFacultyname(faculty_name.get(0));
            attendance.setTotalclasses(total);
            attendance.setTotalpresent(0);
            attendance.setTotalabsent(total);

            double pp, ap;
            pp = 0.0;
            ap = 100.0;

            attendance.setPresentpercentage(Double.parseDouble(dec.format(pp)));
            attendance.setAbsentpercentage(Double.parseDouble(dec.format(ap)));

            return attendance;
        }).forEach(table.getItems()::add);
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
