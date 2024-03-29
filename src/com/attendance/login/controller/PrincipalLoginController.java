/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.controller;

import com.attendance.login.actions.LoginAuthenticator;
import com.attendance.login.activity.dao.Activity;
import com.attendance.login.activity.model.LoginActivity;
import com.attendance.login.activity.service.LoginActivityService;
import com.attendance.login.dao.Login;
import com.attendance.login.service.LoginService;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.personal.model.PersonalDetails;
import com.attendance.util.ExceptionDialog;
import com.attendance.util.Fxml;
import com.attendance.util.RootFactory;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.Base64;
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

    @FXML
    private AnchorPane message;

    @FXML
    private Label bigmessage;

    @FXML
    private Label smallmessage;

    private FXMLLoader fxml;
    private final String userRole = "Principal";
    private Thread thread;
    private Task<Void> blink;

    private LoginService principal;
    private LoginActivityService loginActivity;
    private ExceptionDialog dialog;
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
        principal = (LoginService) Start.app.getBean("loginservice");
        principal.setParent(this);
        dialog = principal.getEx();
        
        loginActivity = (LoginActivityService) Start.app.getBean("loginactivityservice");
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

        Platform.runLater(() -> {
            bigmessage.setText("");
            smallmessage.setText("");
            message.getStyleClass().clear();
            bigmessage.getStyleClass().addAll("strong", "h4");
            smallmessage.getStyleClass().add("h5");
        });

        authenticator = new LoginAuthenticator() {
            @Override
            protected boolean authenticate(String username, String password) {
                user = principal.findByUsernameDepartmentType(username, "N/A", userRole);
                setAccstatus(user.getStatus());
                boolean b = user.getStatus().equals("Accept");
                return username.equals(user.getUsername()) && Base64.getEncoder().encodeToString(password.getBytes()).equals(user.getPassword()) && b;
            }
        };

        authenticator.addLoginFailedListener(() -> Platform.runLater(() -> result.setText("Login Failed")));
        authenticator.addLoginSuccessListener(() -> Platform.runLater(() -> result.setText("Login Success")));
        authenticator.addLoginStatusListener(status -> {
            if (status.equals("Accept")) {
                Platform.runLater(() -> {
                    bigmessage.setText("Account Status");
                    smallmessage.setText("Your account is accepted");
                    message.getStyleClass().clear();
                    message.getStyleClass().addAll("alert", "alert-success");
                    bigmessage.getStyleClass().addAll("strong", "h4");
                    smallmessage.getStyleClass().add("h5");
                });
            } else if (status.equals("Pending")) {
                Platform.runLater(() -> {
                    bigmessage.setText("Request Pending");
                    smallmessage.setText("Your account is pending, please contact your Server Administrator or Existing Principal");
                    message.getStyleClass().clear();
                    message.getStyleClass().addAll("alert", "alert-warning");
                    bigmessage.getStyleClass().addAll("strong", "h4");
                    smallmessage.getStyleClass().add("h5");
                });
            } else if (status.equals("OnHold")) {
                Platform.runLater(() -> {
                    bigmessage.setText("Request On - Hold");
                    smallmessage.setText("Your account is suspended, please contact your Server Administrator or Existing Principal");
                    message.getStyleClass().clear();
                    message.getStyleClass().addAll("alert", "alert-warning");
                    bigmessage.getStyleClass().addAll("strong", "h4");
                    smallmessage.getStyleClass().add("h5");
                });

            } else if (status.equals("Decline")) {
                Platform.runLater(() -> {
                    bigmessage.setText("Account Status");
                    smallmessage.setText("Your account is declined, please contact your Server Administrator or Existing Principal");
                    message.getStyleClass().clear();
                    message.getStyleClass().addAll("alert", "alert-danger");
                    bigmessage.getStyleClass().addAll("strong", "h4");
                    smallmessage.getStyleClass().add("h5");
                });
            } else {
                Platform.runLater(() -> {
                    bigmessage.setText("");
                    smallmessage.setText("");
                    message.getStyleClass().clear();
                    bigmessage.getStyleClass().addAll("strong", "h4");
                    smallmessage.getStyleClass().add("h5");
                });
            }
        });

        login.setOnAction(evt -> {
            if (authenticator.authenticateUser(username.getText(), password.getText())) {
                new Thread(() -> {
                    try {
                        Thread.sleep(500);
                        search = user.getDetails();
                        activity = new LoginActivity(search.getName(), user.getUsername(), "Principal", "ACTIVE", DateTime.now().toString(DateTimeFormat.forPattern("dd-MM-yyyy")), DateTime.now().toString(DateTimeFormat.forPattern("hh:mm:ss a")), "", "N/A");
                        loginActivity.saveactivity(activity);
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
            SystemUtils.setDepartment("N/A");
            User user = new User();
            user.setType("Principal");
            SystemUtils.setCurrentUser(user);
            SwitchRoot.switchRoot(Start.st, RootFactory.getResetPassword1Root(Start.st.getScene().getRoot()));
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
