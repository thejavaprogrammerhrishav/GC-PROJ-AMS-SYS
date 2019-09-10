/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.notes.controller;

import com.attendance.main.Start;
import com.attendance.notes.dao.NotesDao;
import com.attendance.notes.model.Notes;
import com.attendance.util.Fxml;
import com.attendance.util.Message;
import com.attendance.util.MessageUtil;
import com.attendance.util.SwitchRoot;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author Programmer Hrishav
 */
public class UploadNotesController extends AnchorPane {

    @FXML
    private TableView<Notes> table;

    @FXML
    private TableColumn<Notes, String> facultyname;

    @FXML
    private TableColumn<Notes, String> filename;

    @FXML
    private TableColumn<Notes, Double> filesize;

    @FXML
    private TableColumn<Notes, String> uploaddate;

    @FXML
    private JFXCheckBox searchbyfilename;

    @FXML
    private TextField sfilename;

    @FXML
    private JFXCheckBox sortbydate;

    @FXML
    private JFXCheckBox sortascending;

    @FXML
    private JFXCheckBox sortdescending;

    @FXML
    private JFXCheckBox searchbyfacultyname;

    @FXML
    private TextField sfacultyname;

    @FXML
    private JFXCheckBox sortbyfilesize;

    @FXML
    private JFXCheckBox sfasc;

    @FXML
    private JFXCheckBox sfdesc;

    @FXML
    private JFXButton cancel;

    @FXML
    private JFXButton upload;

    @FXML
    private JFXButton refresh;

    @FXML
    private JFXButton applyfilter;

    @FXML
    private TextField uploadfilepath;

    @FXML
    private JFXButton browse;

    @FXML
    private JFXButton sort;

    @FXML
    private JFXCheckBox searchbydate;

    @FXML
    private JFXDatePicker sdate;

    private FXMLLoader fxml;
    private final Parent p;

    private NotesDao dao;
    
    private File file;
    private FileInputStream fin;
    private Notes notes;
    
    private FileChooser ch;
    private String name;
    private DecimalFormat dec;

    public UploadNotesController(Parent p,String name) {
        this.p = p;
        this.name=name;
        fxml = Fxml.getUploadFilesFXML();
        fxml.setController(this);
        fxml.setRoot(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(UploadNotesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao = (NotesDao) Start.app.getBean("notes");
        dec=new DecimalFormat("#.##");
        dec.setRoundingMode(RoundingMode.CEILING);
        initFilters();
        initTable();
        populateTable(null);

        cancel.setOnAction(e -> SwitchRoot.switchRoot(Start.st, p));
        applyfilter.setOnAction(this::applyFilters);
        refresh.setOnAction(this::populateTable);
        sort.setOnAction(this::sortList);
        
        browse.setOnAction(this::browseFile);
        upload.setOnAction(this::uploadFile);
    }

    private void initFilters() {
        sfacultyname.disableProperty().bind(searchbyfacultyname.selectedProperty().not());
        sortascending.disableProperty().bind(sortbydate.selectedProperty().not());
        sortdescending.disableProperty().bind(sortbydate.selectedProperty().not());
        sfilename.disableProperty().bind(searchbyfilename.selectedProperty().not());
        sdate.disableProperty().bind(searchbydate.selectedProperty().not());
        sfasc.disableProperty().bind(sortbyfilesize.selectedProperty().not());
        sfdesc.disableProperty().bind(sortbyfilesize.selectedProperty().not());

        sortascending.selectedProperty().addListener((ol, o, n) -> {
            if (sortascending.isSelected()) {
                sortdescending.setSelected(false);
            }
        });

        sortdescending.selectedProperty().addListener((ol, o, n) -> {
            if (sortdescending.isSelected()) {
                sortascending.setSelected(false);
            }
        });

        sfasc.selectedProperty().addListener((ol, o, n) -> {
            if (sfasc.isSelected()) {
                sfdesc.setSelected(false);
            }
        });

        sfdesc.selectedProperty().addListener((ol, o, n) -> {
            if (sfdesc.isSelected()) {
                sfasc.setSelected(false);
            }
        });
    }

    private void initTable() {
        facultyname.setCellValueFactory(new PropertyValueFactory<>("facultyName"));
        filename.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        filesize.setCellValueFactory(new PropertyValueFactory<>("fileSize"));
        uploaddate.setCellValueFactory(new PropertyValueFactory<>("uploadDate"));
    }

    private void populateTable(ActionEvent evt) {
        List<Notes> all = dao.findAll();
        table.getItems().setAll(all);
    }

    private void applyFilters(ActionEvent evt) {
        List<Notes> filter = table.getItems();
        if (searchbyfacultyname.isSelected()) {
            filter = filter.stream().filter(n -> n.getFacultyName().startsWith(sfacultyname.getText())).collect(Collectors.toList());
        }
        if (searchbyfilename.isSelected()) {
            filter = filter.stream().filter(n -> n.getFileName().startsWith(sfilename.getText())).collect(Collectors.toList());
        }
        if (searchbydate.isSelected()) {
            filter = filter.stream().filter(n -> n.getUploadDate().equals(sdate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))).collect(Collectors.toList());
        }
        table.getItems().setAll(filter);
    }

    private void sortList(ActionEvent evt) {
        List<Notes> all = null;
        if (sortbydate.isSelected() && sortbyfilesize.isSelected()) {
            MessageUtil.showError(Message.ERROR, "Filtering Upload Files List", "Please select only one sorting at a time", ((Node) evt.getSource()).getScene().getWindow());
        } else if (sortbydate.isSelected()) {
            if (sortascending.isSelected()) {
                all = dao.sortBydate("asc");
            }
            if (sortdescending.isSelected()) {
                all = dao.sortBydate("desc");
            }
        } else if (sortbyfilesize.isSelected()) {
            if (sfasc.isSelected()) {
                all = dao.sortByFileSize("asc");
            }
            if (sfdesc.isSelected()) {
                all = dao.sortByFileSize("desc");
            }
        } else {
            all = dao.findAll();
        }
        table.getItems().setAll(all);
    }

    private void browseFile(ActionEvent evt){
        ch=new FileChooser();
        ch.setTitle("Upload Files");
        file = ch.showOpenDialog(((Node)evt.getSource()).getScene().getWindow());
        uploadfilepath.setText(file.getAbsolutePath());
    }
    
    private void uploadFile(ActionEvent evt){
        try {
            fin=new FileInputStream(file.getAbsolutePath());
            byte[] data=new byte[(int)file.length()];
            fin.read(data);
            fin.close();
            
            double len=(double)file.length()/(1024*1024);
            notes=new Notes();
            notes.setFacultyName(name);
            notes.setFile(data);
            notes.setFileName(file.getName());
            notes.setFileSize(Double.parseDouble(dec.format(len)));
            notes.setUploadDate(DateTime.now().toString(DateTimeFormat.forPattern("dd-MM-yyyy")));
            
            int id=dao.save(notes);
            
            MessageUtil.showInformation(Message.INFORMATION, "Upload Files / Notes", "File Uploaded Successfully\nFile Id:  "+id, ((Node)evt.getSource()).getScene().getWindow());
        } catch (IOException ex) {
            Logger.getLogger(UploadNotesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
