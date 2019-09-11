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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
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

    private Thread thread;
    private FXMLLoader fxml;
    private Task<Void> blink;
    private final String ROLE="FACULTY";
    
    private final Login faculty;
    private final Activity loginActivity;
    private LoginAuthenticator authenticator;
    private User user;
    private LoginActivity activity;

    public FacultyLoginController() {
        fxml = Fxml.getFacultyLoginFXML();
        fxml.setRoot(this);
        fxml.setController(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(FacultyLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

        faculty = (Login) Start.app.getBean("userlogin");
        loginActivity = (Activity) Start.app.getBean("loginactivity");
    }

    @FXML
    private void initialize(){
        result.setText("");
        department.setText("Department:  "+SystemUtils.getDepartment());
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
        
        authenticator=new LoginAuthenticator() {
            @Override
            protected boolean authenticate(String username, String password) {
                user = faculty.findByUsernameAndType(username,ROLE);
                return username.equals(user.getUsername()) && password.equals(user.getPassword());
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
                        activity = new LoginActivity(user.getName(), user.getUsername(), "FACULTY", "ACTIVE", DateTime.now().toString(DateTimeFormat.forPattern("dd-MM-yyyy")), DateTime.now().toString(DateTimeFormat.forPattern("hh:mm:ss a")), "");
                        loginActivity.save(activity);
                        SystemUtils.setActivity(activity);
                        Platform.runLater(() -> SwitchRoot.switchRoot(Start.st, RootFactory.getFacultyDashboardRoot(user, activity)));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FacultyLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }).start();
            } else {
                JOptionPane.showMessageDialog(null, username + " " + password.getText());
            }
        });

        forgotpassword.setOnMouseClicked(e -> {
            ForgotPasswordController.LoginType = ROLE;
            SwitchRoot.switchRoot(Start.st, RootFactory.getForgotPasswordRoot());
        });
    }
    
}
