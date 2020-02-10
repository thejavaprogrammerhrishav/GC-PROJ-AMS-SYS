/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings.sub;

import com.attendance.login.dao.Login;
import com.attendance.login.service.LoginService;
import com.attendance.main.Start;
import com.attendance.papers.dao.PapersDao;
import com.attendance.papers.model.Paper;
import com.attendance.papers.service.PapersService;
import com.attendance.student.dao.StudentDao;
import com.attendance.student.service.StudentService;
import com.attendance.studentattendance.dao.ClassDetailsDao;
import com.attendance.studentattendance.model.ClassDetails;
import com.attendance.studentattendance.service.AttendanceService;
import com.attendance.util.ExceptionDialog;
import com.attendance.util.ExportClassDetails;
import com.attendance.util.Fxml;
import com.attendance.util.SystemUtils;
import com.attendance.util.Utils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

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
    private TableColumn<ClassDetails, String> tcoursetype;

    @FXML
    private TableColumn<ClassDetails, String> tpapercode;

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

    private AttendanceService cdao;
    private LoginService dao;
    private PapersService paperdao;
    private StudentService studentdao;
    private ExceptionDialog dialog;

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
        paperdao = (PapersService) Start.app.getBean("papersservice");
        cdao = (AttendanceService) Start.app.getBean("attendanceservice");
        studentdao = (StudentService) Start.app.getBean("studentservice");
        dao = (LoginService) Start.app.getBean("loginservice");
        
        cdao.setParent(this);
        dialog = cdao.getEx();
        
        initTable();
        initFilters();

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
        coursetype.getItems().setAll("Honours", "Pass");

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

        List<String> HODname = Utils.util.getHODUsers(SystemUtils.getDepartment()).stream().map(m -> m.getDetails().getName()).collect(Collectors.toList());
        List<String> faculties = Utils.util.getFacultyUsers(SystemUtils.getDepartment()).stream().map(m -> m.getDetails().getName()).collect(Collectors.toList());

        List<String> names = Stream.concat(HODname.stream(), faculties.stream()).collect(Collectors.toList());
        facultyname.getItems().setAll(names);

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
            if (n != null || !n.isEmpty()) {
                List<Paper> paperlist = paperdao.findByDepartment(SystemUtils.getDepartment());
                List<String> list = paperlist.stream().filter(f -> f.getSemester().equals(n.replace(" Semester", ""))).map(p -> p.getPaperCode()).collect(Collectors.toList());
                paper.getItems().setAll(list);
            }
        });
    }

    private void initTable() {
        name.setCellValueFactory(new PropertyValueFactory<ClassDetails, String>("facultyName"));
        date.setCellValueFactory(new PropertyValueFactory<ClassDetails, String>("date"));
        time.setCellValueFactory(new PropertyValueFactory<ClassDetails, String>("time"));
        tsemester.setCellValueFactory(new PropertyValueFactory<ClassDetails, String>("semester"));
        tyear.setCellValueFactory(new PropertyValueFactory<ClassDetails, Integer>("year"));
        tacadamicyear.setCellValueFactory(new PropertyValueFactory<ClassDetails, String>("acadamicyear"));
        tcoursetype.setCellValueFactory(new PropertyValueFactory<ClassDetails, String>("coursetype"));
        tpapercode.setCellValueFactory(new PropertyValueFactory<ClassDetails, String>("paper"));
    }

    private void populateTable(ActionEvent evt) {
        Task<List<ClassDetails>> task = new Task<List<ClassDetails>>() {
            @Override
            protected List<ClassDetails> call() throws Exception {
                List<ClassDetails> list = cdao.findByDepartment(SystemUtils.getDepartment());
                return list;
            }
        };
        task.setOnRunning(e -> LoadingController.show(this.getScene()));
        task.setOnSucceeded(e -> {
            try {
                table.getItems().clear();
                Thread.sleep(700);
                table.getItems().setAll(task.get());
                LoadingController.hide();
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(ExportDailyStatsListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        SystemUtils.getService().execute(task);
    }

    private void filters(ActionEvent evt) {
        Task<List<ClassDetails>> task = new Task<List<ClassDetails>>() {
            @Override
            protected List<ClassDetails> call() throws Exception {
                List<ClassDetails> list = cdao.findByDepartment(SystemUtils.getDepartment());

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
                return list;
            }
        };
        task.setOnRunning(e -> LoadingController.show(this.getScene()));
        task.setOnSucceeded(e -> {
            try {
                table.getItems().clear();
                Thread.sleep(700);
                table.getItems().setAll(task.get());
                LoadingController.hide();
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(ExportDailyStatsListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        SystemUtils.getService().execute(task);
    }

    private void export(ActionEvent evt) {
        ExportClassDetails exp = new ExportClassDetails(table);
        try {
            exp.createFile().convertToExcel("Class Details List").exportToFile();
            dialog.showSuccess(this, "Export Class Details List", "Class Details List Exported Successfully");
        } catch (IOException ex) {
            dialog.showError(this, "Export Class Details List", "Class Details List Export Failed");
        }
    }

}
