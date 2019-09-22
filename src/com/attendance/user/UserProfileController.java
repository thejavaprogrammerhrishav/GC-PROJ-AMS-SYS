/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.user;

import com.attendance.faculty.dao.FacultyDao;
import com.attendance.faculty.model.Faculty;
import com.attendance.login.dao.Login;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.user.principal.dao.PrincipalDao;
import com.attendance.user.principal.model.Principal;
import com.attendance.util.Fxml;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author pc
 */
public class UserProfileController extends AnchorPane {

    @FXML
    private ImageView profilepic;

    @FXML
    private Button profilepicture;

    @FXML
    private Label result12;

    @FXML
    private Label userrole;

    @FXML
    private Label result121;

    @FXML
    private Label department;

    @FXML
    private Label result;

    @FXML
    private JFXButton close;

    @FXML
    private TextField name;

    @FXML
    private JFXButton nameedit;

    @FXML
    private JFXCheckBox male;

    @FXML
    private JFXCheckBox female;

    @FXML
    private Button updateprofile;

    @FXML
    private TextField contact;

    @FXML
    private JFXButton contactedit;

    @FXML
    private TextField email;

    @FXML
    private JFXButton emailedit;

    @FXML
    private Label result1;

    @FXML
    private Label result11;

    @FXML
    private Label result14;

    @FXML
    private TextField username;

    @FXML
    private JFXButton usernameedit;

    @FXML
    private Button changepassword;

    private FXMLLoader fxml;

    private User user;
    private Faculty faculty;
    private Login login;
    private FacultyDao dao;
    private Principal principal;
    private PrincipalDao pdao;
    private Parent parent;

    private File file;
    private FileInputStream fin;
    private FileChooser chooser;
    private ExtensionFilter filter;

    public UserProfileController(Parent parent) {
        this.user = SystemUtils.getCurrentUser();
        this.parent = parent;
        fxml = Fxml.getProfileFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void initialize() {
        login = (Login) Start.app.getBean("userlogin");
        dao = (FacultyDao) Start.app.getBean("facultyregistration");
        pdao = (PrincipalDao) Start.app.getBean("principallogin");
        loadDetails();
        init();
        initGender();
        buttonActions();
    }

    private void init() {
        name.setEditable(false);
        email.setEditable(false);
        username.setEditable(false);
        contact.setEditable(false);
        result.setText("");
    }

    private void loadDetails() {
        if (user.getType().equals("Principal")) {
            principal = pdao.findById(user.getContact());
            name.setText(principal.getName());
            contact.setText(principal.getContact());
            email.setText(principal.getEmailId());
            username.setText(user.getUsername());
            department.setText("N/A");
            switch (principal.getGender()) {
                case "Male":
                    male.setSelected(true);
                    break;
                case "Female":
                    female.setSelected(true);
                    break;
            }
        } else {
            faculty = dao.findById(user.getContact());
            name.setText(faculty.getName());
            contact.setText(faculty.getContact());
            email.setText(faculty.getEmailId());
            username.setText(user.getUsername());
            department.setText(faculty.getDepartment());
             switch (faculty.getGender()) {
                case "Male":
                    male.setSelected(true);
                    break;
                case "Female":
                    female.setSelected(true);
                    break;
            }
        }
        userrole.setText(user.getType());
    }

    private void buttonActions() {
        close.setOnAction(e -> SwitchRoot.switchRoot(Start.st, parent));
        nameedit.setOnAction(e -> name.setEditable(true));
        contactedit.setOnAction(e -> contact.setEditable(true));
        emailedit.setOnAction(e -> email.setEditable(true));
        usernameedit.setOnAction(e -> username.setEditable(true));
        profilepicture.setOnAction(this::loadImage);
        updateprofile.setOnAction(this::update);
    }

    private void loadImage(ActionEvent evt) {
        filter = new ExtensionFilter("Image Files", Arrays.asList("*.jpg", "*.png"));
        chooser = new FileChooser();
        chooser.getExtensionFilters().add(filter);
        chooser.setSelectedExtensionFilter(filter);
        chooser.setTitle("Select your profile picture");
        file = chooser.showOpenDialog(this.getScene().getWindow());
        try {
            fin = new FileInputStream(file);
            profilepic.setImage(new Image(fin));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initGender() {
        male.selectedProperty().addListener((ol, o, n) -> {
            if (n) {
                male.setSelected(true);
                female.setSelected(false);
            }
        });

        female.selectedProperty().addListener((ol, o, n) -> {
            if (n) {
                male.setSelected(false);
                female.setSelected(true);
            }
        });
    }

    private void update(ActionEvent evt) {
        User updateuser = new User();
        Faculty updatefaculty = new Faculty();
        Principal updateprincipal = new Principal();

        updateuser.setContact(contact.getText());
        updateuser.setUsername(username.getText());
        updateuser.setPassword(user.getPassword());
        updateuser.setId(user.getId());
        updateuser.setType(user.getType());

        if (user.getType().equals("Principal")) {
            updateprincipal.setName(name.getText());
            updateprincipal.setEmailId(email.getText());
            updateprincipal.setContact(principal.getContact());
            if (male.isSelected()) {
                updateprincipal.setGender("Male");
            } else if (female.isSelected()) {
                updateprincipal.setGender("Female");
            } else {
                updateprincipal.setGender("Unknown");
            }
            boolean b1 = login.update(updateuser);
            boolean b2 = pdao.updatePrincipal(updateprincipal);
            boolean b3 = pdao.updatePrincipalId(contact.getText(), principal.getContact());
            if (b1 && b2 && b3) {
                result.setText("Profile Updated Successfully");
                SystemUtils.setCurrentUser(updateuser);
            } else {
                result.setText("Profile Updation Failed");
            }
        } else {
            updatefaculty.setName(name.getText());
            updatefaculty.setEmailId(email.getText());
            updatefaculty.setContact(faculty.getContact());
            updatefaculty.setDepartment(department.getText());
            if (male.isSelected()) {
                updatefaculty.setGender("Male");
            } else if (female.isSelected()) {
                updatefaculty.setGender("Female");
            } else {
                updatefaculty.setGender("Unknown");
            }
            boolean b1 = login.update(updateuser);
            boolean b2 = dao.updateFaculty(updatefaculty);
            boolean b3 = dao.updateFacultyId(contact.getText(), faculty.getContact());
            if (b1 && b2 && b3) {
                result.setText("Profile Updated Successfully");
                SystemUtils.setCurrentUser(updateuser);
            } else {
                result.setText("Profile Updation Failed");
            }
        }

    }
}
