/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings.sub;

import com.attendance.main.Start;
import com.attendance.student.dao.StudentDao;
import com.attendance.student.model.Student;
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
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
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
public class DeleteStudentController extends AnchorPane {

    @FXML
    private Label sem;

    @FXML
    private Label department;

    @FXML
    private JFXCheckBox filterbyid;

    @FXML
    private JFXCheckBox filterbyname;

    @FXML
    private JFXCheckBox filterbyyear;

    @FXML
    private JFXCheckBox filterbygender;

    @FXML
    private TextField studentid;

    @FXML
    private TextField studentname;

    @FXML
    private JFXComboBox<String> studentgender;

    @FXML
    private JFXComboBox<String> studentyear;

    @FXML
    private JFXButton search;

    @FXML
    private JFXButton clear;

    @FXML
    private TableView<Student> table;

    @FXML
    private TableColumn<Student, String> name;

    @FXML
    private TableColumn<Student, Integer> year;

    @FXML
    private TextField sname;

    @FXML
    private TextField syear;

    @FXML
    private TextField scontactnumber;

    @FXML
    private TextField srollno;

    @FXML
    private TextField ssem;

    @FXML
    private TextField sgender;

    @FXML
    private JFXButton deletestudent;

    @FXML
    private JFXButton refresh;

    @FXML
    private JFXButton cancel;

    @FXML
    private JFXCheckBox filterbycourse;

    @FXML
    private JFXCheckBox honours;

    @FXML
    private JFXCheckBox pass;

    @FXML
    private TextField coursetype;

    private final String acadamicyear;

    private FXMLLoader fxml;
    private StudentDao studentdao;

    private Student student;

    public DeleteStudentController(String acadamicyear) {
        this.acadamicyear = acadamicyear;
        fxml = Fxml.getDeleteStudentFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(DeleteStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        department.setText("Department: "+SystemUtils.getDepartment());
        studentdao = (StudentDao) Start.app.getBean("studentregistration");
        sem.setText(acadamicyear + " Year");
        student = new Student();
        //initFilters();
        initTable();
        populateTable(null);

        search.setOnAction(this::search);
        clear.setOnAction(this::clear);
        refresh.setOnAction(this::refresh);
        table.setOnMouseClicked(this::tableClick);
        cancel.setOnAction(this::cancel);
        deletestudent.setOnAction(this::deleteStudent);
    }

    private void initFilters() {
        studentname.disableProperty().bind(filterbyname.selectedProperty().not());
        studentid.disableProperty().bind(filterbyid.selectedProperty().not());
        studentyear.disableProperty().bind(filterbyyear.selectedProperty().not());
        studentgender.disableProperty().bind(filterbygender.selectedProperty().not());

        honours.disableProperty().bind(filterbycourse.selectedProperty().not());
        pass.disableProperty().bind(filterbycourse.selectedProperty().not());

        studentgender.getItems().addAll("Male", "Female");

        List<String> years = studentdao.get("select distinct(year) from student order by year", String.class);
        studentyear.getItems().setAll(years);

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
    }

    private void initTable() {
        name.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        year.setCellValueFactory(new PropertyValueFactory<Student, Integer>("year"));
    }

    private void populateTable(ActionEvent evt) {
        List<Student> list = studentdao.findByAcadamicYear(acadamicyear);
        list = list.stream().filter(p -> p.getDepartment().equals(SystemUtils.getDepartment())).collect(Collectors.toList());

        table.getItems().setAll(list);
    }

    private void search(ActionEvent evt) {
        List<Student> list = studentdao.findByAcadamicYear(acadamicyear);
        list = list.stream().filter(p -> p.getDepartment().equals(SystemUtils.getDepartment())).collect(Collectors.toList());

        if (filterbyid.isSelected()) {
            list = list.stream().filter(s -> s.getId().startsWith(studentid.getText())).collect(Collectors.toList());
        }
        if (filterbyname.isSelected()) {
            list = list.stream().filter(s -> s.getName().contains(studentname.getText())).collect(Collectors.toList());
        }
        if (filterbyyear.isSelected()) {
            list = list.stream().filter(s -> s.getYear() == Integer.parseInt(studentyear.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }
        if (filterbygender.isSelected()) {
            list = list.stream().filter(s -> s.getGender().equalsIgnoreCase(studentgender.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }

        if (filterbycourse.isSelected()) {
            if (honours.isSelected()) {
                list = list.stream().filter(s -> s.getCourseType().equalsIgnoreCase("Honours")).collect(Collectors.toList());
            }
            if (pass.isSelected()) {
                list = list.stream().filter(s -> s.getCourseType().equalsIgnoreCase("Pass")).collect(Collectors.toList());
            }
        }

        table.getItems().setAll(list);
    }

    private void clear(ActionEvent evt) {
        filterbygender.setSelected(false);
        filterbyid.setSelected(false);
        filterbyname.setSelected(false);
        filterbyyear.setSelected(false);
        filterbycourse.setSelected(false);

        sname.setText("");
        scontactnumber.setText("");
        sgender.setText("");
        ssem.setText("");
        syear.setText("");
        srollno.setText("");
        coursetype.setText("");
    }

    private void refresh(ActionEvent evt) {
        populateTable(evt);
    }

    private void tableClick(MouseEvent evt) {
        student = table.getSelectionModel().getSelectedItem();
        sname.setText(student.getName());
        srollno.setText("" + student.getRollno());
        syear.setText("" + student.getYear());
        ssem.setText(student.getAcadamicyear());
        scontactnumber.setText(student.getContact());
        sgender.setText(student.getGender());
        coursetype.setText(student.getCourseType());

    }

    private void cancel(ActionEvent evt) {
        ((BorderPane) this.getParent()).setCenter(null);
    }

    private void deleteStudent(ActionEvent evt) {
        if (student != null) {
            boolean del = studentdao.deleteStudent(student);
            if (del == true) {
                Alert al = new Alert(AlertType.INFORMATION);
                al.setHeaderText("Delete Student");
                al.setContentText("Student Deleted Successfully");
                al.initOwner(((Node) evt.getSource()).getScene().getWindow());
                al.initModality(Modality.WINDOW_MODAL);
                al.show();
            } else {
                Alert al = new Alert(AlertType.ERROR);
                al.setHeaderText("Delete Student");
                al.setContentText("Student Deletion Failed");
                al.initOwner(((Node) evt.getSource()).getScene().getWindow());
                al.initModality(Modality.WINDOW_MODAL);
                al.show();
            }
        } else {
            Alert al = new Alert(AlertType.ERROR);
            al.setHeaderText("Delete Student");
            al.setContentText("Please Select A Row From The Table");
            al.initOwner(((Node) evt.getSource()).getScene().getWindow());
            al.initModality(Modality.WINDOW_MODAL);
            al.show();
        }
    }
}
