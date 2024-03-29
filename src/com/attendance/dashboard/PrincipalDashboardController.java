/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.dashboard;

import com.attendance.login.activity.dao.Activity;
import com.attendance.login.activity.model.LoginActivity;
import com.attendance.login.activity.service.LoginActivityService;
import com.attendance.login.dao.Login;
import com.attendance.login.service.LoginService;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

/**
 *
 * @author pc
 */
public class PrincipalDashboardController extends AnchorPane {

    @FXML
    private JFXButton student;

    @FXML
    private JFXButton faculty;

    @FXML
    private JFXButton classdetails;

    @FXML
    private JFXButton dailyclassreport;

    @FXML
    private JFXButton settings;

    @FXML
    private JFXButton myprofile;

    @FXML
    private JFXButton changepassword;

    @FXML
    private JFXButton logout;

    @FXML
    private JFXComboBox<String> department;

    @FXML
    private JFXComboBox<String> year;

    @FXML
    private JFXButton open;

    @FXML
    private JFXButton refresh;

    @FXML
    private VBox list;

    @FXML
    private JFXButton totalstudents;

    @FXML
    private JFXButton totalfaculties;

    @FXML
    private JFXButton totaldepartments;

    @FXML
    private JFXButton principalaccounts;

    @FXML
    private JFXButton hodaccounts;

    @FXML
    private JFXButton verifyprincipal;

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXButton verifyhod;

    @FXML
    private Label pending;

    @FXML
    private ImageView profilepic;

    @FXML
    private JFXButton security;

    private User principal;
    private LoginActivity activity;
    private FXMLLoader fxml;
    private LoginActivityService act;
    private LoginService login;

    private Task<Integer> task;
    private Thread thread;

    private Thread blinker;

    private StudentService dao;
    private ExceptionDialog dialog;

    public PrincipalDashboardController() {
        this.principal = SystemUtils.getCurrentUser();
        this.activity = SystemUtils.getActivity();

        fxml = Fxml.getPrincipalDashboardFXML();
        fxml.setController(this);
        fxml.setRoot(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void initialize() {
        act = (LoginActivityService) Start.app.getBean("loginactivityservice");
        login = (LoginService) Start.app.getBean("loginservice");
        dao = (StudentService) Start.app.getBean("studentservice");
        dao.setParent(this);
        login.setParent(this);
        act.setParent(this);

        department.getItems().setAll(Arrays.asList(SystemUtils.getDepartments()));
        blinker = new Thread(this::blink);

        List<String> years = dao.findAllYear();
        Collections.sort(years);
        year.getItems().setAll(years);

        security.setVisible(false);

        checkQuestions();
        buttonActions();
        profilepic.setImage(new Image(new ByteArrayInputStream(principal.getImage())));
        initLoginActivity(null);

        taskInit();
        thread.start();

    }

    private void taskInit() {
        task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                while (true) {
                    Platform.runLater(() -> PrincipalDashboardController.this.init());
                    Thread.sleep(2000);
                }

            }
        };
        thread = new Thread(task);
    }

    private void buttonActions() {
        pane.getChildren().setAll(RootFactory.getPrincipalDashboardStudentNodeRoot());
        refresh.setOnAction(this::initLoginActivity);
        myprofile.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getUserProfileRoot("principal")));
        changepassword.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getChangePasswordRoot(Start.st.getScene().getRoot())));
        student.setOnAction(new ShowDepartmentPage("Student"));
        logout.setOnAction(new Logout(activity));
        faculty.setOnAction(new ShowDepartmentPage("Faculty"));
        classdetails.setOnAction(new ShowDepartmentPage("Class Details"));
        dailyclassreport.setOnAction(new ShowDepartmentPage("Daily Class Details"));
        verifyhod.setOnAction(new ShowDepartmentPage("verifyhod"));
        totalstudents.setOnAction(e -> pane.getChildren().setAll(RootFactory.getPrincipalDashboardStudentNodeRoot()));
        totalfaculties.setOnAction(e -> pane.getChildren().setAll(RootFactory.getPrincipalDashboardFacultyNodeRoot()));
        totaldepartments.setOnAction(e -> pane.getChildren().setAll(RootFactory.getPrincipalDashboardDepartmentNodeRoot()));
        principalaccounts.setOnAction(e -> pane.getChildren().setAll(RootFactory.getPrincipalDashboardPrincipalAccountNodeRoot()));
        hodaccounts.setOnAction(e -> pane.getChildren().setAll(RootFactory.getPrincipalDashboardHODAccountNodeRoot()));
        settings.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getPrincipalSettingsRoot(Start.st.getScene().getRoot())));
        verifyprincipal.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getPendingRequestRoot(this.getScene().getRoot(), "N/A", true)));
        open.setOnAction(this::routine);
    }

    private void routine(ActionEvent evt) {
        String dept = department.getSelectionModel().getSelectedItem();
        String yr = year.getSelectionModel().getSelectedItem();
        if (dept != null && yr != null) {
            SwitchRoot.switchRoot(Start.st, RootFactory.getPrincipalRoutineDashboardRoot(Start.st.getScene().getRoot(), dept, yr));
        }
    }

    private void init() {
        int collected = login.count("select count(*) from loginuser where type='HOD' and status='Pending'");
        pending.setText("" + collected);
    }

    private void initLoginActivity(ActionEvent e) {
        List<LoginActivity> allLogins = new ArrayList<>(act.findByDepartment("N/A"));
        allLogins = allLogins.stream().filter(p -> p.getUserType().equals("Principal")).collect(Collectors.toList());
        Collections.reverse(allLogins);
        List<PrincipalLoginActivityController> activityControllers = allLogins.stream().map(c -> new PrincipalLoginActivityController(c)).collect(Collectors.toList());
        list.getChildren().setAll(activityControllers);
    }

    private class ShowDepartmentPage implements EventHandler<ActionEvent> {

        private final String type;

        public ShowDepartmentPage(String type) {
            this.type = type;
        }

        @Override
        public void handle(ActionEvent t) {
            Stage st = StageUtil.newStage().centerOnScreen().fullScreen(false).fullScreenExitHint("").fullScreenExitKeyCombination(null)
                    .initModality(Modality.WINDOW_MODAL).initOwner(((Node) t.getSource()).getScene().getWindow()).initStyle(StageStyle.UNDECORATED)
                    .build();
            if (type.equals("Student")) {
                st.getScene().setRoot(RootFactory.getSelectDepartmentRoot("Student", PrincipalDashboardController.this.getScene().getRoot()));

            } else if (type.equals("Faculty")) {
                st.getScene().setRoot(RootFactory.getSelectDepartmentRoot("Faculty", PrincipalDashboardController.this.getScene().getRoot()));

            } else if (type.equals("Class Details")) {
                st.getScene().setRoot(RootFactory.getSelectDepartmentRoot("Class Details", PrincipalDashboardController.this.getScene().getRoot()));

            } else if (type.equals("Daily Class Details")) {
                st.getScene().setRoot(RootFactory.getSelectDepartmentRoot("Daily Class Details", PrincipalDashboardController.this.getScene().getRoot()));

            } else if (type.equals("verifyhod")) {
                st.getScene().setRoot(RootFactory.getSelectDepartmentRoot("verifyhod", PrincipalDashboardController.this.getScene().getRoot()));

            }

            st.show();
        }
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
        st.setScene(new Scene(RootFactory.getSecurityQuestionRoot(RootFactory.getPrincipalDashboardRoot(), "New")));
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
