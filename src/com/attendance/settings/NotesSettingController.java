/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings;

import com.attendance.main.Start;
import com.attendance.notes.controller.NotesDashboardController;
import com.attendance.notes.controller.NotesNodeController;
import com.attendance.notes.controller.NotesSearchController;
import com.attendance.notes.dao.NotesDao;
import com.attendance.notes.model.Notes;
import com.attendance.notes.service.NotesService;
import com.attendance.util.ExceptionDialog;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author Programmer Hrishav
 */
public class NotesSettingController extends AnchorPane {

    @FXML
    private JFXButton close;

    @FXML
    private JFXCheckBox filterbyname;

    @FXML
    private JFXComboBox<String> selectuploader;

    @FXML
    private JFXCheckBox filterbydate;

    @FXML
    private JFXDatePicker enterdate;

    @FXML
    private JFXButton apply;

    @FXML
    private JFXButton ascending;

    @FXML
    private JFXButton descending;

    @FXML
    private JFXButton refresh;

    @FXML
    private JFXButton searchbyfile;

    @FXML
    private JFXButton selectall;

    @FXML
    private JFXButton searchbyimage;

    @FXML
    private JFXButton uncheck;

    @FXML
    private Label date;

    @FXML
    private Label department;

    @FXML
    private Label facultyname;

    @FXML
    private ImageView image;

    @FXML
    private Label upload;

    @FXML
    private Label size;

    @FXML
    private Label name;

    @FXML
    private HBox list;

    @FXML
    private JFXButton delete;

    @FXML
    private TextField searchpath;

    @FXML
    private JFXButton search;

    private FXMLLoader fxml;
    private Parent parent;
    private NotesService dao;
    private ExceptionDialog dialog;

    private File file;
    private DecimalFormat dec;

    public NotesSettingController(Parent parent) {
        this.parent = parent;
        fxml = Fxml.getNotesSettingsFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(NotesSettingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao = (NotesService) Start.app.getBean("notesservice");
        dao.setParent(this);
        dialog = dao.getEx();

        dec = new DecimalFormat("#.##");
        dec.setRoundingMode(RoundingMode.CEILING);

        List<String> HODname = Utils.util.getHODUsers(SystemUtils.getDepartment()).stream().map(m -> m.getDetails().getName()).collect(Collectors.toList());
        List<String> facultyname = Utils.util.getFacultyUsers(SystemUtils.getDepartment()).stream().map(m -> m.getDetails().getName()).collect(Collectors.toList());

        List<String> names = Stream.concat(HODname.stream(), facultyname.stream()).collect(Collectors.toList());
        selectuploader.getItems().setAll(names);

        loadData(null);

        initDetails();

        selectuploader.disableProperty().bind(filterbyname.selectedProperty().not());
        enterdate.disableProperty().bind(filterbydate.selectedProperty().not());

        close.setOnAction(evt -> SwitchRoot.switchRoot(Start.st, parent));
        refresh.setOnAction(this::loadData);

        ascending.setOnAction(this::sortAscending);
        descending.setOnAction(this::sortDescending);

        apply.setOnAction(this::apply);

        search.setOnAction(this::fileNameSearch);
        searchbyimage.setOnAction(this::imageSearch);
        searchbyfile.setOnAction(this::fileSearch);
        selectall.setOnAction(this::select);
        uncheck.setOnAction(this::deselect);

        delete.setOnAction(this::delete);

    }

    private void loadData(ActionEvent evt) {
        List<Notes> data = dao.findByDepartment(SystemUtils.getDepartment());
        List<NotesNodeController> collect = data.stream().map(NotesNodeController::new).collect(Collectors.toList());
        list.getChildren().setAll(collect);
    }

    private void initDetails() {
        List<NotesNodeController> collect = list.getChildren().stream().map(m -> (NotesNodeController) m).collect(Collectors.toList());
        collect.stream().forEach(c -> {
            c.getSelect().selectedProperty().addListener((ol, o, n) -> {
                if (n) {
                    Notes notes = c.getNotes();
                    name.setText(notes.getFileName());
                    upload.setText(notes.getFacultyName());
                    date.setText(notes.getUploadDate());
                    size.setText(dec.format(notes.getFileSize()) + " MB");
                    String ext = notes.getFileName().substring(notes.getFileName().lastIndexOf(".") + 1);

                    switch (ext) {
                        case "jpg":
                        case "png":
                        case "gif":
                            image.setImage(SystemUtils.getICONS().get("image"));
                            break;
                        case "pdf":
                            image.setImage(SystemUtils.getICONS().get("pdf"));
                            break;
                        case "docx":
                        case "doc":
                            image.setImage(SystemUtils.getICONS().get("doc"));
                            break;
                        default:
                            image.setImage(SystemUtils.getICONS().get("file"));
                    }
                }
            });
        });
    }

    private void sortAscending(ActionEvent evt) {
        List<Notes> data = dao.sortBydate("asc");
        List<NotesNodeController> collect = data.stream().filter(f -> f.getDepartment().equals(SystemUtils.getDepartment())).map(NotesNodeController::new).collect(Collectors.toList());
        list.getChildren().setAll(collect);
    }

    private void sortDescending(ActionEvent evt) {
        List<Notes> data = dao.sortBydate("desc");
        List<NotesNodeController> collect = data.stream().filter(f -> f.getDepartment().equals(SystemUtils.getDepartment())).map(NotesNodeController::new).collect(Collectors.toList());
        list.getChildren().setAll(collect);
    }

    private void apply(ActionEvent evt) {
        List<Notes> data = dao.findByDepartment(SystemUtils.getDepartment());

        if (filterbyname.isSelected()) {
            data = data.stream().filter(f -> f.getFacultyName().equals(selectuploader.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }
        if (filterbydate.isSelected()) {
            data = data.stream().filter(f -> f.getUploadDate().equals(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(enterdate.getValue()))).collect(Collectors.toList());
        }

        List<NotesNodeController> collect = data.stream().map(NotesNodeController::new).collect(Collectors.toList());
        list.getChildren().setAll(collect);
    }

    private void fileNameSearch(ActionEvent evt) {
        List<NotesNodeController> collect = list.getChildren().stream().map(m -> (NotesNodeController) m).filter(p -> p.getNotes().getFileName().contains(searchpath.getText())).collect(Collectors.toList());
        list.getChildren().setAll(collect);
    }

    private void imageSearch(ActionEvent evt) {
        ObservableList<Node> children = list.getChildren();
        List<NotesNodeController> collect = children.stream().map(m -> (NotesNodeController) m).collect(Collectors.toList());
        collect = collect.stream().filter(f -> {
            String name = f.getNotes().getFileName();
            String ext = name.substring(name.lastIndexOf(".") + 1);
            return (ext.equals("jpg") || ext.equals("png") || ext.equals("gif"));
        }).collect(Collectors.toList());
        list.getChildren().setAll(collect);
    }

    private void fileSearch(ActionEvent evt) {
        ObservableList<Node> children = list.getChildren();
        List<NotesNodeController> collect = children.stream().map(m -> (NotesNodeController) m).collect(Collectors.toList());
        collect = collect.stream().filter(f -> {
            String name = f.getNotes().getFileName();
            String ext = name.substring(name.lastIndexOf(".") + 1);
            return !(ext.equals("jpg") || ext.equals("png") || ext.equals("gif"));
        }).collect(Collectors.toList());
        list.getChildren().setAll(collect);
    }

    private void select(ActionEvent evt) {
        ObservableList<Node> children = list.getChildren();
        List<NotesNodeController> collect = children.stream().map(m -> (NotesNodeController) m).collect(Collectors.toList());
        collect.stream().forEach(c -> c.setSelected(true));
        list.getChildren().setAll(collect);
    }

    private void deselect(ActionEvent evt) {
        ObservableList<Node> children = list.getChildren();
        List<NotesNodeController> collect = children.stream().map(m -> (NotesNodeController) m).collect(Collectors.toList());
        collect.stream().forEach(c -> c.setSelected(false));
        list.getChildren().setAll(collect);
    }

    private void delete(ActionEvent evt) {
        List<NotesNodeController> nodes = list.getChildren().stream().map(f -> (NotesNodeController) f).collect(Collectors.toList());
        nodes = nodes.stream().filter(f -> f.isSelected()).collect(Collectors.toList());
        nodes.stream().forEach(c -> dao.deleteNotes(c.getNotes()));

        dialog.showSuccess(this, "Delete Notes", "Notes Deleted Successfully");

    }
}
