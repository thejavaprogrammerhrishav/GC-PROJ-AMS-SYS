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
import javafx.scene.control.Button;
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
public class HODSignupController extends AnchorPane {

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
    private Button signup;

    @FXML
    private Button loginbutton;

    private FXMLLoader fxml;
    private User user;
    private LoginService login;
    private ExceptionDialog dialog;
    private Parent parent;

    private PersonalDetails hod;
    private SecurityQuestion question;

    public HODSignupController(Parent parent) {
        this.parent = parent;
        fxml = Fxml.getHODSignUpFXML();
        fxml.setController(this);
        fxml.setRoot(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(HODSignupController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void initialize() {
        user = new User();
        login = (LoginService) Start.app.getBean("loginservice");
        login.setParent(this);
        dialog = login.getEx();

        hod = new PersonalDetails();
        question = new SecurityQuestion();
        loginbutton.setOnAction(this::loginaction);
        signup.setOnAction(e -> {
            if (login.isUsernameExists(username.getText())) {
                dialog.showError(this, "HOD Signup", "Username Already Taken");

            } else if (password.getText().equals(confirmpassword.getText())) {
                user.setPassword(password.getText());
                user.setType("HOD");
                user.setUsername(username.getText());
                user.setImage(SystemUtils.getDefaultAccountIcon());
                user.setDepartment(SystemUtils.getDepartment());
                user.setStatus("Pending");
                user.setDate(DateTime.now().toString(DateTimeFormat.forPattern("dd-MM-yyyy")));

                hod.setName(fullname.getText());
                hod.setContact(contact.getText());
                hod.setEmailId(email.getText());
                hod.setGender("Unknown");

                question.setQuestion1("");
                question.setQuestion2("");
                question.setQuestion3("");
                question.setAnswer1("");
                question.setAnswer2("");
                question.setAnswer3("");

                user.setDetails(hod);
                user.setSecurityquestion(question);

                Set<ConstraintViolation<User>> validate = ValidationUtils.getValidator().validate(user);
                if (validate.isEmpty()) {
                    int id = login.saveUser(user);

                    if (id > 0) {
                        dialog.showSuccess(this, "HOD Signup", "HOD Signup Successfully");
                    } else {
                        dialog.showError(this, "HOD Signup", "HOD Signup Failed ");
                    }
                } else {
                    validate.stream().forEach(c -> dialog.showWarning(this, "HOD Signup", c.getMessage()));

                }
            } else {
                dialog.showError(this, "HOD Signup", "Password Doesn't Match ");

            }
        });
    }

    private void loginaction(ActionEvent evt) {
        SystemUtils.logout();
        SwitchRoot.switchRoot(Start.st, RootFactory.getHODLoginRoot());
    }
}
