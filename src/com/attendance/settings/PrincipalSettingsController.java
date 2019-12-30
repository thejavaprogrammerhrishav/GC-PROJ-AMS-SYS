/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings;

import com.attendance.dashboard.PrincipalDashboardController;
import com.attendance.main.Start;
import com.attendance.util.Fxml;
import com.attendance.util.RootFactory;
import com.attendance.util.StageUtil;
import com.attendance.util.SwitchRoot;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Programmer Hrishav
 */
public class PrincipalSettingsController extends AnchorPane {

    @FXML
    private JFXButton close;

    @FXML
    private JFXButton deletefacultyuser;

    @FXML
    private JFXButton modifyusertype;

    @FXML
    private JFXButton blockfacultylogin;

    @FXML
    private JFXButton principalloginactivity;

    @FXML
    private JFXButton hodloginactivity;

    private FXMLLoader fxml;
    private Parent parent;

    public PrincipalSettingsController(Parent parent) {
        this.parent = parent;
        fxml = Fxml.getPrincipalSettingsFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        close.setOnAction(eh -> SwitchRoot.switchRoot(Start.st, parent));
        deletefacultyuser.setOnAction(new ShowDepartmentPage("settings"));

    }

    private class ShowDepartmentPage implements EventHandler<ActionEvent> {

        private final String type;

        public ShowDepartmentPage(String type) {
            this.type = type;
        }

        @Override
        public void handle(ActionEvent t) {
            Stage st = StageUtil.newStage().centerOnScreen().fullScreen(false).fullScreenExitHint("").fullScreenExitKeyCombination(null)
                    .initModality(Modality.WINDOW_MODAL).initOwner(((Node) t.getSource()).getScene().getWindow()).initStyle(StageStyle.UNDECORATED)
                    .build();
            if (type.equals("settings")) {
                st.getScene().setRoot(RootFactory.getSelectDepartmentRoot("settings", PrincipalSettingsController.this.getScene().getRoot()));

            }
            st.show();
        }
    }

}
