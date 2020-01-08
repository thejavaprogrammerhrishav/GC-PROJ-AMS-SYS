/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.signup;

import com.attendance.login.dao.Login;
import com.attendance.login.user.model.SecurityQuestion;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.personal.model.PersonalDetails;
import com.attendance.util.Fxml;
import com.attendance.util.Message;
import com.attendance.util.MessageUtil;
import com.attendance.util.RootFactory;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.security.Principal;
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
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author pc
 */
public class PrincipalSignUpController extends AnchorPane {

    @FXML
    private TextField fullname;

    @FXML
    private TextField contact;

    @FXML
    private TextField email;

    @FXML
    private TextField username;

    @FXML
    private JFXButton signup;

    @FXML
    private JFXButton loginbutton;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmpassword;

    private FXMLLoader fxml;
    private User user;
    private Login login;
    private Parent parent;

    private PersonalDetails principal;
    private SecurityQuestion question;

    public PrincipalSignUpController(Parent parent) {
        this.parent = parent;
        fxml = Fxml.getPrincipalSignUpFXML();
        fxml.setController(this);
        fxml.setRoot(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalSignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        user = new User();
        principal = new PersonalDetails();
        question=new SecurityQuestion();
        login = (Login) Start.app.getBean("userlogin");
        signup.setOnAction(E -> {
            if (login.isUsernameExists(username.getText()) > 0) {
                MessageUtil.showError(Message.ERROR, "Principal SignUp", "Username already taken", ((Node) E.getSource()).getScene().getWindow());
            } else if (password.getText().equals(confirmpassword.getText())) {
                user.setType("Principal");
                user.setUsername(username.getText());
                user.setPassword(password.getText());
                user.setImage(SystemUtils.getDefaultAccountIcon());
                user.setDepartment("N/A");
                user.setStatus("Pending");
                user.setDate(DateTime.now().toString(DateTimeFormat.forPattern("dd-MM-yyyy")));

                principal.setName(fullname.getText());
                principal.setContact(contact.getText());
                principal.setEmailId(email.getText());
                principal.setGender("Unknown");
                
                question.setQuestion1("");
                question.setQuestion2("");
                question.setQuestion3("");
                question.setAnswer1("");
                question.setAnswer2("");
                question.setAnswer3("");

                user.setDetails(principal);
                user.setSecurityquestion(question);
                
                int id = login.save(user);

                if (id > 0) {
                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                    al.setHeaderText("HOD Sign Up");
                    al.setContentText("Sign Up Successful\nPrincipal account created successfully\nCurrent Account Status :" + user.getStatus());
                    al.initOwner(((Node) E.getSource()).getScene().getWindow());
                    al.initModality(Modality.WINDOW_MODAL);
                    al.initStyle(StageStyle.UNDECORATED);
                    al.show();
                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setHeaderText("HOD Sign Up");
                    al.setContentText("Principal SignUp failed");
                    al.initOwner(((Node) E.getSource()).getScene().getWindow());
                    al.initModality(Modality.WINDOW_MODAL);
                    al.initStyle(StageStyle.UNDECORATED);
                    al.show();
                }

            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setHeaderText("HOD Sign Up");
                al.setContentText("Password doesn't match");
                al.initOwner(((Node) E.getSource()).getScene().getWindow());
                al.initModality(Modality.WINDOW_MODAL);
                al.initStyle(StageStyle.UNDECORATED);
                al.show();
            }
        });
        loginbutton.setOnAction(this::LoginAction);
    }

    private void LoginAction(ActionEvent evt) {
        SystemUtils.logout();
        SwitchRoot.switchRoot(Start.st, RootFactory.getPrincipalLoginRoot());
    }

}
