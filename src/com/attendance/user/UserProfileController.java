/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.user;

import com.attendance.login.dao.Login;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.personal.dao.PersonalDetailsDao;
import com.attendance.personal.model.PersonalDetails;
import com.attendance.util.Fxml;
import com.attendance.util.RootFactory;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.io.ByteArrayInputStream;
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
    private PersonalDetails details;
    private Login login;
    private PersonalDetailsDao dao;
    private String parent;

    private File file;
    private FileInputStream fin;
    private FileChooser chooser;
    private ExtensionFilter filter;

    private boolean imageChanged = false;

    public UserProfileController(String parent) {
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
        dao = (PersonalDetailsDao) Start.app.getBean("personal");
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
        details = dao.findById(user.getPersonalid());
        name.setText(details.getName());
        contact.setText(details.getContact());
        email.setText(details.getEmailId());
        username.setText(user.getUsername());
        department.setText(user.getDepartment());
        switch (details.getGender()) {
            case "Male":
                male.setSelected(true);
                break;
            case "Female":
                female.setSelected(true);
                break;
        }
        userrole.setText(user.getType());
        profilepic.setImage(new Image(new ByteArrayInputStream(user.getImage())));
    }

    private void buttonActions() {
        switch (parent) {
            case "principal":
                close.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getPrincipalDashboardRoot()));
                break;
            case "hod":
                close.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getHODDashboardRoot()));
                break;
            case "faculty":
                close.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getFacultyDashboardRoot()));
                break;
        }
        changepassword.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getChangePasswordRoot(Start.st.getScene().getRoot())));
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

        try {
            file = chooser.showOpenDialog(this.getScene().getWindow());
            fin = new FileInputStream(file);
            profilepic.setImage(new Image(fin));
            imageChanged = true;
        } catch (NullPointerException ex) {
            imageChanged = false;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Image Changed: " + imageChanged);
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
        PersonalDetails updatedetails = new PersonalDetails();

        updateuser.setUsername(username.getText());
        updateuser.setPassword(user.getPassword());
        updateuser.setId(user.getId());
        updateuser.setType(user.getType());
        updateuser.setDepartment(user.getDepartment());
        updateuser.setPersonalid(details.getId());

        if (imageChanged) {
            updateuser.setImage(SystemUtils.getByteArrayFromImage(profilepic.getImage()));
        } else {
            updateuser.setImage(SystemUtils.getDefaultAccountIcon());
        }

        updatedetails.setId(details.getId());
        updatedetails.setName(name.getText());
        updatedetails.setEmailId(email.getText());
        updatedetails.setContact(contact.getText());
        if (male.isSelected()) {
            updatedetails.setGender("Male");
        } else if (female.isSelected()) {
            updatedetails.setGender("Female");
        } else {
            updatedetails.setGender("Unknown");
        }
        boolean b1 = login.update(updateuser);
        boolean b2 = dao.update(updatedetails);
        if (b1 && b2) {
            result.setText("Profile Updated Successfully");
            SystemUtils.setCurrentUser(updateuser);
        } else {
            result.setText("Profile Updation Failed");
        }
    }

}
