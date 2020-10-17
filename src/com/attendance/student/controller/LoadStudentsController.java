/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.student.controller;

import com.attendance.main.Start;
import com.attendance.student.dao.StudentDao;
import com.attendance.student.model.Student;
import com.attendance.student.service.StudentService;
import com.attendance.util.ExceptionDialog;
import com.attendance.util.Fxml;
import com.attendance.util.StageUtil;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Programmer Hrishav
 */
public class LoadStudentsController extends AnchorPane {

    @FXML
    private JFXCheckBox filterbyacademicyear;

    @FXML
    private JFXCheckBox filterbyyear;

    @FXML
    private JFXComboBox<String> facadamicyear;

    @FXML
    private JFXComboBox<String> fyear;

    @FXML
    private JFXCheckBox filterbyname;

    @FXML
    private JFXButton search;

    @FXML
    private JFXButton refresh;

    @FXML
    private TextField fstudentname;

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, String> studentColumnName;

    @FXML
    private TableColumn<Student, Integer> studentColumnYear;

    @FXML
    private TableColumn<Student, String> studentacadamicyear;

    @FXML
    private TableColumn<Student, String> studentcoursetype;

    @FXML
    private TableColumn<Student, Integer> studentrollno;
    
    @FXML
    private JFXButton close;

    private FXMLLoader fxml;
    private StudentService dao;
    private static Student student;
    private static Stage stage;
    private ExceptionDialog dialog;

    protected LoadStudentsController() {
        fxml = Fxml.getLoadStudentsFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(LoadStudentsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        close.setOnAction(e->stage.close());
        dao = (StudentService) Start.app.getBean("studentservice");
        dao.setParent(this);
        dialog = dao.getEx();
        
        inittable();
        load(null);
        
        student=null;

        facadamicyear.getItems().setAll("1st", "2nd", "3rd");
        fyear.getItems().setAll(dao.findAllYear());

        facadamicyear.disableProperty().bind(filterbyacademicyear.selectedProperty().not());
        fyear.disableProperty().bind(filterbyyear.selectedProperty().not());
        fstudentname.disableProperty().bind(filterbyname.selectedProperty().not());

        search.setOnAction(this::filter);
        refresh.setOnAction(this::load);

        studentTable.setOnMouseClicked(e -> {
            if (studentTable.getSelectionModel().getSelectedIndex() >= 0) {
                student = studentTable.getSelectionModel().getSelectedItem();
                stage.hide();
            }
        });
    }

    private void load(ActionEvent evt) {
        List<Student> data = dao.findByDepartment(SystemUtils.getDepartment());
        studentTable.getItems().setAll(data);
    }

    private void inittable() {
        studentColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        studentColumnYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        studentacadamicyear.setCellValueFactory(new PropertyValueFactory<>("acadamicyear"));
        studentcoursetype.setCellValueFactory(new PropertyValueFactory<>("courseType"));
        studentrollno.setCellValueFactory(new PropertyValueFactory<>("rollno"));

    }

    private void filter(ActionEvent evt) {
        List<Student> data = dao.findByDepartment(SystemUtils.getDepartment());
        if (filterbyname.isSelected()) {
            data = data.stream().filter(f -> f.getName().toLowerCase().contains(fstudentname.getText().toLowerCase())).collect(Collectors.toList());
        }
        if (filterbyacademicyear.isSelected()) {
            data = data.stream().filter(f -> f.getAcadamicyear().equals(facadamicyear.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }
        if (filterbyyear.isSelected()) {
            data = data.stream().filter(f -> f.getYear()== Integer.parseInt(fyear.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }

        studentTable.getItems().setAll(data);
    }

    public static Student Show(Scene scene) {
        stage = StageUtil.newStage().centerOnScreen().fullScreen(false).fullScreenExitHint("").fullScreenExitKeyCombination(KeyCombination.NO_MATCH)
                .initModality(Modality.WINDOW_MODAL).initOwner(scene.getWindow()).initStyle(StageStyle.UNDECORATED).build();
        stage.setScene(new Scene(new LoadStudentsController()));
        stage.showAndWait();
        return student;
    }

}
