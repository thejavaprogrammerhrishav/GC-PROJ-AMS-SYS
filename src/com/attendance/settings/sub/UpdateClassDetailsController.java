/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings.sub;

import com.attendance.login.service.LoginService;
import com.attendance.main.Start;
import com.attendance.papers.model.Paper;
import com.attendance.papers.service.PapersService;
import com.attendance.student.service.StudentService;
import com.attendance.studentattendance.model.ClassDetails;
import com.attendance.studentattendance.service.AttendanceService;
import com.attendance.util.ExceptionDialog;
import com.attendance.util.Fxml;
import com.attendance.util.SystemUtils;
import com.attendance.util.Utils;
import com.attendance.util.ValidationUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
import javax.validation.ConstraintViolation;

/**
 *
 * @author Programmer Hrishav
 */
public class UpdateClassDetailsController extends ScrollPane {

    @FXML
    private JFXButton applyfilter;

    @FXML
    private TableView<ClassDetails> table;

    @FXML
    private TableColumn<ClassDetails, String> tclassid;

    @FXML
    private TableColumn<ClassDetails, String> tfacultyname;

    @FXML
    private TableColumn<ClassDetails, String> tsubjecttaught;

    @FXML
    private TableColumn<ClassDetails, String> tdate;

    @FXML
    private TableColumn<ClassDetails, String> ttime;

    @FXML
    private TableColumn<ClassDetails, String> tsemester;

    @FXML
    private TableColumn<ClassDetails, Integer> tyear;

    @FXML
    private TableColumn<ClassDetails, String> tpapercode;

    @FXML
    private TableColumn<ClassDetails, String> tacadamicyear;

    @FXML
    private TableColumn<ClassDetails, String> tcoursetype;

    @FXML
    private JFXButton refresh;

    @FXML
    private JFXCheckBox filterbyfaculty;

    @FXML
    private JFXComboBox<String> facultyname;

    @FXML
    private JFXCheckBox filterbydate;

    @FXML
    private JFXCheckBox filterbyyear;

    @FXML
    private JFXCheckBox filterbysemester;

    @FXML
    private JFXComboBox<String> year;

    @FXML
    private JFXComboBox<String> semester;

    @FXML
    private JFXCheckBox filterbypaper;

    @FXML
    private JFXComboBox<String> paper;

    @FXML
    private TextField cdsubjecttaught;

    @FXML
    private JFXComboBox<String> cdfaculty;

    @FXML
    private JFXDatePicker cdclassdate;

    @FXML
    private JFXTimePicker cdclasstime;

    @FXML
    private JFXComboBox<String> cdsemester;

    @FXML
    private JFXComboBox<String> cdpapercode;

    @FXML
    private JFXComboBox<String> cdyear;

    @FXML
    private JFXButton close;

    @FXML
    private JFXButton update;

    @FXML
    private JFXDatePicker date;

    @FXML
    private JFXCheckBox filterbyacadamicyear;

    @FXML
    private JFXComboBox<String> acadamicyear;

    @FXML
    private JFXComboBox<String> cdacadamicyear;

    @FXML
    private JFXCheckBox filterbycoursetype;

    @FXML
    private JFXCheckBox honours;

    @FXML
    private JFXCheckBox pass;

    @FXML
    private TextField department;

    @FXML
    private JFXCheckBox uhonours;

    @FXML
    private JFXCheckBox upass;

    private ClassDetails details;
    private AttendanceService cdao;
    private StudentService sdao;
    private PapersService pdao;
    private LoginService dao;
    private ExceptionDialog dialog;

    private FXMLLoader fxml;

    public UpdateClassDetailsController() {
        fxml = Fxml.getUpdateClassDetailsFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(UpdateClassDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        department.setText(SystemUtils.getDepartment());
        cdao = (AttendanceService) Start.app.getBean("attendanceservice");
        sdao = (StudentService) Start.app.getBean("studentservice");
        pdao = (PapersService) Start.app.getBean("papersservice");
        dao = (LoginService) Start.app.getBean("loginservice");

        cdao.setParent(this);
        dialog = cdao.getEx();

        initFilters();
        initTable();

        populateTable(null);

        close.setOnAction(e -> ((BorderPane) this.getParent()).setCenter(null));
        applyfilter.setOnAction(this::filters);
        refresh.setOnAction(this::populateTable);
        table.setOnMouseClicked(this::tableClick);
        update.setOnAction(this::update);
    }

    private void initFilters() {
        facultyname.disableProperty().bind(filterbyfaculty.selectedProperty().not());
        semester.disableProperty().bind(filterbysemester.selectedProperty().not());
        year.disableProperty().bind(filterbyyear.selectedProperty().not());
        paper.disableProperty().bind(filterbypaper.selectedProperty().not());
        date.disableProperty().bind(filterbydate.selectedProperty().not());

        honours.disableProperty().bind(filterbycoursetype.selectedProperty().not());
        pass.disableProperty().bind(filterbycoursetype.selectedProperty().not());

        acadamicyear.disableProperty().bind(filterbyacadamicyear.selectedProperty().not());

        acadamicyear.getItems().setAll("1st", "2nd", "3rd");
        cdacadamicyear.getItems().setAll("1st", "2nd", "3rd");

        acadamicyear.getSelectionModel().selectedItemProperty().addListener((ol, o, n) -> {
            if (n.equals("1st")) {
                semester.getItems().setAll("1st Semester", "2nd Semester");
            } else if (n.equals("2nd")) {
                semester.getItems().setAll("3rd Semester", "4th Semester");
            } else {
                semester.getItems().setAll("5th Semester", "6th Semester");
            }
        });
        cdacadamicyear.getSelectionModel().selectedItemProperty().addListener((ol, o, n) -> {
            if (n.equals("1st")) {
                cdsemester.getItems().setAll("1st Semester", "2nd Semester");
            } else if (n.equals("2nd")) {
                cdsemester.getItems().setAll("3rd Semester", "4th Semester");
            } else {
                cdsemester.getItems().setAll("5th Semester", "6th Semester");
            }
        });

        List<String> years = sdao.get("select distinct(year) from student order by year", String.class);
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

        uhonours.selectedProperty().addListener((ol, o, n) -> {
            if (uhonours.isSelected()) {
                uhonours.setSelected(true);
                upass.setSelected(false);
            }
        });

        upass.selectedProperty().addListener((ol, o, n) -> {
            if (upass.isSelected()) {
                upass.setSelected(true);
                uhonours.setSelected(false);
            }
        });

        semester.getSelectionModel().selectedItemProperty().addListener((ol, o, n) -> {
            if (n != null || !n.isEmpty()) {
                List<Paper> paperlist = pdao.findByDepartment(SystemUtils.getDepartment());
                List<String> list = paperlist.stream().filter(f -> f.getSemester().equals(n.replace(" Semester", ""))).map(p -> p.getPaperCode()).collect(Collectors.toList());
                paper.getItems().setAll(list);
            }
        });

        cdsemester.getSelectionModel().selectedItemProperty().addListener((ol, o, n) -> {
            String sem = n.replace(" Semester", "");
            List<Paper> papers = pdao.findBySemester(sem);
            cdpapercode.getItems().setAll(papers.stream().map(p -> p.getPaperCode()).collect(Collectors.toList()));
        });
    }

    private void initTable() {
        tfacultyname.setCellValueFactory(new PropertyValueFactory<ClassDetails, String>("facultyName"));
        tsubjecttaught.setCellValueFactory(new PropertyValueFactory<ClassDetails, String>("subjectTaught"));
        tdate.setCellValueFactory(new PropertyValueFactory<ClassDetails, String>("date"));
        ttime.setCellValueFactory(new PropertyValueFactory<ClassDetails, String>("time"));
        tclassid.setCellValueFactory(new PropertyValueFactory<ClassDetails, String>("classId"));
        tsemester.setCellValueFactory(new PropertyValueFactory<ClassDetails, String>("semester"));
        tyear.setCellValueFactory(new PropertyValueFactory<ClassDetails, Integer>("year"));
        tpapercode.setCellValueFactory(new PropertyValueFactory<ClassDetails, String>("paper"));
        tacadamicyear.setCellValueFactory(new PropertyValueFactory<>("acadamicyear"));
        tcoursetype.setCellValueFactory(new PropertyValueFactory<>("coursetype"));
    }

    private void populateTable(ActionEvent evt) {
        Task<List<ClassDetails>> task = new Task<List<ClassDetails>>() {
            @Override
            protected List<ClassDetails> call() throws Exception {
                return cdao.findByDepartment(SystemUtils.getDepartment());
            }

        };
        task.setOnRunning(e -> LoadingController.show(this.getScene()));
        task.setOnFailed(e -> LoadingController.hide());
        task.setOnSucceeded(e -> {
            LoadingController.hide();
            Platform.runLater(() -> {
                try {
                    table.getItems().setAll(task.get());
                } catch (InterruptedException | ExecutionException ex) {
                    table.getItems().clear();
                }
            });
        });
        SystemUtils.getService().execute(task);
    }

    private void filters(ActionEvent evt) {
        List<ClassDetails> list = cdao.findByDepartment(department.getText());

        if (filterbyacadamicyear.isSelected()) {
            list = list.stream().filter(s -> s.getAcadamicyear().equals(acadamicyear.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }
        if (filterbyfaculty.isSelected()) {
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
        if (filterbydate.isSelected()) {
            String d = date.getValue().format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy"));
            list = list.stream().filter(f -> f.getDate().equalsIgnoreCase(d)).collect(Collectors.toList());
        }
        if (filterbycoursetype.isSelected()) {
            if (honours.isSelected()) {
                list = list.stream().filter(s -> s.getCoursetype().equals("Honours")).collect(Collectors.toList());
            }
            if (pass.isSelected()) {
                list = list.stream().filter(s -> s.getCoursetype().equals("Pass")).collect(Collectors.toList());
            }
        }
        table.getItems().setAll(list);
    }

    private void tableClick(MouseEvent evt) {
        details = table.getSelectionModel().getSelectedItem();
        List<String> HODname = Utils.util.getHODUsers(SystemUtils.getDepartment()).stream().map(m -> m.getDetails().getName()).collect(Collectors.toList());
        List<String> fname = Utils.util.getFacultyUsers(SystemUtils.getDepartment()).stream().map(m -> m.getDetails().getName()).collect(Collectors.toList());

        List<String> names = Stream.concat(HODname.stream(), fname.stream()).collect(Collectors.toList());
        cdfaculty.getItems().setAll(names);

        List<String> years = sdao.get("select distinct(year) from student order by year", String.class);
        cdyear.getItems().setAll(years);

        cdfaculty.getSelectionModel().select(details.getFacultyName());
        cdsubjecttaught.setText(details.getSubjectTaught());
        cdacadamicyear.getSelectionModel().select(details.getAcadamicyear());
        cdclassdate.setValue(LocalDate.parse(details.getDate(), DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")));
        cdclasstime.setValue(LocalTime.parse(details.getTime(), DateTimeFormatter.ofPattern("h:m a")));
        cdsemester.getSelectionModel().select(details.getSemester() + " Semester");
        cdyear.getSelectionModel().select("" + details.getYear());
        cdpapercode.getSelectionModel().select(details.getPaper());
        if (details.getCoursetype().equals("Honours")) {
            uhonours.setSelected(true);
        }
        if (details.getCoursetype().equals("Pass")) {
            upass.setSelected(true);
        }
    }

    private void update(ActionEvent evt) {
        try {
            if (details != null) {
                ClassDetails cd = new ClassDetails();

                if (cdfaculty.getSelectionModel().getSelectedIndex() >= 0) {
                    cd.setFacultyName(cdfaculty.getSelectionModel().getSelectedItem());
                } else {
                    throw new Exception("Please Select Faculty");
                }
                cd.setSubjectTaught(cdsubjecttaught.getText());
                cd.setDate(cdclassdate.getValue().format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")));
                cd.setTime(cdclasstime.getValue().format(DateTimeFormatter.ofPattern("hh:mm a")));

                if (cdsemester.getSelectionModel().getSelectedIndex() >= 0) {
                    cd.setSemester(cdsemester.getSelectionModel().getSelectedItem().replace(" Semester", ""));
                } else {
                    throw new Exception("Please Select Semester");
                }
                if (cdyear.getSelectionModel().getSelectedIndex() >= 0) {
                    cd.setYear(Integer.parseInt(cdyear.getSelectionModel().getSelectedItem()));
                } else {
                    throw new Exception("Please Select Year");
                }
                if (cdpapercode.getSelectionModel().getSelectedIndex() >= 0) {
                    cd.setPaper(cdpapercode.getSelectionModel().getSelectedItem());
                } else {
                    throw new Exception("Please Select Paper Code");
                }
                if (cdacadamicyear.getSelectionModel().getSelectedIndex() >= 0) {
                    cd.setAcadamicyear(cdacadamicyear.getSelectionModel().getSelectedItem());
                } else {
                    throw new Exception("Please Select Acadamic Year");
                }
                cd.setDepartment(department.getText());
                if (uhonours.isSelected() || upass.isSelected()) {
                    if (uhonours.isSelected()) {
                        cd.setCoursetype("Honours");
                    }
                    if (upass.isSelected()) {
                        cd.setCoursetype("Pass");
                    }
                } else {
                    throw new Exception("Please Select CourseType");
                }

                cd.setDailyStats(details.getDailyStats());
                cd.setAttendance(details.getAttendance());
                cd.setClassId(details.getClassId());

                Set<ConstraintViolation<ClassDetails>> validate = ValidationUtils.getValidator().validate(cd);
                if (validate.isEmpty()) {
                    boolean b1 = cdao.updateAttendance(cd);

                    String id = SystemUtils.getDepartmentCode() + "/" + cdclassdate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "@" + cdclasstime.getValue().format(DateTimeFormatter.ofPattern("hh:mm"))
                            + "#" + cdacadamicyear.getSelectionModel().getSelectedItem() + "__" + cdsemester.getSelectionModel().getSelectedItem().replace(" Semester", "") + "_" + cdyear.getSelectionModel().getSelectedItem() + "&" + cd.getCoursetype().charAt(0);
                    cd.setClassId(id);

                    boolean b2 = cdao.updateClassId(details.getClassId(), cd.getClassId());
                    if (b1 && b2) {
                        dialog.showSuccess(this, "Class Details Update", "Class Details Updated Successfully");
                    } else {
                        dialog.showError(this, "Class Details Update", "Class Details Updation Failed");
                    }
                } else {
                    validate.stream().forEach(c -> dialog.showError(this, "Class Details Update", c.getMessage()));
                }
            }

        } catch (Exception e) {
            dialog.showError(this, "Class Details Update", e.getLocalizedMessage());
        }
    }

}
