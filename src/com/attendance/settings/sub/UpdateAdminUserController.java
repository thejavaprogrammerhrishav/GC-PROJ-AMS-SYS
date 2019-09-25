/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings.sub;

import com.attendance.login.dao.Login;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.util.Fxml;
import com.attendance.util.Message;
import com.attendance.util.MessageUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Programmer Hrishav
 */
public class UpdateAdminUserController extends ScrollPane {

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField contact;

    @FXML
    private JFXTextField username;

    @FXML
    private TextField search;

    @FXML
    private JFXButton searchbyname;

    @FXML
    private JFXButton searchbycontact;

    @FXML
    private JFXButton searchbyusername;

    @FXML
    private JFXCheckBox changepassword;

    @FXML
    private JFXPasswordField newpassword;

    @FXML
    private JFXPasswordField confirmpassword;

    @FXML
    private JFXButton updateadmin;

    @FXML
    private JFXButton close;

    @FXML
    private TableView<User> table;

    @FXML
    private TableColumn<User, String> nameofuser;

    @FXML
    private JFXButton refresh;

    @FXML
    private JFXButton clear;

    private Login dao;
    private FXMLLoader fxml;
    private List<User> list;

    private User user;

    public UpdateAdminUserController() {
        fxml = Fxml.getUpdateAdminFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(UpdateAdminUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao = (Login) Start.app.getBean("userlogin");
        list = dao.findByType("Admin");

        newpassword.disableProperty().bind(changepassword.selectedProperty().not());
        confirmpassword.disableProperty().bind(changepassword.selectedProperty().not());

        nameofuser.setCellValueFactory(new PropertyValueFactory<>("name"));
        def(null);

        table.setOnMouseClicked(this::tableClick);

        close.setOnAction(e -> ((BorderPane) this.getParent()).setCenter(null));
        searchbycontact.setOnAction(this::searchContact);
        searchbyname.setOnAction(this::searchName);
        searchbyusername.setOnAction(this::searchUsername);
        refresh.setOnAction(this::def);
        clear.setOnAction(this::clear);
        updateadmin.setOnAction(this::update);
    }

    private void reinit() {
        list = dao.findByType("Admin");
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
        username.setText(user.getUsername());
    }

    private void clear(ActionEvent evt) {
        name.setText("");
        contact.setText("");
        email.setText("");
        username.setText("");
        newpassword.setText("");
        confirmpassword.setText("");
        changepassword.setSelected(false);
    }

    private void update(ActionEvent evt) {
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setType("Admin");

        if (username.getText().equals(user.getUsername())) {
            newUser.setUsername(username.getText());
        } else {
            if (dao.isUsernameExists(username.getText()) > 0) {
                MessageUtil.showError(Message.ERROR, "Administrator Login Account", "Username is already taken", UpdateAdminUserController.this.getScene().getWindow());
            } else {
                newUser.setUsername(username.getText());
            }
        }
        if (changepassword.isSelected()) {
            if (newpassword.getText().equals(confirmpassword.getText())) {
                newUser.setPassword(newpassword.getText());
            } else {
                MessageUtil.showError(Message.ERROR, "Administrator Login Account", "Passwords doesn't match", UpdateAdminUserController.this.getScene().getWindow());
            }
        } else {
            newUser.setPassword(user.getPassword());
        }

        boolean d = dao.update(newUser);
        if (d) {
            MessageUtil.showInformation(Message.INFORMATION, "Administrator Login Account", "Account Updated Successfully", UpdateAdminUserController.this.getScene().getWindow());
        } else {
            MessageUtil.showError(Message.ERROR, "Administrator Login Account", "Account Updation Failed", UpdateAdminUserController.this.getScene().getWindow());
        }
    }
}
