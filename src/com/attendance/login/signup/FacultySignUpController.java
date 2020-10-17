/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.signup;

import com.attendance.login.dao.Login;
import com.attendance.login.service.LoginService;
import com.attendance.login.user.model.SecurityQuestion;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.personal.model.PersonalDetails;
import com.attendance.util.ExceptionDialog;
import com.attendance.util.Fxml;
import com.attendance.util.RootFactory;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.attendance.util.ValidationUtils;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.Set;
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
import javax.validation.ConstraintViolation;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

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
    private JFXButton loginbutton;

    private FXMLLoader fxml;
    private LoginService login;
    private User user;
    private Parent parent;
    private PersonalDetails faculty;
    private SecurityQuestion question;
    private ExceptionDialog dialog;

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

        login = (LoginService) Start.app.getBean("loginservice");
        login.setParent(this);
        dialog = login.getEx();
        user = new User();
    }

    @FXML
    private void initialize() {
        faculty = new PersonalDetails();
        question = new SecurityQuestion();
        loginbutton.setOnAction(this::loginAction);
        signup.setOnAction(e -> {
            if (login.isUsernameExists(username.getText())) {
                dialog.showError(this, "Faculty Signup", "Username Already Taken ");

            } else if (password.getText().equals(confirmpassword.getText())) {
                user.setPassword(password.getText());
                user.setType("Faculty");
                user.setUsername(username.getText());
                user.setImage(SystemUtils.getDefaultAccountIcon());
                user.setDepartment(SystemUtils.getDepartment());
                user.setStatus("Pending");
                user.setDate(DateTime.now().toString(DateTimeFormat.forPattern("dd-MM-yyyy")));

                faculty.setName(fullname.getText());
                faculty.setContact(contact.getText());
                faculty.setEmailId(email.getText());
                faculty.setGender("Unknown");

                question.setQuestion1("Unknown");
                question.setQuestion2("Unknown");
                question.setQuestion3("Unknown");
                question.setAnswer1("Unknown");
                question.setAnswer2("Unknown");
                question.setAnswer3("Unknown");

                user.setDetails(faculty);
                user.setSecurityquestion(question);

                Set<ConstraintViolation<User>> validate = ValidationUtils.getValidator().validate(user);
                if (validate.isEmpty()) {
                    int id = login.saveUser(user);

                    if (id > 0) {
                        dialog.showSuccess(this, "Faculty Signup", "Faculty Signup Successfully ");

                    } else {
                        dialog.showError(this, "Faculty Signup", "Faculty Signup Failed ");

                    }
                } else {
                    validate.stream().forEach(c -> dialog.showWarning(this, "Faculty Signup", c.getMessage()));

                }
            } else {
                dialog.showError(this, "Faculty Signup", "Password Doesn't Match ");

            }
        });
    }

    private void loginAction(ActionEvent evt) {
        SystemUtils.logout();
        SwitchRoot.switchRoot(Start.st, RootFactory.getFacultyLoginRoot());
    }
}
