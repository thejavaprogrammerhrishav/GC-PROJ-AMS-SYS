/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.signup;

import com.attendance.login.dao.Login;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.personal.model.PersonalDetails;
import com.attendance.util.Fxml;
import com.attendance.util.RootFactory;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import java.io.IOException;
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
    private Login login;
    private Parent parent;

    private PersonalDetails hod;

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

        user = new User();
        login = (Login) Start.app.getBean("userlogin");
    }

    @FXML
    private void initialize() {
        hod = new PersonalDetails();
        loginbutton.setOnAction(this::loginaction);
        signup.setOnAction(e -> {
            if (login.isUsernameExists(username.getText()) > 0) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setHeaderText("Administrator Sign Up");
                al.setContentText("Username already taken");
                al.initOwner(((Node) e.getSource()).getScene().getWindow());
                al.initModality(Modality.WINDOW_MODAL);
                al.initStyle(StageStyle.UNDECORATED);
                al.show();
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


                user.setDetails(hod);
                int id = login.save(user);
                
                if(id>0) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setHeaderText("HOD Sign Up");
                al.setContentText("Sign Up Successful\nHOD account created successfully\nCurrent Account Status :"+user.getStatus());
                al.initOwner(((Node) e.getSource()).getScene().getWindow());
                al.initModality(Modality.WINDOW_MODAL);
                al.initStyle(StageStyle.UNDECORATED);
                al.show();}
                else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                al.setHeaderText("HOD Sign Up");
                al.setContentText("HOD SignUp Failed");
                al.initOwner(((Node) e.getSource()).getScene().getWindow());
                al.initModality(Modality.WINDOW_MODAL);
                al.initStyle(StageStyle.UNDECORATED);
                al.show();
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setHeaderText("HOD Sign Up");
                al.setContentText("Passwords don't match\nCheck passwords again");
                al.initOwner(((Node) e.getSource()).getScene().getWindow());
                al.initModality(Modality.WINDOW_MODAL);
                al.initStyle(StageStyle.UNDECORATED);
                al.show();
            }
        });
    }

    private void loginaction(ActionEvent evt) {
        SystemUtils.logout();
        SwitchRoot.switchRoot(Start.st, RootFactory.getHODLoginRoot());
    }
}
