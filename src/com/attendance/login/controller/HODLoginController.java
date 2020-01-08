/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.attendance.login.actions.LoginAuthenticator;
import com.attendance.login.activity.dao.Activity;
import com.attendance.login.activity.model.LoginActivity;
import com.attendance.login.dao.Login;
import com.attendance.main.Start;
import com.attendance.login.user.model.User;
import com.attendance.personal.model.PersonalDetails;
import com.attendance.util.Fxml;
import com.attendance.util.RootFactory;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
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
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author Programmer Hrishav
 */
public class HODLoginController extends AnchorPane {

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

    @FXML
    private AnchorPane message;

    @FXML
    private Label bigmessage;

    @FXML
    private Label smallmessage;

    private FXMLLoader fxml;

    private final String userRole = "HOD";
    private Thread thread;
    private Task<Void> blink;

    private Login admin;
    private Activity loginActivity;
    private LoginAuthenticator authenticator;
    private User user;
    private LoginActivity activity;
    private PersonalDetails search;

    public HODLoginController() {
        fxml = Fxml.getHODLoginFXML();
        fxml.setRoot(HODLoginController.this);
        fxml.setController(HODLoginController.this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(HODLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void initialize() {
        admin = (Login) Start.app.getBean("userlogin");
        loginActivity = (Activity) Start.app.getBean("loginactivity");
        result.setText("");
        department.setText("Department: " + SystemUtils.getDepartment());
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
                user = admin.findByUsernameDepartmentType(username, SystemUtils.getDepartment(), userRole);
                setAccstatus(user.getStatus());
                boolean b = user.getStatus().equals("Accept");
                return username.equals(user.getUsername()) && password.equals(user.getPassword()) && b;
            }
        };
        authenticator.addLoginFailedListener(() -> Platform.runLater(() -> result.setText("Login Failed")));
        authenticator.addLoginSuccessListener(() -> Platform.runLater(() -> result.setText("Login Success")));
        authenticator.addLoginStatusListener(status -> {
            if (status.equals("Accept")) {
                Platform.runLater(() -> {
                    bigmessage.setText("Login Success");
                    smallmessage.setText("Your account is accepted");
                    message.getStyleClass().clear();
                    message.getStyleClass().addAll("alert", "alert-success");
                    bigmessage.getStyleClass().addAll("strong", "h4");
                    smallmessage.getStyleClass().add("h5");
                });
            } else if (status.equals("Pending")) {
                Platform.runLater(() -> {
                    bigmessage.setText("Request Pending");
                    smallmessage.setText("Your account is pending, please contact your principal");
                    message.getStyleClass().clear();
                    message.getStyleClass().addAll("alert", "alert-warning");
                    bigmessage.getStyleClass().addAll("strong", "h4");
                    smallmessage.getStyleClass().add("h5");
                });
            } else if (status.equals("OnHold")) {
                Platform.runLater(() -> {
                    bigmessage.setText("Request On - Hold");
                    smallmessage.setText("Your account is suspended, please contact your principal");
                    message.getStyleClass().clear();
                    message.getStyleClass().addAll("alert", "alert-warning");
                    bigmessage.getStyleClass().addAll("strong", "h4");
                    smallmessage.getStyleClass().add("h5");
                });

            } else if (status.equals("Decline")) {
                Platform.runLater(() -> {
                    bigmessage.setText("Login Failed");
                    smallmessage.setText("Your account is declined, please contact your principal");
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
                        activity = new LoginActivity(search.getName(), user.getUsername(), "HOD", "Active", DateTime.now().toString(DateTimeFormat.forPattern("dd-MM-yyyy")), DateTime.now().toString(DateTimeFormat.forPattern("hh:mm:ss a")), "", SystemUtils.getDepartment());
                        loginActivity.save(activity);
                        SystemUtils.setActivity(activity);
                        for (int i = 3; i >= 0; i--) {
                            final int x = i;
                            Platform.runLater(() -> result.setText("Redirecting to Dashboard in " + x + " Sec"));
                            Thread.sleep(1000);
                        }
                        SystemUtils.setCurrentUser(user);
                        Platform.runLater(() -> SwitchRoot.switchRoot(Start.st, RootFactory.getHODDashboardRoot()));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(HODLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }).start();
            }
        });

        forgotpassword.setOnMouseClicked(e -> {
            User user = new User();
            user.setType("HOD");
            SystemUtils.setCurrentUser(user);
            SwitchRoot.switchRoot(Start.st, RootFactory.getResetPassword1Root(Start.st.getScene().getRoot()));
        });
        signup.setOnMouseClicked(this::signupAction);
        close.setOnAction(this::closeAction);
    }

    private void signupAction(MouseEvent evt) {
        SwitchRoot.switchRoot(Start.st, RootFactory.getHODSignupRoot(Start.st.getScene().getRoot()));
    }

    private void closeAction(ActionEvent evt) {
        SystemUtils.setDepartment("");
        SystemUtils.logout();
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType1Root());
    }
}
