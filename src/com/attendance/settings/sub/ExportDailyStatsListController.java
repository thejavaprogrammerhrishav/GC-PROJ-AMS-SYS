/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings.sub;

import com.attendance.main.Start;
import com.attendance.student.dao.StudentDao;
import com.attendance.student.service.StudentService;
import com.attendance.studentattendance.dao.ClassDetailsDao;
import com.attendance.studentattendance.model.ClassDetails;
import com.attendance.studentattendance.model.DailyStats;
import com.attendance.studentattendance.service.AttendanceService;
import com.attendance.util.DailyStatsUtilModel;
import com.attendance.util.ExceptionDialog;
import com.attendance.util.ExportDailyStats;
import com.attendance.util.Fxml;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author Programmer Hrishav
 */
public class ExportDailyStatsListController extends AnchorPane {

    @FXML
    private TableView<DailyStatsUtilModel> table;

    @FXML
    private TableColumn<DailyStatsUtilModel, String> tclassdate;

    @FXML
    private TableColumn<DailyStatsUtilModel, String> tclasstime;

    @FXML
    private TableColumn<DailyStatsUtilModel, String> tsemester;

    @FXML
    private TableColumn<DailyStatsUtilModel, String> tyear;

    @FXML
    private TableColumn<DailyStatsUtilModel, Integer> tstudents;

    @FXML
    private TableColumn<DailyStatsUtilModel, Integer> tpresent;

    @FXML
    private TableColumn<DailyStatsUtilModel, Integer> tabsent;

    @FXML
    private TableColumn<DailyStatsUtilModel, Double> tpresentper;

    @FXML
    private TableColumn<DailyStatsUtilModel, Double> tabsentper;

    @FXML
    private TableColumn<DailyStatsUtilModel, String> tacadamicyear;

    @FXML
    private JFXCheckBox filterbysemester;

    @FXML
    private JFXCheckBox filterbymonth;

    @FXML
    private JFXComboBox<String> semester;

    @FXML
    private JFXComboBox<String> month;

    @FXML
    private JFXCheckBox filterbyyear;

    @FXML
    private JFXComboBox<String> year;

    @FXML
    private JFXButton applyfilters;

    @FXML
    private JFXButton refresh;

    @FXML
    private JFXButton export2excel;

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

    private FXMLLoader fxml;

    private StudentService sdao;
    private AttendanceService cdao;
    private ExceptionDialog dialog;

    public ExportDailyStatsListController() {
        fxml = Fxml.getExportDailtStatsListFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ExportDailyStatsListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        department.setText(SystemUtils.getDepartment());
        sdao = (StudentService) Start.app.getBean("studentservice");
        cdao = (AttendanceService) Start.app.getBean("attendanceservice");
        cdao.setParent(this);
        dialog = cdao.getEx();
        
        initFilters();
        initTable();
        populateTable(null);

        applyfilters.setOnAction(this::applyfilters);
        refresh.setOnAction(this::populateTable);
        export2excel.setOnAction(this::export);
    }

    private void initFilters() {
        semester.disableProperty().bind(filterbysemester.selectedProperty().not());
        year.disableProperty().bind(filterbyyear.selectedProperty().not());
        month.disableProperty().bind(filterbymonth.selectedProperty().not());
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

        month.getItems().setAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

        List<String> years = sdao.get("select distinct(year) from student order by year", String.class);
        year.getItems().setAll(years);
    }

    private void initTable() {
        tclassdate.setCellValueFactory(new PropertyValueFactory<DailyStatsUtilModel, String>("date"));
        tclasstime.setCellValueFactory(new PropertyValueFactory<DailyStatsUtilModel, String>("time"));
        tsemester.setCellValueFactory(new PropertyValueFactory<DailyStatsUtilModel, String>("semester"));
        tyear.setCellValueFactory(new PropertyValueFactory<DailyStatsUtilModel, String>("year"));
        tstudents.setCellValueFactory(new PropertyValueFactory<DailyStatsUtilModel, Integer>("totalStudent"));
        tpresent.setCellValueFactory(new PropertyValueFactory<DailyStatsUtilModel, Integer>("totalPresent"));
        tabsent.setCellValueFactory(new PropertyValueFactory<DailyStatsUtilModel, Integer>("totalAbsent"));
        tpresentper.setCellValueFactory(new PropertyValueFactory<DailyStatsUtilModel, Double>("presentPercentage"));
        tabsentper.setCellValueFactory(new PropertyValueFactory<DailyStatsUtilModel, Double>("absentPercentage"));
        tacadamicyear.setCellValueFactory(new PropertyValueFactory<DailyStatsUtilModel, String>("acadamicyear"));
    }

    private void populateTable(ActionEvent evt) {
        Task<List<DailyStatsUtilModel>> task = new Task<List<DailyStatsUtilModel>>() {
            @Override
            protected List<DailyStatsUtilModel> call() throws Exception {
                Map<String, DailyStats> list = cdao.findByDepartment(SystemUtils.getDepartment()).stream().collect(Collectors.toMap(ClassDetails::getClassId, ClassDetails::getDailyStats));

                List<DailyStatsUtilModel> nlist = list.entrySet().stream().map(a -> {
                    DailyStatsUtilModel at = new DailyStatsUtilModel();
                    at.setTotalStudent(a.getValue().getTotalStudent());
                    at.setTotalPresent(a.getValue().getTotalPresent());
                    at.setTotalAbsent(a.getValue().getTotalAbsent());

                    at.setPresentPercentage(a.getValue().getPresentPercentage());
                    at.setAbsentPercentage(a.getValue().getAbsentPercentage());

                    String s = a.getKey();
                    String[] ss = s.split("@");
                    at.setDate(ss[0].split("/")[1]);

                    ss = ss[1].split("#");
                    at.setTime(ss[0]);

                    ss = ss[1].split("__");
                    at.setAcadamicyear(ss[0]);

                    ss = ss[1].split("_");
                    at.setSemester(ss[0] + " Semester");

                    ss = ss[1].split("&");
                    at.setYear(ss[0]);

                    if (a.getKey().charAt(a.getKey().length() - 1) == 'H') {
                        at.setCoursetype("Honours");
                    }

                    if (a.getKey().charAt(a.getKey().length() - 1) == 'P') {
                        at.setCoursetype("Pass");
                    }

                    return at;
                }).collect(Collectors.toList());
                return nlist;
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

    private void applyfilters(ActionEvent evt) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
        Task<List<DailyStatsUtilModel>> task = new Task<List<DailyStatsUtilModel>>() {
            @Override
            protected List<DailyStatsUtilModel> call() throws Exception {
                List<DailyStatsUtilModel> nlist = table.getItems();

                if (filterbyacadamicyear.isSelected()) {
                    nlist = nlist.stream().filter(s -> s.getAcadamicyear().equals(acadamicyear.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
                }
                if (filterbymonth.isSelected()) {
                    int mm = month.getSelectionModel().getSelectedIndex() + 1;

                    nlist = nlist.stream().filter(f -> DateTime.parse(f.getDate(), dtf).getMonthOfYear() == mm).collect(Collectors.toList());

                }
                if (filterbysemester.isSelected()) {
                    nlist = nlist.stream().filter(s -> s.getSemester().equals(semester.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
                }

                if (filterbyyear.isSelected()) {
                    nlist = nlist.stream().filter(s -> s.getYear().equals(year.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
                }

                if (filterbycoursetype.isSelected()) {
                    nlist = nlist.stream().filter(s -> s.getCoursetype().equals(coursetype.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
                }

                return nlist;
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
        ExportDailyStats exp = new ExportDailyStats(table);
        try {
            exp.createFile().convertToExcel("Daily Class Statistics List").exportToFile();
            dialog.showSuccess(this, "Export Class Daily Stats List", "Daily Stats List Exported Successfully");
        } catch (IOException ex) {
            dialog.showError(this, "Export Class Daily Stats List", "Daily Stats List Export Failed");
        }
    }
}
