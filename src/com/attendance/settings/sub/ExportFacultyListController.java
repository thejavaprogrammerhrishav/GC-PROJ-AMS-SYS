/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings.sub;

import com.attendance.faculty.dao.FacultyDao;
import com.attendance.faculty.model.Faculty;
import com.attendance.main.Start;
import com.attendance.util.ExportFacultyList;
import com.attendance.util.Fxml;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Programmer Hrishav
 */
public class ExportFacultyListController extends AnchorPane {

    @FXML
    private JFXCheckBox filterbygender;

    @FXML
    private JFXCheckBox male;

    @FXML
    private JFXCheckBox female;

    @FXML
    private JFXButton export2excel;

    @FXML
    private JFXButton cancel;

    @FXML
    private JFXButton applyfilters;

    @FXML
    private JFXButton refresh;

    @FXML
    private TableView<Faculty> table;

    @FXML
    private TableColumn<Faculty, String> name;

    @FXML
    private TableColumn<Faculty, String> contact;

    @FXML
    private TableColumn<Faculty, String> gender;

    @FXML
    private TableColumn<Faculty, String> email;
    
    @FXML
    private Label department;

    private FXMLLoader fxml;

    private List<Faculty> list;

    private FacultyDao dao;

    public ExportFacultyListController() {
        fxml = Fxml.getExportFacultyListFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ExportFacultyListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        department.setText(SystemUtils.getDepartment());
        dao = (FacultyDao) Start.app.getBean("facultyregistration");

        name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        email.setCellValueFactory(new PropertyValueFactory<>("emailId"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));

        male.disableProperty().bind(filterbygender.selectedProperty().not());
        female.disableProperty().bind(filterbygender.selectedProperty().not());

        male.selectedProperty().addListener((ol, o, n) -> {
            if (n) {
                male.setSelected(true);
                female.setSelected(false);
            }
        });

        female.selectedProperty().addListener((ol, o, n) -> {
            if (n) {
                female.setSelected(true);
                male.setSelected(false);
            }
        });

        applyfilters.setOnAction(this::apply);
        refresh.setOnAction(this::populate);
        export2excel.setOnAction(this::export);
        cancel.setOnAction(e -> ((BorderPane) this.getParent()).setCenter(null));

        populate(null);
    }

    private void apply(ActionEvent evt) {
        List<Faculty> filter = table.getItems();
        if (filterbygender.isSelected()) {
            if (male.isSelected()) {
                filter = filter.stream().filter(f -> f.getGender().equalsIgnoreCase("male")).collect(Collectors.toList());
            }
            if (female.isSelected()) {
                filter = filter.stream().filter(f -> f.getGender().equalsIgnoreCase("female")).collect(Collectors.toList());
            }
        }
        table.getItems().setAll(filter);
    }

    private void reinit() {
        list = dao.findByDepartment(SystemUtils.getDepartment());
    }

    private void populate(ActionEvent evt) {
        reinit();
        table.getItems().setAll(list);
    }

    private void export(ActionEvent evt) {
        ExportFacultyList exp = new ExportFacultyList(table);
        try {
            exp.createFile().convertToExcel("Faculty List").exportToFile();
        } catch (IOException ex) {
            Logger.getLogger(ExportFacultyListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
