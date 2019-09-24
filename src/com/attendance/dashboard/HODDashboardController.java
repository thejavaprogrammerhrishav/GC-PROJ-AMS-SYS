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
import com.attendance.student.dao.StudentDao;
import com.attendance.util.ClockUtil;
import com.attendance.util.DateTimerThread;
import com.attendance.util.Fxml;
import com.attendance.util.RootFactory;
import com.attendance.util.StageUtil;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Programmer Hrishav
 */
public class HODDashboardController extends AnchorPane {

    @FXML
    private Label time;

    @FXML
    private Label userlabel;

    @FXML
    private Label date;

    @FXML
    private JFXButton registernewstudent;

    @FXML
    private JFXButton updatestudent;

    @FXML
    private JFXButton addnewfaculty;

    @FXML
    private JFXButton updatefaculty;

    @FXML
    private JFXButton adminsignup;

    @FXML
    private JFXButton facultysignup;

    @FXML
    private JFXButton settings;

    @FXML
    private JFXButton logout;

    @FXML
    private JFXButton viewstudentdetails;

    @FXML
    private JFXButton viewfacultydetails;

    @FXML
    private Label date1;

    @FXML
    private JFXComboBox<String> acadamicyear;

    @FXML
    private JFXButton refresh;

    @FXML
    private Label sem1;

    @FXML
    private Label sem2;

    @FXML
    private Label sem3;

    @FXML
    private Pane pane1;

    @FXML
    private JFXButton activityrefresh;

    @FXML
    private VBox logins;

    @FXML
    private JFXButton studentattendance;

    @FXML
    private JFXButton studentattendancereport;

    @FXML
    private JFXButton uploadnotes;

    @FXML
    private JFXButton downloadnotes;

    @FXML
    private JFXButton uploadunittestmarks;

    @FXML
    private JFXButton unittestreport;

    private FXMLLoader fxml;
    private Thread timer;
    private Thread dater;

    private User user;
    private LoginActivity activity;
    private StudentDao dao;
    private Activity act;

    public HODDashboardController() {
        this.user = SystemUtils.getCurrentUser();
        this.activity = SystemUtils.getActivity();
        fxml = Fxml.getAdminDashboardFXML();
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
        userlabel.setText("Welcome back @  " + user.getContact());
        initLoginActivity(null);
        countStudents(null);
        timer = DateTimerThread.newInstance().forLabel(DateTimerThread.TIME, time).init().thread();
        timer.start();
        dater = DateTimerThread.newInstance().forLabel(DateTimerThread.DATE, date).init().thread();
        dater.start();

        acadamicyear.getItems().add("Select Acadamic Year");
        List<String> years = new ArrayList<>(dao.get("select distinct(year) from student order by year", String.class));
        Collections.sort(years);
        years.stream().forEach(acadamicyear.getItems()::add);

        logout.setOnAction(new Logout(activity));

        registernewstudent.setOnAction(new StudentRegister());

        adminsignup.setOnAction(new AdministratorSignup());
        facultysignup.setOnAction(new FacultySignup());

        viewstudentdetails.setOnAction(new ViewStudentDetails());
        updatestudent.setOnAction(new StudentUpdate());
        refresh.setOnAction(this::countStudents);
        activityrefresh.setOnAction(this::initLoginActivity);

       
        viewfacultydetails.setOnAction(new ViewFaculty());

        studentattendance.setOnAction(new StudentAttendance());
        studentattendancereport.setOnAction(new AttendanceReport());

        settings.setOnAction(new Settings());

        uploadnotes.setOnAction(new UploadNotes());
        downloadnotes.setOnAction(new DownloadNotes());
        
        uploadunittestmarks.setOnAction(new UploadUnitTest());
        
        addnewfaculty.setOnAction(e->SwitchRoot.switchRoot(Start.st, RootFactory.getUserProfileRoot("hod")));
    }

    private void countStudents(ActionEvent e) {
        int Sem1, Sem2, Sem3;

        if (acadamicyear.getSelectionModel().getSelectedIndex() <= 0) {
            Sem1 = dao.countStudents("1st");
            Sem2 = dao.countStudents("2nd");

            Sem3 = dao.countStudents("3rd");
        } else {
            String year = acadamicyear.getSelectionModel().getSelectedItem();
            Sem1 = dao.countStudents("1st", year);
            Sem2 = dao.countStudents("2nd", year);
            Sem3 = dao.countStudents("3rd", year);
        }

        sem1.setText("" + Sem1);
        sem2.setText("" + Sem2);
        sem3.setText("" + Sem3);
    }

    private void initLoginActivity(ActionEvent e) {
        List<LoginActivity> allLogins = new ArrayList<>(act.findAll());
        Collections.reverse(allLogins);
        List<LoginActivityController> activityControllers = allLogins.stream().map(c -> new LoginActivityController(c)).collect(Collectors.toList());
        logins.getChildren().setAll(activityControllers);
    }

    private class StudentRegister implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            Stage st = StageUtil.newStage().centerOnScreen().fullScreen(false).fullScreenExitHint("").fullScreenExitKeyCombination(null)
                    .initModality(Modality.WINDOW_MODAL).initOwner(((Node) t.getSource()).getScene().getWindow()).initStyle(StageStyle.UNDECORATED)
                    .build();
            st.getScene().setRoot(RootFactory.getRegisterStudentRoot());
            st.show();
        }
    }

    private class AdministratorSignup implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
           SwitchRoot.switchRoot(Start.st, RootFactory.getHODSignupRoot(Start.st.getScene().getRoot()));
        }
    }

    private class FacultySignup implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
           SwitchRoot.switchRoot(Start.st, RootFactory.getFacultySignupRoot(Start.st.getScene().getRoot()));
        }
    }

    private class ViewStudentDetails implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            Stage st = StageUtil.newStage().centerOnScreen().fullScreen(false).fullScreenExitHint("").fullScreenExitKeyCombination(null)
                    .initModality(Modality.WINDOW_MODAL).initOwner(((Node) t.getSource()).getScene().getWindow()).initStyle(StageStyle.UNDECORATED)
                    .build();
            st.getScene().setRoot(RootFactory.getViewStudentDetailsRoot(SystemUtils.getDepartment(),HODDashboardController.this.getScene().getRoot()));
            st.show();
        }
    }

    private class StudentUpdate implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            Stage st = StageUtil.newStage().centerOnScreen().fullScreen(false).fullScreenExitHint("").fullScreenExitKeyCombination(null)
                    .initModality(Modality.WINDOW_MODAL).initOwner(((Node) t.getSource()).getScene().getWindow()).initStyle(StageStyle.UNDECORATED)
                    .build();
            st.getScene().setRoot(RootFactory.getStudentUpdateRoot());
            st.show();
        }
    }


    private class ViewFaculty implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            Stage st = StageUtil.newStage().centerOnScreen().fullScreen(false).fullScreenExitHint("").fullScreenExitKeyCombination(null)
                    .initModality(Modality.WINDOW_MODAL).initOwner(((Node) t.getSource()).getScene().getWindow()).initStyle(StageStyle.UNDECORATED)
                    .build();
            st.getScene().setRoot(RootFactory.getViewFacultyRoot(SystemUtils.getDepartment(),HODDashboardController.this.getScene().getRoot()));
            st.show();
        }
    }

    private class StudentAttendance implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            Stage st = StageUtil.newStage().centerOnScreen().fullScreen(false).fullScreenExitHint("").fullScreenExitKeyCombination(null)
                    .initModality(Modality.WINDOW_MODAL).initOwner(((Node) t.getSource()).getScene().getWindow()).initStyle(StageStyle.UNDECORATED)
                    .build();
            st.getScene().setRoot(RootFactory.getSelectFacultyRoot("attendance"));
            st.show();
        }
    }

    private class AttendanceReport implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            SwitchRoot.switchRoot(Start.st, RootFactory.getAttendanceReportRoot(Start.st.getScene().getRoot()));
        }
    }

    private class Settings implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            SwitchRoot.switchRoot(Start.st, RootFactory.getSettingsRoot(Start.st.getScene().getRoot()));
        }
    }

    private class DownloadNotes implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            SwitchRoot.switchRoot(Start.st, RootFactory.getDownloadNotesRoot(Start.st.getScene().getRoot()));
        }
    }

    private class UploadNotes implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            Stage st = StageUtil.newStage().centerOnScreen().fullScreen(false).fullScreenExitHint("").fullScreenExitKeyCombination(null)
                    .initModality(Modality.WINDOW_MODAL).initOwner(((Node) t.getSource()).getScene().getWindow()).initStyle(StageStyle.UNDECORATED)
                    .build();
            st.getScene().setRoot(RootFactory.getSelectFacultyRoot("uploadnotes"));
            st.show();
        }
    }
    
     private class UploadUnitTest implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            Stage st = StageUtil.newStage().centerOnScreen().fullScreen(false).fullScreenExitHint("").fullScreenExitKeyCombination(null)
                    .initModality(Modality.WINDOW_MODAL).initOwner(((Node) t.getSource()).getScene().getWindow()).initStyle(StageStyle.UNDECORATED)
                    .build();
            st.getScene().setRoot(RootFactory.getUploadMarksRoot());
            st.show();
        }
    }
}
