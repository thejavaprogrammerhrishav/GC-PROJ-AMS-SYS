/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings.sub;

import com.attendance.faculty.dao.FacultyDao;
import com.attendance.faculty.model.Faculty;
import com.attendance.main.Start;
import com.attendance.student.model.Student;
import com.attendance.util.Fxml;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;

/**
 *
 * @author Programmer Hrishav
 */
public class DeleteFacultyController extends AnchorPane {

    @FXML
    private TextField searchfaculty;

    @FXML
    private JFXButton search;

    @FXML
    private JFXButton clear;

    @FXML
    private JFXButton refresh;

    @FXML
    private TableView<Faculty> table;

    @FXML
    private TableColumn<Faculty, String> name;

    @FXML
    private TableColumn<Faculty, String> contact;

    @FXML
    private TextField fname;

    @FXML
    private TextField femail;

    @FXML
    private TextField fcontact;

    @FXML
    private TextField fgender;

    @FXML
    private JFXButton deletefaculty;

    @FXML
    private TextField department;
    
    @FXML
    private JFXButton cancel;

    private FXMLLoader fxml;
    private FacultyDao dao;
    private Faculty faculty;

    public DeleteFacultyController() {
        fxml = Fxml.getDeleteFacultyFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(DeleteFacultyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao = (FacultyDao) Start.app.getBean("facultyregistration");
        faculty = null;

        initTable();
        populateTable(null);

        search.setOnAction(this::search);
        clear.setOnAction(this::clear);
        refresh.setOnAction(this::refresh);
        table.setOnMouseClicked(this::tableClick);
        cancel.setOnAction(this::cancel);
        deletefaculty.setOnAction(this::deleteFaculty);
    }

    

    private void initTable() {
        name.setCellValueFactory(new PropertyValueFactory<Faculty, String>("fullName"));
        contact.setCellValueFactory(new PropertyValueFactory<Faculty, String>("contact"));
    }

    private void populateTable(ActionEvent evt) {
        List<Faculty> list = dao.findByDepartment(SystemUtils.getDepartment());
        table.getItems().setAll(list);
    }

    private void search(ActionEvent evt) {
        List<Faculty> list = dao.findAll();
        list=list.stream().filter(f->f.getContact().startsWith(searchfaculty.getText())).collect(Collectors.toList());
        table.getItems().setAll(list);
    }

    private void clear(ActionEvent evt) {
        searchfaculty.setText("");
        fname.setText("");
        fcontact.setText("");
        fgender.setText("");
        femail.setText("");
    }

    private void refresh(ActionEvent evt) {
        populateTable(evt);
    }

    private void tableClick(MouseEvent evt) {
        faculty = table.getSelectionModel().getSelectedItem();
        fname.setText(faculty.getName());
        fcontact.setText(faculty.getContact());
        fgender.setText(faculty.getGender());
        femail.setText(faculty.getEmailId());
        department.setText(faculty.getDepartment());
    }
   
    private void cancel(ActionEvent evt) {
        ((BorderPane) this.getParent()).setCenter(null);
    }

    private void deleteFaculty(ActionEvent evt) {
        if (faculty != null) {
            boolean del = dao.deleteFaculty(faculty);
            if (del == true) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setHeaderText("Delete Faculty");
                al.setContentText("Faculty Deleted Successfully");
                al.initOwner(((Node) evt.getSource()).getScene().getWindow());
                al.initModality(Modality.WINDOW_MODAL);
                al.show();
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setHeaderText("Delete Faculty");
                al.setContentText("Faculty Deletion Failed");
                al.initOwner(((Node) evt.getSource()).getScene().getWindow());
                al.initModality(Modality.WINDOW_MODAL);
                al.show();
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setHeaderText("Delete Faculty");
            al.setContentText("Please Select A Row From The Table");
            al.initOwner(((Node) evt.getSource()).getScene().getWindow());
            al.initModality(Modality.WINDOW_MODAL);
            al.show();
        }
    }
}
