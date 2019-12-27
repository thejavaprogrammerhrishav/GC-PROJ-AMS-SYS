/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.dashboard;

import com.attendance.login.activity.dao.Activity;
import com.attendance.login.activity.model.LoginActivity;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.personal.model.PersonalDetails;
import com.attendance.student.dao.StudentDao;
import com.attendance.util.DateTimerThread;
import com.attendance.util.Fxml;
import com.attendance.util.RootFactory;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Programmer Hrishav
 */
public class FacultyDashboardController extends AnchorPane {

    @FXML
    private ImageView profilepic;

    @FXML
    private JFXButton myprofile;

    @FXML
    private JFXButton changepassword;

    @FXML
    private JFXButton logout;

    @FXML
    private JFXButton registernewstudent;

    @FXML
    private JFXButton updatestudentdetails;

    @FXML
    private JFXButton viewstudentdetails;

    @FXML
    private JFXButton viewfacultydetails;

    @FXML
    private JFXButton classdetails;

    @FXML
    private JFXButton dailystatistics;

    @FXML
    private JFXButton settings;

    @FXML
    private JFXButton studentattendance;

    @FXML
    private Label department;

    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private Label aca2ndcount;

    @FXML
    private Label aca3rdcount;

    @FXML
    private Label aca1stcount;

    @FXML
    private JFXComboBox<String> year;

    @FXML
    private JFXButton refresh;

    @FXML
    private JFXButton notesdashboard;

    @FXML
    private JFXButton loginrefresh;

    @FXML
    private JFXButton routine;

    @FXML
    private JFXButton uploadmarks;

    @FXML
    private VBox list;

    private FXMLLoader fxml;
    private Thread timer;
    private Thread dater;

    private User user;
    private LoginActivity activity;
    private StudentDao dao;
    private Activity act;
    private PersonalDetails details;

    public FacultyDashboardController() {
        this.user = SystemUtils.getCurrentUser();
        this.activity = SystemUtils.getActivity();
        fxml = Fxml.getFacultyDashboardFXML();
        fxml.setRoot(FacultyDashboardController.this);
        fxml.setController(FacultyDashboardController.this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(FacultyDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        department.setText(SystemUtils.getDepartment());
        act = (Activity) Start.app.getBean("loginactivity");
        dao = (StudentDao) Start.app.getBean("studentregistration");
        details = user.getDetails();
        profilepic.setImage(new Image(new ByteArrayInputStream(user.getImage())));
        countStudents(null);
        initLoginActivity(null);

        timer = DateTimerThread.newInstance().forLabel(DateTimerThread.TIME, time).init().thread();
        timer.start();
        dater = DateTimerThread.newInstance().forLabel(DateTimerThread.DATE, date).init().thread();
        dater.start();
        List<String> years = new ArrayList<>(dao.get("select distinct(year) from student", String.class));
        Collections.sort(years);
        year.getItems().setAll(years);

        loginrefresh.setOnAction(this::initLoginActivity);
        logout.setOnAction(new Logout(activity));
        myprofile.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getUserProfileRoot("faculty")));
        changepassword.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getChangePasswordRoot(Start.st.getScene().getRoot())));

        registernewstudent.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getRegisterStudentRoot(Start.st.getScene().getRoot())));
        viewstudentdetails.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getViewStudentDetailsRoot(SystemUtils.getDepartment(), Start.st.getScene().getRoot())));
        updatestudentdetails.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getStudentUpdateRoot(Start.st.getScene().getRoot())));
        refresh.setOnAction(this::countStudents);
        uploadmarks.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getUploadMarksRoot(Start.st.getScene().getRoot())));
        viewfacultydetails.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getViewFacultyRoot(SystemUtils.getDepartment(), Start.st.getScene().getRoot())));
        notesdashboard.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getNotesDashboardRoot(Start.st.getScene().getRoot(), details.getName())));
        studentattendance.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getStudentAttendanceRoot(Start.st.getScene().getRoot(), details.getName())));
        classdetails.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getClassDetailsRoot(Start.st.getScene().getRoot(), details.getName())));
        routine.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getRoutineDashboardRoot(Start.st.getScene().getRoot())));
        dailystatistics.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getDailyStatsRoot(Start.st.getScene().getRoot())));
    }

    private void countStudents(ActionEvent e) {
        int Sem1, Sem2, Sem3;

        if (year.getSelectionModel().getSelectedIndex() < 0) {
            Sem1 = dao.countStudents("1st", SystemUtils.getDepartment());
            Sem2 = dao.countStudents("2nd", SystemUtils.getDepartment());
            Sem3 = dao.countStudents("3rd", SystemUtils.getDepartment());
        } else {
            String yyear = year.getSelectionModel().getSelectedItem();
            Sem1 = dao.countStudents("1st", yyear, SystemUtils.getDepartment());
            Sem2 = dao.countStudents("2nd", yyear, SystemUtils.getDepartment());
            Sem3 = dao.countStudents("3rd", yyear, SystemUtils.getDepartment());
        }

        aca1stcount.setText("" + Sem1);
        aca2ndcount.setText("" + Sem2);
        aca3rdcount.setText("" + Sem3);
    }

    private void initLoginActivity(ActionEvent e) {
        List<LoginActivity> allLogins = new ArrayList<>(act.findByName(details.getName()));
        allLogins = allLogins.stream().filter(p -> p.getDepartment().equals(SystemUtils.getDepartment())).collect(Collectors.toList());
        Collections.reverse(allLogins);
        List<FacultyLoginActivityController> activityControllers = allLogins.stream().map(c -> new FacultyLoginActivityController(c)).collect(Collectors.toList());
        list.getChildren().setAll(activityControllers);
    }
}
