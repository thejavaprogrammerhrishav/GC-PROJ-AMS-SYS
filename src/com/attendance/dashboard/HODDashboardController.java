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
import com.attendance.personal.dao.PersonalDetailsDao;
import com.attendance.personal.model.PersonalDetails;
import com.attendance.student.dao.StudentDao;
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
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author Programmer Hrishav
 */
public class HODDashboardController extends AnchorPane {

    @FXML
    private JFXButton registerstudent;

    @FXML
    private JFXButton updatestudent;

    @FXML
    private JFXButton settings;

    @FXML
    private JFXButton viewstudent;

    @FXML
    private JFXButton viewfaculty;

    @FXML
    private JFXButton routine;

    @FXML
    private JFXButton myprofile;

    @FXML
    private JFXButton changepassword;

    @FXML
    private JFXButton logout;

    @FXML
    private ImageView profilepic;

    @FXML
    private Label department;

    @FXML
    private Label aca1stcount;

    @FXML
    private Label aca2ndcount;

    @FXML
    private Label aca3rdcount;

    @FXML
    private JFXComboBox<String> year;

    @FXML
    private JFXButton refresh;

    @FXML
    private JFXButton dailyattendance;

    @FXML
    private JFXButton attendancereport;

    @FXML
    private JFXButton uploadmarks;

    @FXML
    private JFXButton unittestreport;

    @FXML
    private JFXButton notes;

    @FXML
    private JFXButton verifyfaculty;

    @FXML
    private JFXButton loginrefresh;

    @FXML
    private Label lastupdated;

    @FXML
    private VBox list;

    private FXMLLoader fxml;
    private Thread timer;
    private Thread dater;
    private PersonalDetails details;

    private User user;
    private LoginActivity activity;
    private StudentDao dao;
    private Activity act;
    private PersonalDetailsDao pdao;

    public HODDashboardController() {
        this.user = SystemUtils.getCurrentUser();
        this.activity = SystemUtils.getActivity();
        fxml = Fxml.getHODDashboardFXML();
        fxml.setRoot(HODDashboardController.this);
        fxml.setController(HODDashboardController.this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(HODDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        act = (Activity) Start.app.getBean("loginactivity");
        dao = (StudentDao) Start.app.getBean("studentregistration");
        pdao = (PersonalDetailsDao) Start.app.getBean("personal");
        department.setText("Department :- " + SystemUtils.getDepartment());
        details = pdao.findById(user.getPersonalid());
        initLoginActivity(null);
        countStudents(null);
        profilepic.setImage(new Image(new ByteArrayInputStream(user.getImage())));
        List<String> years = new ArrayList<>(dao.get("select distinct(year) from student order by year", String.class));
        Collections.sort(years);
        year.getItems().setAll(years);
        buttonactions();
    }

    private void buttonactions() {
        logout.setOnAction(new Logout(activity));
        refresh.setOnAction(this::countStudents);
        loginrefresh.setOnAction(this::initLoginActivity);
        myprofile.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getUserProfileRoot("hod")));
        changepassword.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getChangePasswordRoot(Start.st.getScene().getRoot())));
        registerstudent.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getRegisterStudentRoot(Start.st.getScene().getRoot())));
        updatestudent.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getStudentUpdateRoot(Start.st.getScene().getRoot())));
        viewstudent.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getViewStudentDetailsRoot(SystemUtils.getDepartment(), Start.st.getScene().getRoot())));
        viewfaculty.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getViewFacultyRoot(SystemUtils.getDepartment(), Start.st.getScene().getRoot())));
        settings.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getSettingsRoot(Start.st.getScene().getRoot())));
        dailyattendance.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getStudentAttendanceRoot(Start.st.getScene().getRoot(), details.getName())));
        attendancereport.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getAttendanceReportRoot(Start.st.getScene().getRoot())));
        uploadmarks.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getUploadMarksRoot(Start.st.getScene().getRoot())));
        unittestreport.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getUnitTestReportRoot(Start.st.getScene().getRoot())));
        verifyfaculty.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getPendingRequestRoot(Start.st.getScene().getRoot(), SystemUtils.getDepartment())));
        notes.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getNotesDashboardRoot(Start.st.getScene().getRoot(), details.getName())));

    }

    private void countStudents(ActionEvent e) {
        int Sem1 = 0, Sem2 = 0, Sem3 = 0;

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
        String time = DateTime.now().toString(DateTimeFormat.forPattern("hh : mm : ss a"));
        lastupdated.setText("Last Updated :- " + time);
        List<LoginActivity> allLogins = new ArrayList<>(act.findByDepartment(SystemUtils.getDepartment()));
        Collections.reverse(allLogins);
        List<HODLoginActivityController> activityControllers = allLogins.stream().map(c -> new HODLoginActivityController(c)).collect(Collectors.toList());
        list.getChildren().setAll(activityControllers);
    }

}
