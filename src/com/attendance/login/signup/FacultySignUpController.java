/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.signup;

import com.attendance.faculty.dao.FacultyDao;
import com.attendance.faculty.model.Faculty;
import com.attendance.login.dao.Login;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.util.Fxml;
import com.attendance.util.RootFactory;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

/**
 *
 * @author Programmer Hrishav
 */
public class FacultySignUpController extends AnchorPane {

    @FXML
    private TextField fullname;

    @FXML
    private TextField email;

    @FXML
    private TextField contact;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmpassword;

    @FXML
    private JFXButton signup;

    @FXML
    private JFXButton abort;

    @FXML
    private JFXButton loginbutton;

    private FXMLLoader fxml;
    private Login login;
    private User user;
    private Parent parent;
    private Faculty faculty;
    private FacultyDao fdao;

    public FacultySignUpController(Parent parent) {
        this.parent = parent;
        fxml = Fxml.getFacultySignupFXML();
        fxml.setController(this);
        fxml.setRoot(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(FacultySignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }

        login = (Login) Start.app.getBean("userlogin");
        user = new User();
    }

    @FXML
    private void initialize() {
        fdao = (FacultyDao) Start.app.getBean("facultyregistration");
        faculty = new Faculty();
        loginbutton.setOnAction(this::loginAction);
        abort.setOnAction(e -> SwitchRoot.switchRoot(Start.st, parent));
        signup.setOnAction(e -> {
            if (login.isUsernameExists(username.getText()) > 0) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setHeaderText("Faculty Sign Up");
                al.setContentText("Username already taken");
                al.initOwner(((Node) e.getSource()).getScene().getWindow());
                al.initModality(Modality.WINDOW_MODAL);
                al.initStyle(StageStyle.UNDECORATED);
                al.show();
            } else if (password.getText().equals(confirmpassword.getText())) {
                user.setName(fullname.getText());
                user.setEmail(email.getText());
                user.setContact(contact.getText());
                user.setPassword(password.getText());
                user.setType("Faculty");
                user.setUsername(username.getText());

                faculty.setName(fullname.getText());
                faculty.setContact(contact.getText());
                faculty.setEmailId(email.getText());
                faculty.setGender("Unknown");
                faculty.setDepartment(SystemUtils.getDepartment());

                login.save(user);
                fdao.saveFaculty(faculty);

                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setHeaderText("Faculty Sign Up");
                al.setContentText("Sign Up Successful\nFaculty account created successfully with faculty details");
                al.initOwner(((Node) e.getSource()).getScene().getWindow());
                al.initModality(Modality.WINDOW_MODAL);
                al.initStyle(StageStyle.UNDECORATED);
                al.show();
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setHeaderText("Faculty Sign Up");
                al.setContentText("Passwords don't match\nCheck passwords again");
                al.initOwner(((Node) e.getSource()).getScene().getWindow());
                al.initModality(Modality.WINDOW_MODAL);
                al.initStyle(StageStyle.UNDECORATED);
                al.show();
            }
        });
    }

    private void loginAction(ActionEvent evt) {
        SystemUtils.logout();
        SwitchRoot.switchRoot(Start.st, RootFactory.getFacultyLoginRoot());
    }
}
