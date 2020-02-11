/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.notes.controller;

import com.attendance.main.Start;
import com.attendance.notes.dao.NotesDao;
import com.attendance.notes.model.Notes;
import com.attendance.notes.service.NotesService;
import com.attendance.settings.sub.LoadingController;
import com.attendance.util.ExceptionDialog;
import com.attendance.util.Fxml;
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
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
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

    @FXML
    private JFXButton searchbyimage;

    @FXML
    private JFXButton searchbyfile;

    @FXML
    private JFXButton selectall;

    @FXML
    private JFXButton searchbyfilename;

    @FXML
    private JFXButton uncheck;

    private Parent parent;
    private String facultyName;
    private ExceptionDialog dialog;

    private FXMLLoader fxml;
    private NotesService dao;

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
        dao = (NotesService) Start.app.getBean("notesservice");
        dao.setParent(this);
        dialog = dao.getEx();

        dec = new DecimalFormat("#.##");
        dec.setRoundingMode(RoundingMode.CEILING);

        facultyname.setText(facultyName);
        date.setText(DateTime.now().toString(DateTimeFormat.forPattern("EEEEE, dd MMMMM yyyy")));
        department.setText(SystemUtils.getDepartment());

        List<String> HODname = Utils.util.getHODUsers(SystemUtils.getDepartment()).stream().map(m -> m.getDetails().getName()).collect(Collectors.toList());
        List<String> facultyname = Utils.util.getFacultyUsers(SystemUtils.getDepartment()).stream().map(m -> m.getDetails().getName()).collect(Collectors.toList());

        List<String> names = Stream.concat(HODname.stream(), facultyname.stream()).collect(Collectors.toList());
        selectuploader.getItems().setAll(names);

        selectuploader.disableProperty().bind(searchbyname.selectedProperty().not());
        enterdate.disableProperty().bind(searchbydate.selectedProperty().not());

        loadData(null);

        close.setOnAction(evt -> SwitchRoot.switchRoot(Start.st, parent));
        refresh.setOnAction(this::loadData);
        upload.setOnAction(this::uploadFile);

        ubrowse.setOnAction(evt -> {
            file = browse("Upload File", "Open");
            uploadpath.setText(file.getAbsolutePath());
        });

        dbrowse.setOnAction(evt -> {
            file = directory("Download File");
            downloadpath.setText(file.getAbsolutePath());
        });

        download.setOnAction(this::downloadFile);
        backup.setOnAction(this::backup);
        ascending.setOnAction(this::sortAscending);
        descending.setOnAction(this::sortDescending);

        search.setOnAction(this::search);

        searchbyfilename.setOnAction(this::fileNameSearch);
        searchbyimage.setOnAction(this::imageSearch);
        searchbyfile.setOnAction(this::fileSearch);
        selectall.setOnAction(this::select);
        uncheck.setOnAction(this::deselect);
    }

    private void loadData(ActionEvent evt) {
        Task<List<NotesNodeController>> task = new Task<List<NotesNodeController>>() {
            @Override
            protected List<NotesNodeController> call() throws Exception {
                List<Notes> data = dao.findByDepartment(SystemUtils.getDepartment());
                Long images = data.stream().filter(f -> {
                    try {
                        String name = f.getFileName();
                        String ext = name.substring(name.lastIndexOf(".") + 1);
                        return ext.equals("jpg") || ext.equals("png") || ext.equals("gif");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        return false;
                    }
                }).collect(Collectors.counting());

                Long docs = data.stream().filter(f -> {
                    try {
                        String name = f.getFileName();
                        String ext = name.substring(name.lastIndexOf(".") + 1);
                        return !(ext.equals("jpg") || ext.equals("png") || ext.equals("gif"));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        return false;
                    }
                }).collect(Collectors.counting());

                Platform.runLater(()->totalimages.setText("" + images.longValue()));
                Platform.runLater(()->totaldocument.setText("" + docs.longValue()));

                List<NotesNodeController> collect = data.stream().map(NotesNodeController::new).collect(Collectors.toList());
                return collect;
            }
        };

        task.setOnRunning(e -> LoadingController.show(this.getScene()));
        task.setOnSucceeded(e -> {
            try {
                list.getChildren().clear();
                Thread.sleep(700);
                list.getChildren().setAll(task.get());
                LoadingController.hide();
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(NotesDashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        SystemUtils.getService().execute(task);
    }

    private File browse(String title, String mode) {
        File selected = null;
        FileChooser fc = new FileChooser();
        ExtensionFilter file = new ExtensionFilter("All Files", "*.*");
        ExtensionFilter image = new ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif");
        ExtensionFilter pdf = new ExtensionFilter("Pdf Files", "*.pdf");
        ExtensionFilter doc = new ExtensionFilter("Doc Files", "*.doc", "*.docx");

        fc.getExtensionFilters().addAll(file, image, pdf, doc);
        fc.setTitle(title);

        if (mode.equals("Save")) {
            selected = fc.showSaveDialog(this.getScene().getWindow());
        } else if (mode.equals("Open")) {
            selected = fc.showOpenDialog(this.getScene().getWindow());
        } else {
            return selected = null;
        }
        return selected;

    }

    private File directory(String title) {
        DirectoryChooser ch = new DirectoryChooser();
        ch.setInitialDirectory(new File(System.getProperty("user.home") + "\\Downloads"));
        ch.setTitle(title);
        return ch.showDialog(this.getScene().getWindow());
    }

    private void uploadFile(ActionEvent evt) {
        FileInputStream fin = null;
        try {
            if (file != null) {
                Notes upload = new Notes();
                upload.setDepartment(SystemUtils.getDepartment());
                upload.setFacultyName(facultyName);

                fin = new FileInputStream(file.getAbsolutePath());
                byte[] b = new byte[(int) file.length()];
                fin.read(b);
                fin.close();
                upload.setFile(b);

                upload.setFileName(file.getName());

                double d = (double) file.length() / (1024 * 1024);
                upload.setFileSize(Double.parseDouble(dec.format(d)));
                upload.setUploadDate(DateTime.now().toString(DateTimeFormat.forPattern("dd-MM-yyyy")));
                Integer save = dao.saveNotes(upload);
                if (save == null) {
                    dialog.showError(this, "Upload File", "File Upload Failed");
                } else {
                    dialog.showSuccess(this, "Upload File", "File Uploadation Failed");
                }
            } else {
                dialog.showError(this, "Upload File", "No File Selected");
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

    private void downloadFile(ActionEvent evt) {
        List<NotesNodeController> nodes = list.getChildren().stream().map(f -> (NotesNodeController) f).collect(Collectors.toList());
        nodes = nodes.stream().filter(f -> f.isSelected()).collect(Collectors.toList());
        nodes.stream().forEach(c -> {
            try {
                FileOutputStream fout = new FileOutputStream(downloadpath.getText() + "\\" + c.getNotes().getFileName());
                fout.write(c.getNotes().getFile());
                fout.flush();
                fout.close();
            } catch (IOException ex) {
                Logger.getLogger(NotesDashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        dialog.showSuccess(this, "Download File", "File Successfully Downloaded\nFile directory:  " + downloadpath.getText());
    }

    private void backup(ActionEvent evt) {
        File backup = directory("Backup All Notes");
        List<NotesNodeController> nodes = list.getChildren().stream().map(f -> (NotesNodeController) f).collect(Collectors.toList());
        nodes.stream().forEach(c -> {
            try {
                FileOutputStream fout = new FileOutputStream(backup.getAbsolutePath() + "\\" + c.getNotes().getFileName());
                fout.write(c.getNotes().getFile());
                fout.flush();
                fout.close();
            } catch (IOException ex) {
                Logger.getLogger(NotesDashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        dialog.showSuccess(this,  "Backup Files", "Backup Completed Successfully\nFile directory:  " + downloadpath.getText());
    }

    private void sortAscending(ActionEvent evt) {
        Task<List<NotesNodeController>> task = new Task<List<NotesNodeController>>() {
            @Override
            protected List<NotesNodeController> call() throws Exception {
                List<Notes> data = dao.sortBydate("asc");
                List<NotesNodeController> collect = data.stream().filter(f -> f.getDepartment().equals(SystemUtils.getDepartment())).map(NotesNodeController::new).collect(Collectors.toList());
                return collect;
            }
        };

        task.setOnRunning(e -> LoadingController.show(this.getScene()));
        task.setOnSucceeded(e -> {
            try {
                list.getChildren().clear();
                Thread.sleep(700);
                list.getChildren().setAll(task.get());
                LoadingController.hide();
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(NotesDashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        SystemUtils.getService().execute(task);
    }

    private void sortDescending(ActionEvent evt) {
        Task<List<NotesNodeController>> task = new Task<List<NotesNodeController>>() {
            @Override
            protected List<NotesNodeController> call() throws Exception {
        List<Notes> data = dao.sortBydate("desc");
        List<NotesNodeController> collect = data.stream().filter(f -> f.getDepartment().equals(SystemUtils.getDepartment())).map(NotesNodeController::new).collect(Collectors.toList());
        return collect;
            }
        };

        task.setOnRunning(e -> LoadingController.show(this.getScene()));
        task.setOnSucceeded(e -> {
            try {
                list.getChildren().clear();
                Thread.sleep(700);
                list.getChildren().setAll(task.get());
                LoadingController.hide();
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(NotesDashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        SystemUtils.getService().execute(task);
    }

    private void search(ActionEvent evt) {
        Task<List<NotesNodeController>> task = new Task<List<NotesNodeController>>() {
            @Override
            protected List<NotesNodeController> call() throws Exception {
        List<Notes> data = dao.findByDepartment(SystemUtils.getDepartment());

        if (searchbyname.isSelected()) {
            data = data.stream().filter(f -> f.getFacultyName().equals(selectuploader.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }
        if (searchbydate.isSelected()) {
            data = data.stream().filter(f -> f.getUploadDate().equals(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(enterdate.getValue()))).collect(Collectors.toList());
        }

        List<NotesNodeController> collect = data.stream().map(NotesNodeController::new).collect(Collectors.toList());
        return collect;
            }
        };

        task.setOnRunning(e -> LoadingController.show(this.getScene()));
        task.setOnSucceeded(e -> {
            try {
                list.getChildren().clear();
                Thread.sleep(700);
                list.getChildren().setAll(task.get());
                LoadingController.hide();
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(NotesDashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        SystemUtils.getService().execute(task);
    }

    private void fileNameSearch(ActionEvent evt) {
        List<NotesNodeController> collect = NotesSearchController.show(this.getScene(), list.getChildren());
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
}
