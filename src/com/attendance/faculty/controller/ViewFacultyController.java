/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.faculty.controller;

import com.attendance.faculty.dao.FacultyDao;
import com.attendance.faculty.model.Faculty;
import com.attendance.main.Start;
import com.attendance.util.Fxml;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 *
 * @author Programmer Hrishav
 */
public class ViewFacultyController extends AnchorPane {

    @FXML
    private TableView<Faculty> facultyList;

    @FXML
    private TableColumn<Faculty, String> facultyListName;

    @FXML
    private TableColumn<Faculty, String> facultyListContact;

    @FXML
    private TextField searchFaculty;

    @FXML
    private JFXButton search;

    @FXML
    private TextField firstName;

    @FXML
    private TextField emailId;

    @FXML
    private TextField contact;

    @FXML
    private JFXCheckBox male;

    @FXML
    private JFXCheckBox female;

    @FXML
    private Button close;

    @FXML
    private Button refresh;

    @FXML
    private Label department;

    private FXMLLoader fxml;
    private FacultyDao dao;

    public ViewFacultyController() {
        fxml = Fxml.getViewFacultyDetailsFXML();
        fxml.setRoot(ViewFacultyController.this);
        fxml.setController(ViewFacultyController.this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ViewFacultyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao = (FacultyDao) Start.app.getBean("facultyregistration");
        department.setText("Department: " + SystemUtils.getDepartment());
        facultyListName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Faculty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Faculty, String> p) {
                return new SimpleStringProperty(p.getValue().getName());
            }
        });

        facultyListContact.setCellValueFactory(new PropertyValueFactory<Faculty, String>("contact"));

        loadAllFaculty(null);

        refresh.setOnAction(this::loadAllFaculty);

        close.setOnAction(e -> ((Node) e.getSource()).getScene().getWindow().hide());

        search.setOnAction(this::searchFaculty);
        facultyList.setOnMouseClicked(this::displayFacultyDetails);
    }

    private void loadAllFaculty(ActionEvent e) {
        List<Faculty> faculties = new ArrayList<Faculty>(dao.findByDepartment(SystemUtils.getDepartment()));
        facultyList.setItems(FXCollections.observableArrayList(faculties));
    }

    private void searchFaculty(ActionEvent e) {
        List<Faculty> searchList = dao.findByDepartment(SystemUtils.getDepartment());
        searchList = searchList.stream().filter(p -> p.getName().contains(searchFaculty.getText())).collect(Collectors.toList());
        facultyList.setItems(FXCollections.observableArrayList(searchList));
    }

    private void displayFacultyDetails(MouseEvent evt) {
        Faculty selected = facultyList.getSelectionModel().getSelectedItem();
        firstName.setText(selected.getName());
        emailId.setText(selected.getEmailId());
        contact.setText(selected.getContact());
        if (selected.getGender().equalsIgnoreCase("Male")) {
            male.setSelected(true);
        } else if (selected.getGender().equalsIgnoreCase("Female")) {
            female.setSelected(true);
        } else {
            male.setSelected(false);
            female.setSelected(false);
        }
    }

}
