/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.routines.controller;

import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.personal.dao.PersonalDetailsDao;
import com.attendance.personal.model.PersonalDetails;
import com.attendance.routines.dao.RoutineDao;
import com.attendance.routines.model.Routine;
import com.attendance.util.Fxml;
import com.attendance.util.Message;
import com.attendance.util.MessageUtil;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import com.sun.xml.internal.ws.handler.MessageUpdatableContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author pc
 */
public class AddNewRoutineController extends AnchorPane {

    @FXML
    private TextField path;

    @FXML
    private JFXButton browse;

    @FXML
    private JFXButton upload;

    @FXML
    private Label name;

    @FXML
    private Label usertype;

    @FXML
    private Label department;

    @FXML
    private Label date;

    @FXML
    private ImageView image;

    private FXMLLoader fxml;
    private Routine routine;
    private RoutineDao dao;
    private PersonalDetailsDao pdao;

    private File file;

    public AddNewRoutineController() {
        fxml = Fxml.getAddNewRoutineFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(AddNewRoutineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void initialize() {
        dao = (RoutineDao) Start.app.getBean("routine");
        pdao = (PersonalDetailsDao) Start.app.getBean("personal");
        User currentUser = SystemUtils.getCurrentUser();
        PersonalDetails personalDetails = pdao.findById(currentUser.getPersonalid());

        date.setText(DateTime.now().toString(DateTimeFormat.forPattern("dd-MM-yyyy")));
        name.setText(personalDetails.getName());
        department.setText(currentUser.getDepartment());
        usertype.setText(currentUser.getType());

        browse.setOnAction(this::browse);
        upload.setOnAction(this::uploadRoutine);
    }

    private void browse(ActionEvent evt) {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter images = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif");

        fc.getExtensionFilters().addAll(images);
        fc.setTitle("Select Routine File");

        file = fc.showOpenDialog(this.getScene().getWindow());

        if (file != null) {
            try {
                path.setText(file.getAbsolutePath());
                image.setImage(new Image(new FileInputStream(file.getAbsolutePath())));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AddNewRoutineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void uploadRoutine(ActionEvent evt) {
        if (file != null) {
            FileInputStream fin = null;
            try {
                routine = new Routine();
                routine.setName(name.getText());
                routine.setDate(date.getText());
                routine.setDepartment(department.getText());
                routine.setUsertype(usertype.getText());
                fin = new FileInputStream(file.getAbsolutePath());
                byte[] data = new byte[(int) file.length()];
                fin.read(data);
                fin.close();
                routine.setImage(data);
                Integer save = dao.save(routine);
                if (save > 0) {
                    MessageUtil.showInformation(Message.INFORMATION, "Add New Routine ", "Routine Added Successfully ", this.getScene().getWindow());
                } else {
                    MessageUtil.showError(Message.ERROR, "Add New Routine ", "Routine Addition Failed ", this.getScene().getWindow());
                }
            } catch (IOException ex) {
                Logger.getLogger(AddNewRoutineController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fin.close();
                } catch (IOException ex) {
                    Logger.getLogger(AddNewRoutineController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {
            MessageUtil.showError(Message.ERROR, "Add New Routine ", "No Routine Image File Selected ", this.getScene().getWindow());
        }
    }
}
