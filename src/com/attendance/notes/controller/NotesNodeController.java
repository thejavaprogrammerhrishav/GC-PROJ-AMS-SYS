/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.notes.controller;

import com.attendance.notes.model.Notes;
import com.attendance.util.Fxml;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXCheckBox;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author pc
 */
public class NotesNodeController extends AnchorPane {

    @FXML
    private ImageView image;

    @FXML
    private Label filename;

    @FXML
    private Label name;

    @FXML
    private Label size;
    
    @FXML
    private Label date;
    
    @FXML
    private JFXCheckBox select;

    private FXMLLoader fxml;
    private Notes notes;

    public NotesNodeController(Notes notes) {
        this.notes = notes;
        fxml = Fxml.getNotesNodeFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(NotesNodeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        filename.setText(notes.getFileName());
        size.setText(notes.getFileSize() + " MB");
        name.setText(notes.getFacultyName());
        date.setText(notes.getUploadDate());
        String ext = notes.getFileName().substring(notes.getFileName().lastIndexOf(".")+1);

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

    public boolean isSelected (){
        return select.isSelected();
    }
    
    public Notes getNotes() {
        return notes;
    }
}
