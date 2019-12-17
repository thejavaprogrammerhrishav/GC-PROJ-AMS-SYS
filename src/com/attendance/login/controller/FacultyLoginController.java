/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.controller;

import com.attendance.login.actions.LoginAuthenticator;
import com.attendance.login.activity.dao.Activity;
import com.attendance.login.activity.model.LoginActivity;
import com.attendance.login.dao.Login;
import com.attendance.login.forgot.ForgotPasswordController;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.personal.model.PersonalDetails;
import com.attendance.util.Fxml;
import com.attendance.util.RootFactory;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author Programmer Hrishav
 */
public class FacultyLoginController extends AnchorPane {

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton login;

    @FXML
    private Label forgotpassword;

    @FXML
    private Label result;

    @FXML
    private Label department;

    @FXML
    private Label signup;
    
    @FXML
    private JFXButton close;

    private Thread thread;
    private FXMLLoader fxml;
    private Task<Void> blink;
    private final String ROLE = "Faculty";

    private Login faculty;
    private Activity loginActivity;
    private LoginAuthenticator authenticator;
    private User user;
    private LoginActivity activity;
    private PersonalDetails search;

    public FacultyLoginController() {
        fxml = Fxml.getFacultyLoginFXML();
        fxml.setRoot(this);
        fxml.setController(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(FacultyLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }

    @FXML
    private void initialize() {
        faculty = (Login) Start.app.getBean("userlogin");
        loginActivity = (Activity) Start.app.getBean("loginactivity");
        result.setText("");
        department.setText("Department:  " + SystemUtils.getDepartment());
        blink = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int count = 1;
                while (true) {
                    if (count == 0) {
                        count = 1;
                        forgotpassword.setStyle("-fx-text-fill: navy;");
                    } else {
                        count = 0;
                        forgotpassword.setStyle("-fx-text-fill: skyblue;");
                    }
                    Thread.sleep(400);
                }
            }
        };
        thread = new Thread(blink);
        thread.start();

        authenticator = new LoginAuthenticator() {
            @Override
            protected boolean authenticate(String username, String password) {
                user = faculty.findByUsernameDepartmentType(username, SystemUtils.getDepartment(), ROLE);
                boolean b = user.getStatus().equals("Accept");
                return username.equals(user.getUsername()) && password.equals(user.getPassword())&&b;
            }
        };
        authenticator.addLoginFailedListener(() -> Platform.runLater(() -> result.setText("Login Failed")));
        authenticator.addLoginSuccessListener(() -> Platform.runLater(() -> result.setText("Login Success")));

        login.setOnAction(evt -> {
            if (authenticator.authenticateUser(username.getText(), password.getText())) {
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                        for (int i = 3; i >= 0; i--) {
                            final int x = i;
                            Platform.runLater(() -> result.setText("Redirecting to Dashboard in " + x + " Sec"));
                            Thread.sleep(1000);
                        }
                        search=user.getDetails();
                        activity = new LoginActivity(search.getName(), user.getUsername(), "Faculty", "Active", DateTime.now().toString(DateTimeFormat.forPattern("dd-MM-yyyy")), DateTime.now().toString(DateTimeFormat.forPattern("hh:mm:ss a")), "",SystemUtils.getDepartment());
                        loginActivity.save(activity);
                        SystemUtils.setActivity(activity);
                        SystemUtils.setCurrentUser(user);
                        Platform.runLater(() -> SwitchRoot.switchRoot(Start.st, RootFactory.getFacultyDashboardRoot()));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FacultyLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }).start();
            } 
        });

        forgotpassword.setOnMouseClicked(e -> {
            ForgotPasswordController.LoginType = ROLE;
            SwitchRoot.switchRoot(Start.st, RootFactory.getForgotPasswordRoot());
        });
        signup.setOnMouseClicked(this::signupAction);
        close.setOnAction(this::closeAction);
    }

    private void signupAction(MouseEvent evt) {
        SwitchRoot.switchRoot(Start.st, RootFactory.getFacultySignupRoot(Start.st.getScene().getRoot()));
    }
    
    private void closeAction(ActionEvent evt) {
        SystemUtils.setDepartment("");
        SystemUtils.logout();
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType1Root());
    }

}
