/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.studentattendance.controller;

import com.attendance.main.Start;
import com.attendance.papers.dao.PapersDao;
import com.attendance.papers.model.Paper;
import com.attendance.student.dao.StudentDao;
import com.attendance.student.model.Student;
import com.attendance.studentattendance.dao.AttendanceDao;
import com.attendance.studentattendance.dao.ClassDetailsDao;
import com.attendance.studentattendance.dao.DailyStatsDao;
import com.attendance.studentattendance.model.Attendance;
import com.attendance.studentattendance.model.ClassDetails;
import com.attendance.studentattendance.model.DailyStats;
import com.attendance.util.DateTimerThread;
import com.attendance.util.Fxml;
import com.attendance.util.Message;
import com.attendance.util.MessageUtil;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTimePicker;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author Programmer Hrishav
 */
public class StudentAttendanceController extends BorderPane {

    @FXML
    private Label date;

    @FXML
    private Label facultyName;

    @FXML
    private TextField subjectTaught;

    @FXML
    private JFXTimePicker time;

    @FXML
    private Label department;

    @FXML
    private JFXButton loadStudents;

    @FXML
    private JFXComboBox<String> acayear;

    @FXML
    private JFXComboBox<String> year;

    @FXML
    private JFXComboBox<String> coursetype;

    @FXML
    private JFXButton clear;

    @FXML
    private Label totalStudents;

    @FXML
    private Label totalPresent;

    @FXML
    private Label totalAbsent;

    @FXML
    private Label presentPer;

    @FXML
    private Label AbsentPer;

    @FXML
    private TextField sid;

    @FXML
    private JFXButton searchStudent;

    @FXML
    private JFXButton updateAttendance;

    @FXML
    private JFXButton close;

    @FXML
    private JFXButton refresh;

    @FXML
    private JFXComboBox<String> papers;

    @FXML
    private JFXComboBox<String> semester;

    @FXML
    private VBox vb;

    private FXMLLoader loader;
    private final Parent parentScene;

    private final String faculty;
    private String acadamicyear;
    private int yyear;
    private String ccoursetype;
    private ObservableList<Node> nodes;
    private StudentAttendanceNodeController searched;

    private Thread thread;
    private Thread dt;

    private StudentDao dao;
    private AttendanceDao attendancedao;
    private ClassDetailsDao classdao;
    private DailyStatsDao dailydao;
    private PapersDao papersdao;

    private DailyStats daily;
    private ClassDetails classDetails;

    public StudentAttendanceController(Parent parentScene, String faculty) {
        this.parentScene = parentScene;
        this.faculty = faculty;
        loader = Fxml.getStudentAttendanceFXML();
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(StudentAttendanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        department.setText(SystemUtils.getDepartment());
        dt = DateTimerThread.newInstance().forLabel(DateTimerThread.DATE, date).init().thread();
        dt.start();
        refresh.setDisable(true);
        dao = (StudentDao) Start.app.getBean("studentregistration");
        classdao = (ClassDetailsDao) Start.app.getBean("classdetails");
        dailydao = (DailyStatsDao) Start.app.getBean("dailystats");
        attendancedao = (AttendanceDao) Start.app.getBean("attendance");
        papersdao = (PapersDao) Start.app.getBean("papers");
        facultyName.setText(faculty);
        totalStudents.setText("");
        totalPresent.setText("");
        totalAbsent.setText("");
        presentPer.setText("");
        AbsentPer.setText("");

        close.setOnAction(e -> {
            dt.stop();
            SwitchRoot.switchRoot(Start.st, parentScene);
        });
        loadStudents.setOnAction(this::loadStudents);
        searchStudent.setOnAction(this::searchStudent);
        refresh.setOnAction(this::refreshList);
        clear.setOnAction(this::clear);
        updateAttendance.setOnAction(this::updateAttendance);

        semester.getSelectionModel().selectedItemProperty().addListener((ol, o, n) -> {
            List<Paper> paperlist = papersdao.findBySemester(n);
            paperlist.stream().map(p -> p.getPaperCode()).forEach(papers.getItems()::add);
        });

        acayear.getItems().setAll("1st", "2nd", "3rd");
        List<String> years = dao.get("select distinct(year) from student order by year", String.class);
        year.getItems().setAll(years);
        coursetype.getItems().setAll("Honours", "Pass");

    }

    private void updateAttendance(ActionEvent evt) {
        if (time.getEditor().getText().isEmpty()) {
            MessageUtil.showError(Message.ERROR, "Attendance Load Students", "Please Select Class Time", ((Node)evt.getSource()).getScene().getWindow());
        } else if (subjectTaught.getText().isEmpty()) {
            MessageUtil.showError(Message.ERROR, "Attendance Load Students", "Please Enter Subject Taught In The Class", ((Node)evt.getSource()).getScene().getWindow());
        } else if (papers.getSelectionModel().getSelectedItem().isEmpty()) {
            MessageUtil.showError(Message.ERROR, "Attendance Load Students", "Please Select Paper", ((Node)evt.getSource()).getScene().getWindow());
        } else {
            String t = time.getEditor().getText();
            if (t.contains("AM")) {
                t = t.replace(" AM", "");
            }
            if (t.contains("PM")) {
                t = t.replace(" PM", "");
            }
            String classId = DateTime.now().toString(DateTimeFormat.forPattern("dd/MM/yyyy")) + "@" + t + "#" + acadamicyear + "__" + semester.getSelectionModel().getSelectedItem() + "_" + yyear+"&"+ccoursetype.charAt(0);
            classDetails = new ClassDetails();
            classDetails.setClassId(classId);
            classDetails.setDate(date.getText());
            classDetails.setFacultyName(faculty);
            classDetails.setSemester(semester.getSelectionModel().getSelectedItem());
            classDetails.setAcadamicyear(acadamicyear);
            classDetails.setSubjectTaught(subjectTaught.getText());
            classDetails.setTime(time.getEditor().getText());
            classDetails.setYear(yyear);
            classDetails.setPaper(papers.getSelectionModel().getSelectedItem());
            classDetails.setDepartment(department.getText());
            classDetails.setCoursetype(ccoursetype);
            
            String cid = classdao.save(classDetails);
            if (cid == null || cid.isEmpty()) {
                MessageUtil.showError(Message.ERROR, "Attendance Load Students", "Class Details Not Saved", ((Node)evt.getSource()).getScene().getWindow());
            } else {
                daily = new DailyStats();
                daily.setAbsentPercentage(Double.parseDouble(AbsentPer.getText().replace("%", "")));
                daily.setClassId(classDetails.getClassId());
                daily.setPresentPercentage(Double.parseDouble(presentPer.getText().replace("%", "")));
                daily.setTotalAbsent(Integer.parseInt(totalAbsent.getText()));
                daily.setTotalPresent(Integer.parseInt(totalPresent.getText()));
                daily.setTotalStudent(Integer.parseInt(totalStudents.getText()));

                dailydao.save(daily);

                vb.getChildren().stream().map(s -> (StudentAttendanceNodeController) s).map(att -> {
                    Attendance at = new Attendance();
                    at.setClassId(classDetails.getClassId());
                    at.setStatus(att.getAttendanceStatus());
                    at.setStudentId(att.getStudentId());
                    return at;
                }).forEach(attendancedao::save);
                
                MessageUtil.showError(Message.INFORMATION, "Attendance Load Students", "Class Details Saved Successfully\nDaily Stats Saved Successfully\nDaily Attendance Saved Successfully", ((Node)evt.getSource()).getScene().getWindow());
                thread.stop();
            }
        }
    }

    private void loadStudents(ActionEvent evt) {
        try {
            if (acayear.getSelectionModel().getSelectedIndex() < 0 || coursetype.getSelectionModel().getSelectedIndex() < 0 || year.getSelectionModel().getSelectedIndex() < 0) {
                throw new NullPointerException();
            } else {
                initThread();
                acadamicyear = acayear.getSelectionModel().getSelectedItem();
                if (acadamicyear.equals("1st")) {
                    semester.getItems().setAll("1st", "2nd");
                } else if (acadamicyear.equals("2nd")) {
                    semester.getItems().setAll("3rd", "4th");
                } else {
                    semester.getItems().setAll("5th", "6th");
                }
                yyear = Integer.parseInt(year.getSelectionModel().getSelectedItem());
                ccoursetype=coursetype.getSelectionModel().getSelectedItem();
                List<Student> list = new ArrayList<>(dao.findByAcadamicYearAndYear(acadamicyear, yyear));
                list=list.stream().filter(p->p.getCourseType().equals(ccoursetype)).collect(Collectors.toList());
                List<StudentAttendanceNodeController> nodes = list.stream().map(StudentAttendanceNodeController::new).collect(Collectors.toList());
                vb.getChildren().setAll(nodes);
                sortStudentList(vb);
                thread.start();
            }
        } catch (NullPointerException e) {
            MessageUtil.showError(Message.ERROR, "Attendance Load Students", "Fill The Missing Ones", ((Node)evt.getSource()).getScene().getWindow());
        }
    }

    private void clear(ActionEvent evt) {
        vb.getChildren().clear();
        sid.setText("");
        subjectTaught.setText("");
        nodes = null;
        searched = null;
        searchStudent.setDisable(false);
        refresh.setDisable(true);
        thread.stop();
        totalAbsent.setText("0");
        totalPresent.setText("0");
        totalStudents.setText("0");
        presentPer.setText("0.00 %");
        AbsentPer.setText("0.00 %");
        acayear.getSelectionModel().clearSelection();
        coursetype.getSelectionModel().clearSelection();
        year.getSelectionModel().clearSelection();
    }

    private void searchStudent(ActionEvent evt) {
        nodes = FXCollections.observableArrayList(vb.getChildren());
        searched = nodes.stream().map(n -> (StudentAttendanceNodeController) n).
                filter(n -> n.getStudentId().startsWith(sid.getText())).collect(Collectors.toList()).get(0);
        nodes.remove(searched);
        vb.getChildren().setAll(searched);
        searchStudent.setDisable(true);
        refresh.setDisable(false);
    }

    private void refreshList(ActionEvent evt) {
        StudentAttendanceNodeController node = (StudentAttendanceNodeController) vb.getChildren().get(0);
        nodes.add(node);
        vb.getChildren().setAll(nodes);
        sortStudentList(vb);
        searchStudent.setDisable(false);
        refresh.setDisable(true);
    }

    private void sortStudentList(VBox vb) {
        List<StudentAttendanceNodeController> sorted = vb.getChildren().stream().map(n -> (StudentAttendanceNodeController) n)
                .sorted((i1, i2) -> i1.getRollNo().compareTo(i2.getRollNo()))
                .collect(Collectors.toList());
        vb.getChildren().setAll(sorted);
    }

    private void initThread() {
        thread = new Thread(new Calculator());
    }

    private class Calculator implements Runnable {

        @Override
        public void run() {
            while (true) {
                DecimalFormat d = new DecimalFormat("#.##");
                ObservableList<Node> list = vb.getChildren();
                int tot = list.size();

                int totpre = list.stream().map(n -> (StudentAttendanceNodeController) n).filter(n -> n.getAttendanceStatus().equalsIgnoreCase("Present")).collect(Collectors.toList()).size();

                int totabs = list.stream().map(n -> (StudentAttendanceNodeController) n).filter(n -> n.getAttendanceStatus().equalsIgnoreCase("Absent")).collect(Collectors.toList()).size();

                float preper = ((float) totpre / tot) * 100;
                float absper = ((float) totabs / tot) * 100;

                Platform.runLater(() -> {
                    totalStudents.setText("" + tot);
                    totalPresent.setText("" + totpre);
                    totalAbsent.setText("" + totabs);
                    presentPer.setText("" + d.format(preper) + " %");
                    AbsentPer.setText("" + d.format(absper) + " %");
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(StudentAttendanceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
}