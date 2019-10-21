/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.notes.controller;

import com.attendance.main.Start;
import com.attendance.notes.dao.NotesDao;
import com.attendance.notes.model.Notes;
import com.attendance.personal.model.PersonalDetails;
import com.attendance.util.Fxml;
import com.attendance.util.Message;
import com.attendance.util.MessageUtil;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.attendance.util.Utils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author pc
 */
public class NotesDashboardController extends AnchorPane {

    @FXML
    private JFXCheckBox searchbyname;

    @FXML
    private JFXComboBox<String> selectuploader;

    @FXML
    private JFXCheckBox searchbydate;

    @FXML
    private JFXDatePicker enterdate;

    @FXML
    private JFXButton search;

    @FXML
    private JFXButton ascending;

    @FXML
    private JFXButton descending;

    @FXML
    private TextField downloadpath;

    @FXML
    private JFXButton dbrowse;

    @FXML
    private JFXButton download;

    @FXML
    private Label date;

    @FXML
    private Label department;

    @FXML
    private Label facultyname;

    @FXML
    private Label totaldocument;

    @FXML
    private Label totalimages;

    @FXML
    private TextField uploadpath;

    @FXML
    private JFXButton ubrowse;

    @FXML
    private JFXButton upload;

    @FXML
    private HBox list;

    @FXML
    private JFXButton backup;

    @FXML
    private JFXButton close;

    @FXML
    private JFXButton refresh;

    private Parent parent;
    private String facultyName;

    private FXMLLoader fxml;
    private NotesDao dao;
    
    private File file;
    
    private DecimalFormat dec;

    public NotesDashboardController(Parent parent, String facultyName) {
        this.parent = parent;
        this.facultyName = facultyName;

        fxml = Fxml.getNotesDashboardFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(NotesDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void initialize() {
        dao = (NotesDao) Start.app.getBean("notes");

        dec=new DecimalFormat("#.##");
        dec.setRoundingMode(RoundingMode.CEILING);
        
        facultyname.setText(facultyName);
        date.setText(DateTime.now().toString(DateTimeFormat.forPattern("EEEEE, dd MMMMM yyyy")));
        department.setText(SystemUtils.getDepartment());

        List<PersonalDetails> details = Utils.util.getDetails(SystemUtils.getDepartment());
        selectuploader.getItems().setAll(details.stream().map(f -> f.getName()).collect(Collectors.toList()));

        selectuploader.disableProperty().bind(searchbyname.selectedProperty().not());
        enterdate.disableProperty().bind(searchbydate.selectedProperty().not());

        loadData(null);

        close.setOnAction(evt -> SwitchRoot.switchRoot(Start.st, parent));
        refresh.setOnAction(this::loadData);
        upload.setOnAction(this::uploadFile);
        
        ubrowse.setOnAction(evt->{
            file = browse("Upload File", "Open");
            uploadpath.setText(file.getAbsolutePath());
        });
        
        dbrowse.setOnAction(evt->{
            file = browse("Download File", "Save");
            downloadpath.setText(file.getAbsolutePath());
        });
    }

    private void loadData(ActionEvent evt) {
        List<Notes> data = dao.findByDepartment(SystemUtils.getDepartment());
        Long images = data.stream().filter(f -> {
            try{
                String name = f.getFileName();
            String ext = name.split(".")[1];
            return ext.equals("jpg") || ext.equals("png") || ext.equals("gif");
            }catch(ArrayIndexOutOfBoundsException e){
                return false;
            }
        }).collect(Collectors.counting());

        Long docs = data.stream().filter(f -> {
            try{
                String name = f.getFileName();
            String ext = name.split(".")[1];
            return !(ext.equals("jpg") || ext.equals("png") || ext.equals("gif"));
            }catch(ArrayIndexOutOfBoundsException e){
                return false;
            }
        }).collect(Collectors.counting());

        List<NotesNodeController> collect = data.stream().map(NotesNodeController::new).collect(Collectors.toList());
        list.getChildren().setAll(collect);

        totalimages.setText("" + images.longValue());
        totaldocument.setText("" + docs.longValue());
    }

    private File browse(String title, String mode) {
        FileChooser fc = new FileChooser();
        ExtensionFilter file = new ExtensionFilter("All Files", "*.*");
        ExtensionFilter image = new ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif");
        ExtensionFilter pdf = new ExtensionFilter("Pdf Files", "*.pdf");
        ExtensionFilter doc = new ExtensionFilter("Doc Files", "*.doc", "*.docx");

        fc.getExtensionFilters().addAll(file, image, pdf, doc);

        fc.setTitle(title);

        if (mode.equals("Save")) {
            return fc.showSaveDialog(this.getScene().getWindow());
        } else if (mode.equals("Open")) {
            return fc.showOpenDialog(this.getScene().getWindow());
        } else {
            return null;
        }
    }

    private void uploadFile(ActionEvent evt) {
        FileInputStream fin = null;
        try {
               Notes upload = new Notes();
            upload.setDepartment(SystemUtils.getDepartment());
            upload.setFacultyName(facultyName);
            
            fin = new FileInputStream(file.getAbsolutePath());
            byte[] b = new byte[(int)file.length()];
            fin.read(b);
            fin.close();
            upload.setFile(b);
            
            upload.setFileName(file.getName());
            
            double d = (double) file.length() / (1024 * 1024);
            upload.setFileSize(Double.parseDouble(dec.format(d)));
            upload.setUploadDate(DateTime.now().toString(DateTimeFormat.forPattern("dd-MM-yyyy")));
            Integer save = dao.save(upload);
            if (save == null) {
                MessageUtil.showError(Message.ERROR, "Upload File", "File Uploading Failed", this.getScene().getWindow());
            } else {
                MessageUtil.showError(Message.INFORMATION, "Upload File", "File Uploaded Successfully", this.getScene().getWindow());
            }
        } catch (IOException ex) {
            Logger.getLogger(NotesDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fin.close();
            } catch (IOException ex) {
                Logger.getLogger(NotesDashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
