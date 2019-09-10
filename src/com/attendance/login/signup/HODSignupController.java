/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.signup;

import com.attendance.login.dao.Login;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.util.Fxml;
import com.attendance.util.RootFactory;
import com.attendance.util.SwitchRoot;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

/**
 *
 * @author Programmer Hrishav
 */
public class HODSignupController extends AnchorPane{
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
    private Button abort;
    
    @FXML 
    private Button loginbutton;
    
    private FXMLLoader fxml;
    private User user;
    private Login login;

    public HODSignupController() {
        fxml=Fxml.getHodSignUpFXML();
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
    private void initialize(){
        abort.setOnAction(e->((Node)e.getSource()).getScene().getWindow().hide());
        signup.setOnAction(e->{
            if(login.isUsernameExists(username.getText())>0){
                Alert al=new Alert(Alert.AlertType.ERROR);
                al.setHeaderText("Administrator Sign Up");
                al.setContentText("Username already taken");
                al.initOwner(((Node)e.getSource()).getScene().getWindow());
                al.initModality(Modality.WINDOW_MODAL);
                al.initStyle(StageStyle.UNDECORATED);
                al.show();
            }
            else if(password.getText().equals(confirmpassword.getText())){
                user.setName(fullname.getText());
                user.setEmail(email.getText());
                user.setContact(contact.getText());
                user.setPassword(password.getText());
                user.setType("Admin");
                user.setUsername(username.getText());
                
                login.save(user);
                
                Alert al=new Alert(Alert.AlertType.INFORMATION);
                al.setHeaderText("Administrator Sign Up");
                al.setContentText("Sign Up Successful\nAdministrator account created successfully");
                al.initOwner(((Node)e.getSource()).getScene().getWindow());
                al.initModality(Modality.WINDOW_MODAL);
                al.initStyle(StageStyle.UNDECORATED);
                al.show();
            }
            else{
                Alert al=new Alert(Alert.AlertType.ERROR);
                al.setHeaderText("Administrator Sign Up");
                al.setContentText("Passwords don't match\nCheck passwords again");
                al.initOwner(((Node)e.getSource()).getScene().getWindow());
                al.initModality(Modality.WINDOW_MODAL);
                al.initStyle(StageStyle.UNDECORATED);
                al.show();
            }
        });
    }
    
    private void loginaction(ActionEvent evt){
        SwitchRoot.switchRoot(Start.st, RootFactory.getHODLoginRoot());
    }
}
