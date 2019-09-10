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
import com.attendance.util.ConnectionThread;
import com.attendance.util.DateTimerThread;
import com.attendance.util.Fxml;
import com.attendance.util.RootFactory;
import com.attendance.util.StageUtil;
import com.attendance.util.SwitchRoot;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Programmer Hrishav
 */
public class FacultyDashboardController extends AnchorPane {

    @FXML
    private JFXButton registernewstudent;

    @FXML
    private JFXButton updatestudent;

    @FXML
    private JFXButton viewstudentdetails;

    @FXML
    private JFXButton logout;

    @FXML
    private Label userlabel;

    @FXML
    private Label sem1;

    @FXML
    private Label sem2;

    @FXML
    private Label sem3;


    @FXML
    private JFXComboBox<String> acadamicyear;

    @FXML
    private JFXButton refresh;

    @FXML
    private Label serverproduct;

    @FXML
    private Label serverusername;

    @FXML
    private Label serverstatus;

    @FXML
    private JFXButton viewfacultydetails;

    @FXML
    private JFXButton uploadnotes;

    @FXML
    private JFXButton downloadnotes;

    @FXML
    private JFXButton studentattendance;

    @FXML
    private Label date;

    @FXML
    private Label time;

    private FXMLLoader fxml;
    private Thread timer;
    private Thread dater;

    private User user;
    private LoginActivity activity;
    private StudentDao dao;
    private Activity act;
    private Thread server;

    public FacultyDashboardController(User user, LoginActivity activity) {
        this.user = user;
        this.activity = activity;
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
        server=new ConnectionThread(serverproduct, serverusername, serverstatus);
        server.start();
        act = (Activity) Start.app.getBean("loginactivity");
        dao = (StudentDao) Start.app.getBean("studentregistration");
        userlabel.setText("Welcome back @ " + user.getName());
        countStudents(null);
        timer = DateTimerThread.newInstance().forLabel(DateTimerThread.TIME, time).init().thread();
        timer.start();
        dater = DateTimerThread.newInstance().forLabel(DateTimerThread.DATE, date).init().thread();
        dater.start();
        acadamicyear.getItems().add("Select Acadamic Year");
        List<String> years = new ArrayList<>(dao.get("select distinct(year) from student", String.class));
        Collections.sort(years);
        years.stream().forEach(acadamicyear.getItems()::add);

        logout.setOnAction(new Logout(activity));

        registernewstudent.setOnAction(new StudentRegister());
        viewstudentdetails.setOnAction(new ViewStudentDetails());
        updatestudent.setOnAction(new StudentUpdate());
        refresh.setOnAction(this::countStudents);

        viewfacultydetails.setOnAction(new ViewFaculty());

        studentattendance.setOnAction(new StudentAttendance());
        serverstatus.textProperty().addListener(this::statusListener);
        
        uploadnotes.setOnAction(new Upload());
        downloadnotes.setOnAction(new Download());
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
    
    public void statusListener(ObservableValue<? extends String> ov, String t, String t1) {
        if(t1.equalsIgnoreCase("Connected")){
            serverstatus.setStyle("-fx-background-color: forestgreen;");
        }
        else{
            serverstatus.setStyle("-fx-background-color: red;");
        }
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

    private class ViewStudentDetails implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            Stage st = StageUtil.newStage().centerOnScreen().fullScreen(false).fullScreenExitHint("").fullScreenExitKeyCombination(null)
                    .initModality(Modality.WINDOW_MODAL).initOwner(((Node) t.getSource()).getScene().getWindow()).initStyle(StageStyle.UNDECORATED)
                    .build();
            st.getScene().setRoot(RootFactory.getViewStudentDetailsRoot());
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
            st.getScene().setRoot(RootFactory.getViewFacultyRoot());
            st.show();
        }
    }

    private class StudentAttendance implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            SwitchRoot.switchRoot(Start.st, RootFactory.getStudentAttendanceRoot(Start.st.getScene().getRoot(), user.getName()));
        }
    }
    
    private class Upload implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            SwitchRoot.switchRoot(Start.st, RootFactory.getUploadNotesRoot(Start.st.getScene().getRoot(), user.getName()));
        }
    }
    
    private class Download implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            SwitchRoot.switchRoot(Start.st, RootFactory.getDownloadNotesRoot(Start.st.getScene().getRoot()));
        }
    }
}
