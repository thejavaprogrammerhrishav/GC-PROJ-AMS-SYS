/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings;

import com.attendance.config.Crypter;
import com.attendance.config.SysConfig;
import com.attendance.main.Start;
import com.attendance.util.ExceptionDialog;
import com.attendance.util.Fxml;
import com.attendance.util.SwitchRoot;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author Programmer Hrishav
 */
public class DatabaseServerSettingsController extends AnchorPane {

    @FXML
    private TextField url;

    @FXML
    private TextField dbname;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmpassword;

    @FXML
    private TextField host;

    @FXML
    private JFXButton save;

    @FXML
    private JFXButton load;

    @FXML
    private JFXButton export;

    @FXML
    private JFXButton cancel;

    @FXML
    private TextField driverclassname;

    @FXML
    private TextField port;

    @FXML
    private TextField dialect;

    @FXML
    private JFXButton generate;

    private FXMLLoader fxml;

    private Parent parent;
    private ExceptionDialog dialog;
    private String format = "jdbc:mysql://{HOST}:{PORT}/{DBNAME}?createDatabaseIfNotExist=true";

    public DatabaseServerSettingsController(Parent parent) {
        this.parent = parent;
        fxml = Fxml.getDatabaseServerFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(DatabaseServerSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        cancel.setOnAction(e -> SwitchRoot.switchRoot(Start.st, parent));

        dialog=new ExceptionDialog();
        
        loadDefaults();

        generate.setOnAction(this::generate);
        load.setOnAction(this::load);
        export.setOnAction(this::export);
        save.setOnAction(this::save);
    }

    private void loadDefaults() {
        driverclassname.setText(SysConfig.get("sys.driver"));
        host.setText(SysConfig.get("sys.host"));
        port.setText(SysConfig.get("sys.port"));
        dbname.setText(SysConfig.get("sys.database.name"));
        dialect.setText(SysConfig.get("sys.database.dialect"));
        url.setText(SysConfig.get("sys.url"));
        username.setText(SysConfig.get("sys.username"));
        password.setText(SysConfig.get("sys.password"));

        Start.sysload();
    }

    private void save(ActionEvent evt) {
        if (password.getText().equals(confirmpassword.getText())) {
            SysConfig.set("sys.driver", driverclassname.getText());
            SysConfig.set("sys.host", host.getText());
            SysConfig.set("sys.port", port.getText());
            SysConfig.set("sys.database.name", dbname.getText());
            SysConfig.set("sys.database.dialect", dialect.getText());
            SysConfig.set("sys.url", url.getText());
            SysConfig.set("sys.username", username.getText());
            SysConfig.set("sys.password", password.getText());
            SysConfig.save();
            loadDefaults();
            
            dialog.showSuccess(this, "Configuration Settings", "Database Server Settings Saved Successfully");
        }else{
            dialog.showError(this, "Configuration Settings", "Passwords Doesn't Match");
        }
       
    }

    private void generate(ActionEvent e) {
        if (format.contains("{HOST}")) {
            format = format.replace("{HOST}", host.getText());
        }
        if (format.contains("{PORT}")) {
            format = format.replace("{PORT}", port.getText());
        }
        if (format.contains("{DBNAME}")) {
            format = format.replace("{DBNAME}", dbname.getText());
        }

        url.setText(format);
    }

    private void load(ActionEvent evt) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Load Configuration From File");
        fc.getExtensionFilters().setAll(new ExtensionFilter("System Config Files", "*.sys.config"));

        File file = fc.showOpenDialog(this.getScene().getWindow());
        if (file != null) {
            Crypter get = SysConfig.crypt();
            try {
                get.write(get.read(file.getAbsolutePath()));
                SysConfig.load();
            } catch (IOException ex) {
                Logger.getLogger(DatabaseServerSettingsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            loadDefaults();
            dialog.showSuccess(this, "Configuration Settings", "Database Server Settings Loaded From File Successfully");
        }
    }

    private void export(ActionEvent evt) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Load Configuration From File");
        fc.getExtensionFilters().setAll(new ExtensionFilter("System Config Files", "*.sys.config"));

        File file = fc.showSaveDialog(this.getScene().getWindow());
        if (file != null) {
            try {
                byte[] data;

                FileInputStream fin = new FileInputStream(Crypter.path);
                data = new byte[fin.available()];

                fin.read(data);

                fin.close();

                FileOutputStream fout = new FileOutputStream(file);
                fout.write(data);
                fout.flush();
                fout.close();
                dialog.showSuccess(this, "Configuration Settings", "Database Server Settings Exported Successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
