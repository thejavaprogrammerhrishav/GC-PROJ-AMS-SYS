/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings.sub;

import com.attendance.login.dao.Login;
import com.attendance.main.Start;
import com.attendance.papers.dao.PapersDao;
import com.attendance.papers.model.Paper;
import com.attendance.student.dao.StudentDao;
import com.attendance.studentattendance.dao.ClassDetailsDao;
import com.attendance.studentattendance.model.ClassDetails;
import com.attendance.util.Fxml;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.attendance.util.Utils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Programmer Hrishav
 */
public class ClassDetailsController extends ScrollPane {

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
    private TableColumn<ClassDetails, String> akayear;

    @FXML
    private TableColumn<ClassDetails, String> ccoursetype;

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
    private JFXCheckBox filterbypaper;

    @FXML
    private JFXComboBox<String> paper;

    @FXML
    private JFXComboBox<String> facultyname;

    @FXML
    private TextField classId;

    @FXML
    private TextField faculty;

    @FXML
    private TextField subtaught;

    @FXML
    private TextField cdate;

    @FXML
    private TextField ctime;

    @FXML
    private TextField cpaper;

    @FXML
    private TextField csem;

    @FXML
    private TextField cyear;

    @FXML
    private TextField cacadamicyear;

    @FXML
    private JFXCheckBox filterbyacadamicyear;

    @FXML
    private JFXComboBox<String> acadamicyear;

    @FXML
    private JFXCheckBox filterbycourse;

    @FXML
    private JFXCheckBox honours;

    @FXML
    private JFXCheckBox pass;

    @FXML
    private Label department;

    @FXML
    private TextField coursetype;

    @FXML
    private TextField cdepartment;

    private ClassDetailsDao cdao;
    private Login dao;
    private PapersDao paperdao;
    private StudentDao studentdao;

    private Parent parent;
    private String currentfaculty;

    private FXMLLoader fxml;

    public ClassDetailsController(Parent parent, String currentfaculty) {
        this.parent = parent;
        this.currentfaculty = currentfaculty;
        fxml = Fxml.getClassDetailsFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ClassDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        department.setText(SystemUtils.getDepartment());
        paperdao = (PapersDao) Start.app.getBean("papers");
        cdao = (ClassDetailsDao) Start.app.getBean("classdetails");
        studentdao = (StudentDao) Start.app.getBean("studentregistration");
        dao = (Login) Start.app.getBean("userlogin");
        close.setOnAction(e -> SwitchRoot.switchRoot(Start.st, parent));
        initTable();
        initFilters();

        refresh.setOnAction(this::populateTable);
        applyfilters.setOnAction(this::filters);

        populateTable(null);

        table.setOnMouseClicked(this::tableClick);
    }

    private void initFilters() {
        if (!currentfaculty.equals("N/A")) {
            filterbyname.setDisable(true);
            facultyname.setDisable(true);
        }
        facultyname.disableProperty().bind(filterbyname.selectedProperty().not());
        semester.disableProperty().bind(filterbysemester.selectedProperty().not());
        year.disableProperty().bind(filterbyyear.selectedProperty().not());
        paper.disableProperty().bind(filterbypaper.selectedProperty().not());
        acadamicyear.disableProperty().bind(filterbyacadamicyear.selectedProperty().not());

        acadamicyear.getItems().setAll("1st", "2nd", "3rd");

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
        List<String> fname = Utils.util.getFacultyUsers(SystemUtils.getDepartment()).stream().map(m -> m.getDetails().getName()).collect(Collectors.toList());

        List<String> names = Stream.concat(HODname.stream(), fname.stream()).collect(Collectors.toList());
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
        honours.disableProperty().bind(filterbycourse.selectedProperty().not());
        pass.disableProperty().bind(filterbycourse.selectedProperty().not());

        honours.selectedProperty().addListener((ol, o, n) -> {
            if (n) {
                honours.setSelected(true);
                pass.setSelected(false);
            }
        });

        pass.selectedProperty().addListener((ol, o, n) -> {
            if (n) {
                pass.setSelected(true);
                honours.setSelected(false);
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
        akayear.setCellValueFactory(new PropertyValueFactory<>("acadamicyear"));
        ccoursetype.setCellValueFactory(new PropertyValueFactory<>("coursetype"));
    }

    private void populateTable(ActionEvent evt) {
        Task<List<ClassDetails>> task = new Task<List<ClassDetails>>() {
            @Override
            protected List<ClassDetails> call() throws Exception {
                List<ClassDetails> list = new ArrayList<>();
                list = cdao.findByDepartment(SystemUtils.getDepartment());
                if (!currentfaculty.equals("N/A")) {
                    list = list.stream().filter(p -> p.getFacultyName().equals(currentfaculty)).collect(Collectors.toList());
                }
                return list;
            }
        };
        task.setOnRunning(e -> LoadingController.show(this.getScene()));
        task.setOnSucceeded(e -> {
            Platform.runLater(() -> {
                try {
                    table.getItems().clear();
                    table.getItems().setAll(task.get());
                } catch (InterruptedException | ExecutionException ex) {
                    table.getItems().clear();
                }
            });
            LoadingController.hide();
        });
        SystemUtils.getService().execute(task);
    }

    private void filters(ActionEvent evt) {
        Task<List<ClassDetails>> task = new Task<List<ClassDetails>>() {
            @Override
            protected List<ClassDetails> call() throws Exception {

                List<ClassDetails> list = cdao.findByDepartment(SystemUtils.getDepartment());
                if (!currentfaculty.equals("N/A")) {
                    list = list.stream().filter(p -> p.getFacultyName().equals(currentfaculty)).collect(Collectors.toList());
                }

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
                if (filterbycourse.isSelected()) {
                    if (honours.isSelected()) {
                        list = list.stream().filter(s -> s.getCoursetype().equals("Honours")).collect(Collectors.toList());
                    }
                    if (pass.isSelected()) {
                        list = list.stream().filter(s -> s.getCoursetype().equals("Pass")).collect(Collectors.toList());
                    }
                }
                return list;
            }
        };
         task.setOnRunning(e -> LoadingController.show(this.getScene()));
        task.setOnSucceeded(e -> {
            Platform.runLater(() -> {
                try {
                    table.getItems().clear();
                    table.getItems().setAll(task.get());
                } catch (InterruptedException | ExecutionException ex) {
                    table.getItems().clear();
                }
            });
            LoadingController.hide();
        });
        SystemUtils.getService().execute(task);
    }

    private void tableClick(MouseEvent evt) {
        ClassDetails det = table.getSelectionModel().getSelectedItem();

        classId.setText(det.getClassId());
        faculty.setText(det.getFacultyName());
        subtaught.setText(det.getSubjectTaught());
        cdate.setText(det.getDate());
        ctime.setText(det.getTime());
        cpaper.setText(det.getPaper());
        csem.setText(det.getSemester());
        cyear.setText("" + det.getYear());
        cacadamicyear.setText(det.getAcadamicyear());
        cdepartment.setText(det.getDepartment());
        coursetype.setText(det.getCoursetype());
    }

}
