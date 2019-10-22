/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.notes.controller;

import com.attendance.util.Fxml;
import com.attendance.util.StageUtil;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author pc
 */
public class NotesSearchController extends AnchorPane {

    @FXML
    private TextField enterfilename;

    @FXML
    private JFXButton search;

    @FXML
    private JFXButton cancel;

    private FXMLLoader fxml;
    
    private ObservableList<Node> list;
    
    private List<NotesNodeController> result;

    private NotesSearchController(ObservableList<Node> list) {
        this.list = list;
        fxml = Fxml.getNotesSearchFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(NotesSearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void initialize() {
        result = list.stream().map(m->(NotesNodeController)m).collect(Collectors.toList());
        cancel.setOnAction(e->this.getScene().getWindow().hide());
        
        search.setOnAction(e-> {
            List<NotesNodeController> collect = list.stream().map(m->(NotesNodeController)m).collect(Collectors.toList());
            result = collect.stream().filter(f-> f.getNotes().getFileName().toLowerCase().contains(enterfilename.getText().toLowerCase())).collect(Collectors.toList());
            NotesSearchController.this.getScene().getWindow().hide();
        });
    }

    public List<NotesNodeController> getResult() {
        return result;
    }

    
    
    public static List<NotesNodeController> show(Scene parent,ObservableList<Node> list) {
        Stage st = StageUtil.newStage().centerOnScreen().fullScreen(false).fullScreenExitHint("").fullScreenExitKeyCombination(KeyCombination.NO_MATCH)
                .initModality(Modality.WINDOW_MODAL).initOwner(parent.getWindow()).initStyle(StageStyle.UNDECORATED).build();
        
        NotesSearchController dash = new NotesSearchController(list);
        
        st.setScene(new Scene(dash));
        st.showAndWait();
        
        return dash.getResult();
    }
}
