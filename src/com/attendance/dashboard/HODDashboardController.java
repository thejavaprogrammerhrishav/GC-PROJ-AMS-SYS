/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.dashboard;

import com.attendance.login.activity.dao.Activity;
import com.attendance.login.activity.model.LoginActivity;
import com.attendance.login.activity.service.LoginActivityService;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.personal.model.PersonalDetails;
import com.attendance.student.dao.StudentDao;
import com.attendance.student.service.StudentService;
import com.attendance.util.ExceptionDialog;
import com.attendance.util.Fxml;
import com.attendance.util.RootFactory;
import com.attendance.util.StageUtil;
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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.hslf.record.ExAviMovie;
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
    private JFXButton paper;

    @FXML
    private VBox list;

    @FXML
    private JFXButton security;

    private FXMLLoader fxml;
    private PersonalDetails details;

    private Thread blinker;

    private User user;
    private LoginActivity activity;
    private StudentService dao;
    private LoginActivityService act;
    private ExceptionDialog dialog;

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
        act = (LoginActivityService) Start.app.getBean("loginactivityservice");
        dao = (StudentService) Start.app.getBean("studentservice");
        dao.setParent(this);
        act.setParent(this);

        department.setText("Department :- " + SystemUtils.getDepartment());
        blinker = new Thread(this::blink);

        details = user.getDetails();

        security.setVisible(false);

        initLoginActivity(null);
        countStudents(null);
        checkQuestions();

        profilepic.setImage(new Image(new ByteArrayInputStream(user.getImage())));
        List<String> years = dao.findAllYear();
        Collections.sort(years);
        year.getItems().setAll("All");
        year.getItems().addAll(years);
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
        verifyfaculty.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getPendingRequestRoot(Start.st.getScene().getRoot(), SystemUtils.getDepartment(), false)));
        notes.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getNotesDashboardRoot(Start.st.getScene().getRoot(), details.getName())));
        routine.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getRoutineDashboardRoot(Start.st.getScene().getRoot())));
        paper.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getPaperRoot(Start.st.getScene().getRoot())));
    }

    private void countStudents(ActionEvent e) {
        int Sem1 = 0, Sem2 = 0, Sem3 = 0;

        if (year.getSelectionModel().getSelectedIndex() <= 0) {
            Sem1 = dao.countStudents("1st", SystemUtils.getDepartment());
            Sem2 = dao.countStudents("2nd", SystemUtils.getDepartment());
            Sem3 = dao.countStudents("3rd", SystemUtils.getDepartment());
        } else {
            String yyear = year.getSelectionModel().getSelectedItem();
            Sem1 = dao.countStudents("1st", Integer.parseInt(yyear), SystemUtils.getDepartment());
            Sem2 = dao.countStudents("2nd", Integer.parseInt(yyear), SystemUtils.getDepartment());
            Sem3 = dao.countStudents("3rd", Integer.parseInt(yyear), SystemUtils.getDepartment());
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

    private void checkQuestions() {
        if (!SystemUtils.getCurrentUser().hasSecurityQuestion()) {
            security.setStyle("");
            security.setVisible(true);
            security.setOnAction(this::updateSecurity);
            security.setContentDisplay(ContentDisplay.LEFT);
            blinker.start();
        } else {
            if(blinker.isAlive()){
                blinker.stop();
            }
            security.setVisible(true);
            security.setText("Security Answers");
            security.setStyle("-fx-text-fill: black;");
            security.setOnAction(this::updateSecurity);
            security.setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
    }

    private void updateSecurity(ActionEvent evt) {
         Stage st = StageUtil.newStage().fullScreen(true).fullScreenExitHint("").fullScreenExitKeyCombination(KeyCombination.NO_MATCH)
                .initStyle(StageStyle.UNDECORATED).initModality(Modality.WINDOW_MODAL).initOwner(Start.st).centerOnScreen().build();
      st.setScene(new Scene(RootFactory.getSecurityQuestionRoot(RootFactory.getHODDashboardRoot(), "New")));
      st.showAndWait();
      checkQuestions();
    }

    private void blink() {
        String c1 = "-fx-background-color: red";
        String c2 = "-fx-background-color: white";
        boolean b = true;
        while (true) {
            if (b) {
                b = false;
                Platform.runLater(() -> security.setStyle(c1));
            } else {
                b = true;
                Platform.runLater(() -> security.setStyle(c2));
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(PrincipalDashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
