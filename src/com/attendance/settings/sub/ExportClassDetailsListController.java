/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings.sub;

import com.attendance.faculty.dao.FacultyDao;
import com.attendance.main.Start;
import com.attendance.papers.dao.PapersDao;
import com.attendance.papers.model.Paper;
import com.attendance.student.dao.StudentDao;
import com.attendance.studentattendance.dao.ClassDetailsDao;
import com.attendance.studentattendance.model.ClassDetails;
import com.attendance.util.ExportClassDetails;
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
public class ExportClassDetailsListController extends AnchorPane {

    @FXML
    private TableView<ClassDetails> table;

    @FXML
    private TableColumn<ClassDetails, String> name;

    @FXML
    private TableColumn<ClassDetails, String> date;

    @FXML
    private TableColumn<ClassDetails, String> time;

    @FXML
    private TableColumn<ClassDetails, String> tsemester;

    @FXML
    private TableColumn<ClassDetails, Integer> tyear;

    @FXML
    private TableColumn<ClassDetails, String> tacadamicyear;

    @FXML
    private JFXCheckBox filterbyname;

    @FXML
    private JFXCheckBox filterbysemester;

    @FXML
    private JFXCheckBox filterbyyear;

    @FXML
    private JFXComboBox<String> semester;

    @FXML
    private JFXComboBox<String> year;

    @FXML
    private JFXButton applyfilters;

    @FXML
    private JFXButton refresh;

    @FXML
    private JFXButton close;

    @FXML
    private JFXButton export2excel;

    @FXML
    private JFXCheckBox filterbypaper;

    @FXML
    private JFXComboBox<String> paper;

    @FXML
    private JFXComboBox<String> facultyname;

    @FXML
    private JFXCheckBox filterbyacadamicyear;

    @FXML
    private JFXComboBox<String> acadamicyear;

    @FXML
    private Label department;

    @FXML
    private JFXCheckBox filterbycoursetype;

    @FXML
    private JFXComboBox<String> coursetype;
    
    private ClassDetailsDao dao;
    private PapersDao paperdao;
    private FacultyDao facultydao;
    private StudentDao studentdao;

    private FXMLLoader fxml;

    public ExportClassDetailsListController() {
        fxml = Fxml.getExportClassDetailsListFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ExportClassDetailsListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        department.setText(SystemUtils.getDepartment());
        paperdao = (PapersDao) Start.app.getBean("papers");
        dao = (ClassDetailsDao) Start.app.getBean("classdetails");
        facultydao = (FacultyDao) Start.app.getBean("facultyregistration");
        studentdao = (StudentDao) Start.app.getBean("studentregistration");
        initTable();
        initFilters();

        close.setOnAction(e -> ((BorderPane) this.getParent()).setCenter(null));
        refresh.setOnAction(this::populateTable);
        applyfilters.setOnAction(this::filters);
        export2excel.setOnAction(this::export);

        populateTable(null);

    }

    private void initFilters() {
        facultyname.disableProperty().bind(filterbyname.selectedProperty().not());
        semester.disableProperty().bind(filterbysemester.selectedProperty().not());
        year.disableProperty().bind(filterbyyear.selectedProperty().not());
        paper.disableProperty().bind(filterbypaper.selectedProperty().not());
        acadamicyear.disableProperty().bind(filterbyacadamicyear.selectedProperty().not());
        coursetype.disableProperty().bind(filterbycoursetype.selectedProperty().not());
        
        acadamicyear.getItems().setAll("1st", "2nd", "3rd");
        coursetype.getItems().setAll("Honours","Pass");

        acadamicyear.getSelectionModel().selectedItemProperty().addListener((ol, o, n) -> {
            if (n.equals("1st")) {
                semester.getItems().setAll("1st Semester", "2nd Semester");
            } else if (n.equals("2nd")) {
                semester.getItems().setAll("3rd Semester", "4th Semester");
            } else {
                semester.getItems().setAll("5th Semester", "6th Semester");
            }
        });

        List<String> years = studentdao.get("select distinct(year) from student order by year", String.class);
        year.getItems().setAll(years);

        List<String> faculties = facultydao.findAll().stream().map(f -> f.getFullName()).collect(Collectors.toList());
        facultyname.getItems().setAll(faculties);

        filterbypaper.selectedProperty().addListener((ol, o, n) -> {
            if (filterbysemester.isSelected()) {
                filterbypaper.setSelected(n);
            } else {
                filterbypaper.setSelected(false);
            }
        });

        filterbysemester.selectedProperty().addListener((ol, o, n) -> {
            if (!n) {
                filterbypaper.setSelected(false);
            }
        });

        filterbyacadamicyear.selectedProperty().addListener((ol, o, n) -> {
            if (!n) {
                filterbysemester.setSelected(false);
            }
        });

        semester.getSelectionModel().selectedItemProperty().addListener((ol, o, n) -> {
            String sem = n.replace(" Semester", "");
            List<Paper> papers = paperdao.findBySemester(sem);
            paper.getItems().setAll(papers.stream().map(p -> p.getPaperCode()).collect(Collectors.toList()));
        });
    }

    private void initTable() {
        name.setCellValueFactory(new PropertyValueFactory<ClassDetails, String>("facultyName"));
        date.setCellValueFactory(new PropertyValueFactory<ClassDetails, String>("date"));
        time.setCellValueFactory(new PropertyValueFactory<ClassDetails, String>("time"));
        tsemester.setCellValueFactory(new PropertyValueFactory<ClassDetails, String>("semester"));
        tyear.setCellValueFactory(new PropertyValueFactory<ClassDetails, Integer>("year"));
        tacadamicyear.setCellValueFactory(new PropertyValueFactory<ClassDetails, String>("acadamicyear"));
    }

    private void populateTable(ActionEvent evt) {
        List<ClassDetails> list = dao.findAll();
        table.getItems().setAll(list);
    }

    private void filters(ActionEvent evt) {
        List<ClassDetails> list = dao.findAll();

        if (filterbyacadamicyear.isSelected()) {
            list = list.stream().filter(s -> s.getAcadamicyear().equals(acadamicyear.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }
        if (filterbyname.isSelected()) {
            list = list.stream().filter(s -> s.getFacultyName().equals(facultyname.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }
        if (filterbysemester.isSelected()) {
            list = list.stream().filter(s -> s.getSemester().equals(semester.getSelectionModel().getSelectedItem().replace(" Semester", ""))).collect(Collectors.toList());
        }
        if (filterbyyear.isSelected()) {
            list = list.stream().filter(s -> s.getYear() == Integer.parseInt(year.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }
        if (filterbypaper.isSelected()) {
            list = list.stream().filter(s -> s.getPaper().equals(paper.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }
        
        if (filterbycoursetype.isSelected()) {
            list = list.stream().filter(s -> s.getCoursetype().equals(coursetype.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }
        table.getItems().setAll(list);
    }

    private void export(ActionEvent evt) {
        ExportClassDetails exp = new ExportClassDetails(table);
        try {
            exp.createFile().convertToExcel("Class Details List").exportToFile();
        } catch (IOException ex) {
            Logger.getLogger(ExportClassDetailsListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
