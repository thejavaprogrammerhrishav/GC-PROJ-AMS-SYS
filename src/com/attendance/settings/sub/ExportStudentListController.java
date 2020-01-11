/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings.sub;

import com.attendance.main.Start;
import com.attendance.student.dao.StudentDao;
import com.attendance.student.model.Student;
import com.attendance.util.ExportStudentList;
import com.attendance.util.Fxml;
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
public class ExportStudentListController extends AnchorPane {

    @FXML
    private TableView<Student> table;

    @FXML
    private TableColumn<Student, String> tname;

    @FXML
    private TableColumn<Student, Integer> tyear;

    @FXML
    private TableColumn<Student, Integer> trollno;

    @FXML
    private TableColumn<Student, String> tsem;

    @FXML
    private TableColumn<Student, String> tcontact;

    @FXML
    private TableColumn<Student, String> tgender;

    @FXML
    private JFXCheckBox filterbysemester;

    @FXML
    private JFXCheckBox filterbyyear;

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
    private JFXComboBox<String> year;

    @FXML
    private JFXComboBox<String> semester;

    @FXML
    private JFXButton refresh;

    @FXML
    private Label department;

    @FXML
    private JFXCheckBox filterbycoursetype;

    @FXML
    private JFXComboBox<String> coursetype;

    private FXMLLoader fxml;

    private StudentDao dao;
    private List<Student> list;

    public ExportStudentListController() {
        fxml = Fxml.getExportStudentListFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ExportStudentListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        department.setText(SystemUtils.getDepartment());
        dao = (StudentDao) Start.app.getBean("studentregistration");

        semester.getItems().addAll("1st", "2nd", "3rd");

        List<String> years = dao.get("select distinct(year) from student order by year", String.class);
        year.getItems().setAll(years);

        tname.setCellValueFactory(new PropertyValueFactory<>("name"));
        trollno.setCellValueFactory(new PropertyValueFactory<>("rollno"));
        tyear.setCellValueFactory(new PropertyValueFactory<>("year"));
        tsem.setCellValueFactory(new PropertyValueFactory<>("acadamicyear"));
        tgender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        tcontact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        semester.disableProperty().bind(filterbysemester.selectedProperty().not());
        year.disableProperty().bind(filterbyyear.selectedProperty().not());
        male.disableProperty().bind(filterbygender.selectedProperty().not());
        female.disableProperty().bind(filterbygender.selectedProperty().not());

        coursetype.disableProperty().bind(filterbycoursetype.selectedProperty().not());

        filterbygender.selectedProperty().addListener((ol,o,n)->{
            if(!n) {
                male.setSelected(false);
                female.setSelected(false);
            }
        });
        coursetype.getItems().setAll("Honours", "Pass");
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

        applyfilters.setOnAction(this::applyfilter);
        refresh.setOnAction(this::populate);
        export2excel.setOnAction(this::export);
        cancel.setOnAction(e -> ((BorderPane) this.getParent()).setCenter(null));

        populate(null);
    }

    private void reinit() {
        list = dao.findByDepartment(SystemUtils.getDepartment());
    }

    private void populate(ActionEvent evt) {
        reinit();
        table.getItems().setAll(list);
    }

    private void applyfilter(ActionEvent evt) {
        List<Student> filteredList = table.getItems();
        if (filterbygender.isSelected()) {
            if (male.isSelected()) {
                filteredList = filteredList.stream().filter(f -> f.getGender().equalsIgnoreCase("Male")).collect(Collectors.toList());
            }
            if (female.isSelected()) {
                filteredList = filteredList.stream().filter(f -> f.getGender().equalsIgnoreCase("Female")).collect(Collectors.toList());
            }
        }
        if (filterbysemester.isSelected()) {
            filteredList = filteredList.stream().filter(f -> f.getAcadamicyear().equalsIgnoreCase(semester.getSelectionModel().getSelectedItem().replace(" Semester", ""))).collect(Collectors.toList());
        }
        if (filterbyyear.isSelected()) {
            filteredList = filteredList.stream().filter(f -> f.getYear() == Integer.parseInt(year.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }
        if (filterbycoursetype.isSelected()) {
            filteredList = filteredList.stream().filter(s -> s.getCourseType().equals(coursetype.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }
        table.getItems().setAll(filteredList);
    }

    private void export(ActionEvent evt) {
        ExportStudentList exp = new ExportStudentList(table);
        try {
            exp.createFile().convertToExcel("Student List").exportToFile();
        } catch (IOException ex) {
            Logger.getLogger(ExportStudentListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
