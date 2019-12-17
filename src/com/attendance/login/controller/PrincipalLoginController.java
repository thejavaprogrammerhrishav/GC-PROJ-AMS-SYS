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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author pc
 */
public class PrincipalLoginController extends AnchorPane {

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private JFXButton login;

    @FXML
    private Label forgotpassword;

    @FXML
    private Label result;

    @FXML
    private Label signup;

    @FXML
    private JFXButton close;

    private FXMLLoader fxml;
    private final String userRole = "Principal";
    private Thread thread;
    private Task<Void> blink;

    private Login principal;
    private Activity loginActivity;
    private LoginAuthenticator authenticator;
    private User user;
    private LoginActivity activity;
    private PersonalDetails search;

    public PrincipalLoginController() {
        fxml = Fxml.getPrincipalLoginFXML();
        fxml.setRoot(this);
        fxml.setController(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        principal = (Login) Start.app.getBean("userlogin");
        loginActivity = (Activity) Start.app.getBean("loginactivity");
        result.setText("");
        blink = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int count = 1;
                while (true) {
                    if (count == 0) {
                        count = 1;
                        forgotpassword.setStyle("-fx-text-fill:navy");
                    } else {
                        count = 0;
                        forgotpassword.setStyle("-fx-text-fill:skyblue");
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
                user = principal.findByUsernameDepartmentType(username, "N/A", userRole);
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
                        Thread.sleep(500);
                        search = user.getDetails();
                        activity = new LoginActivity(search.getName(), user.getUsername(), "Principal", "ACTIVE", DateTime.now().toString(DateTimeFormat.forPattern("dd-MM-yyyy")), DateTime.now().toString(DateTimeFormat.forPattern("hh:mm:ss a")), "", "N/A");
                        loginActivity.save(activity);
                        SystemUtils.setActivity(activity);
                        for (int i = 3; i >= 0; i--) {
                            final int x = i;
                            Platform.runLater(() -> result.setText("Redirecting to Dashboard in " + x + " Sec"));
                            Thread.sleep(1000);
                        }
                        SystemUtils.setCurrentUser(user);
                        Platform.runLater(() -> SwitchRoot.switchRoot(Start.st, RootFactory.getPrincipalDashboardRoot()));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PrincipalLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }).start();
            } 
        });

        forgotpassword.setOnMouseClicked(e -> {
            ForgotPasswordController.LoginType = userRole;
            SwitchRoot.switchRoot(Start.st, RootFactory.getForgotPasswordRoot());
        });

        signup.setOnMouseClicked(this::signupAction);
        close.setOnAction(this::closeAction);
    }

    private void signupAction(MouseEvent evt) {
        SwitchRoot.switchRoot(Start.st, RootFactory.getPrincipalSignUpRoot(Start.st.getScene().getRoot()));
    }

    private void closeAction(ActionEvent evt) {
        SystemUtils.setDepartment("");
        SystemUtils.logout();
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType1Root());
    }
}
