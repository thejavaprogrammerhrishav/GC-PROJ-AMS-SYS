/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.dashboard;

import com.attendance.login.activity.dao.Activity;
import com.attendance.login.activity.model.LoginActivity;
import com.attendance.login.dao.Login;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.student.dao.StudentDao;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private AnchorPane pane;

    @FXML
    private JFXButton verifyhod;

    @FXML
    private Label pending;

    @FXML
    private ImageView profilepic;

    private User principal;
    private LoginActivity activity;
    private FXMLLoader fxml;
    private Activity act;
    private Login login;

    private Task<Integer> task;
    private Thread thread;

    private StudentDao dao;

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
        act = (Activity) Start.app.getBean("loginactivity");
        login = (Login) Start.app.getBean("userlogin");
        dao = (StudentDao) Start.app.getBean("studentregistration");
        department.getItems().setAll(Arrays.asList(SystemUtils.getDepartments()));

        List<String> years = new ArrayList<>(dao.get("select distinct(year) from student order by year", String.class));
        Collections.sort(years);
        year.getItems().setAll(years);

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

        open.setOnAction(this::routine);
    }

    private void routine(ActionEvent evt) {
        String dept = department.getSelectionModel().getSelectedItem();
        String yr = year.getSelectionModel().getSelectedItem();
        System.out.println(dept + "\t" + yr);
        if (dept != null && yr != null) {
            SwitchRoot.switchRoot(Start.st, RootFactory.getPrincipalRoutineDashboardRoot(Start.st.getScene().getRoot(), dept, yr));
        }
    }

    private void init() {
        List<User> collected = login.findByStatus("Pending");
        collected = collected.stream().filter(p -> p.getType().equals("HOD")).collect(Collectors.toList());
        pending.setText("" + collected.size());
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

}
