/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.user;

import com.attendance.login.service.LoginService;
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
import com.jfoenix.controls.JFXCheckBox;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.validation.ConstraintViolation;

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
    private LoginService login;
    private String parent;
    
    private File file;
    private FileInputStream fin;
    private FileChooser chooser;
    private ExtensionFilter filter;
    private ExceptionDialog dialog;
    
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
        login = (LoginService) Start.app.getBean("loginservice");
        login.setParent(this);
        dialog = login.getEx();
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
        details = user.getDetails();
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
        updateuser.setStatus(user.getStatus());
        updateuser.setDate(user.getDate());
        
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
        updateuser.setDetails(updatedetails);
        updateuser.setSecurityquestion(user.getSecurityquestion());
        
        Set<ConstraintViolation<User>> validate = ValidationUtils.getValidator().validate(updateuser);
        if (validate.isEmpty()) {
            boolean b1 = login.updateUser(updateuser);
            if (b1) {
                result.setText("Profile Updated Successfully");
                SystemUtils.setCurrentUser(updateuser);
            } else {
                result.setText("Profile Updation Failed");
            }
        } else {
            validate.stream().forEach(c -> dialog.showError(this, "Update User Profile", c.getMessage()));
        }
    }
}
