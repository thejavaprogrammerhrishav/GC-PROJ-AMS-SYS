/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.dashboard.node;

import com.attendance.util.Fxml;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author pc
 */
public class PrincipalDashboardDepartmentNodeController extends AnchorPane {
    
    private FXMLLoader fxml;

    public PrincipalDashboardDepartmentNodeController() {
        fxml = Fxml.getPrincipalDashboardDepartmentNodeFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalDashboardDepartmentNodeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void initialize(){
        
    }
    
}
