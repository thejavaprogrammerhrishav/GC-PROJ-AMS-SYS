/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings.sub;

import com.attendance.login.dao.Login;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.personal.dao.PersonalDetailsDao;
import com.attendance.personal.model.PersonalDetails;
import com.attendance.util.Fxml;
import com.attendance.util.Message;
import com.attendance.util.MessageUtil;
import com.attendance.util.SwitchRoot;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Programmer Hrishav
 */
public class DeleteFacultyUserController extends AnchorPane {

    @FXML
    private TextField search;

    @FXML
    private JFXButton searchbyname;

    @FXML
    private JFXButton refresh;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField contact;

    @FXML
    private JFXButton deletefaculty;

    @FXML
    private JFXButton cancel;

    @FXML
    private TableView<User> table;

    @FXML
    private TableColumn<User, String> nameofuser;

    @FXML
    private TableColumn<User, String> username;

    @FXML
    private JFXButton clear;

    @FXML
    private JFXButton searchbycontact;

    @FXML
    private JFXButton searchbyusername;

    private List<User> list;
    private User user;
    private Login dao;
    private PersonalDetailsDao fdao;

    private FXMLLoader fxml;
    private Parent parent;

    public DeleteFacultyUserController(Parent parent) {
        this.parent = parent;
        fxml = Fxml.getDeleteFacultyUserFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(DeleteFacultyUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao = (Login) Start.app.getBean("userlogin");
        fdao = (PersonalDetailsDao) Start.app.getBean("facultyregistration");

        list = dao.findByType("Faculty");

        nameofuser.setCellValueFactory(new PropertyValueFactory<>("name"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        def(null);

        table.setOnMouseClicked(this::tableClick);

        cancel.setOnAction(e -> SwitchRoot.switchRoot(Start.st, parent));
        searchbycontact.setOnAction(this::searchContact);
        searchbyname.setOnAction(this::searchName);
        searchbyusername.setOnAction(this::searchUsername);
        refresh.setOnAction(this::def);
        clear.setOnAction(this::clear);
        deletefaculty.setOnAction(this::delete);
    }

    private void reinit() {
        list = dao.findByType("Faculty");
    }

    private void searchName(ActionEvent evt) {
       
    }

    private void searchContact(ActionEvent evt) {
        List<User> contactlist = list.stream().filter(u -> u.getDepartment().startsWith(search.getText())).collect(Collectors.toList());
        table.getItems().setAll(contactlist);
    }

    private void searchUsername(ActionEvent evt) {
        List<User> namelist = list.stream().filter(u -> u.getUsername().startsWith(search.getText())).collect(Collectors.toList());
        table.getItems().setAll(namelist);
    }

    private void def(ActionEvent evt) {
        reinit();
        table.getItems().setAll(list);
    }

    private void tableClick(MouseEvent evt) {
        user = table.getSelectionModel().getSelectedItem();

        contact.setText(user.getDepartment());
    }

    private void clear(ActionEvent evt) {
        name.setText("");
        contact.setText("");
        email.setText("");
    }

    private void delete(ActionEvent evt) {
        PersonalDetails del = fdao.findById(user.getPersonalid());
        boolean d = dao.delete(user);
        boolean e = fdao.delete(del);
        if (d && e) {
            MessageUtil.showInformation(Message.INFORMATION, "Faculty Login Account", "Account Updated Successfully", DeleteFacultyUserController.this.getScene().getWindow());
        } else {
            MessageUtil.showError(Message.ERROR, "Faculty Login Account", "Account Updation Failed", DeleteFacultyUserController.this.getScene().getWindow());
        }
    }
}
