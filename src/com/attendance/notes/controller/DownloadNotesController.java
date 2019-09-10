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
import java.io.FileOutputStream;
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
import javafx.stage.DirectoryChooser;

/**
 *
 * @author Programmer Hrishav
 */
public class DownloadNotesController extends AnchorPane{
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
    private JFXButton download;

    @FXML
    private JFXButton refresh;

    @FXML
    private JFXButton applyfilter;

    @FXML
    private TextField downloadfilepath;

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
    private FileOutputStream fout;
    private Notes notes;
    
    private DirectoryChooser ch;
    private DecimalFormat dec;

    public DownloadNotesController(Parent p) {
        this.p = p;
        fxml = Fxml.getDownloadFilesFXML();
        fxml.setController(this);
        fxml.setRoot(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(DownloadNotesController.class.getName()).log(Level.SEVERE, null, ex);
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
        download.setOnAction(this::downloadFile);
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
        ch=new DirectoryChooser();
        ch.setTitle("Download Files");
        file = ch.showDialog(((Node)evt.getSource()).getScene().getWindow());
        downloadfilepath.setText(file.getAbsolutePath());
    }
    
    private void downloadFile(ActionEvent evt){
        if(table.getSelectionModel().getSelectedIndex()<0){
            MessageUtil.showError(Message.ERROR, "Download Notes", "Please select a row from the table", ((Node)evt.getSource()).getScene().getWindow());
        }
        else{
            try {
                Notes note=table.getSelectionModel().getSelectedItem();
                downloadfilepath.setText(downloadfilepath.getText()+note.getFileName());
                fout=new FileOutputStream(downloadfilepath.getText());
                fout.write(note.getFile());
                fout.flush();
                fout.close();
                MessageUtil.showInformation(Message.INFORMATION, "Download Files", "File Successfully Downloaded\nFile Path:  "+downloadfilepath.getText(), ((Node)evt.getSource()).getScene().getWindow());
            } catch (IOException ex) {
                Logger.getLogger(DownloadNotesController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
