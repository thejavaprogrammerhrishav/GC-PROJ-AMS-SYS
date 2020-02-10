/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

import com.attendance.message.type.MessageUtils;
import com.attendance.util.Message;
import javafx.scene.Parent;

/**
 *
 * @author pc
 */
public class ExceptionDialog {
    private MessageUtils utils;

    public ExceptionDialog() {
        
    }
    
    public void showInformation(Parent parent, String header, String content) {
        utils.showInformationDialog(header, content, parent);
    }
    
    public void showError(Parent parent, String header, String content) {
        utils.showErrorDialog(header, content, parent);
        
    }
    
    public void showWarning(Parent parent, String header, String content) {
        utils.showWarningDialog(header, content, parent);
        
    }
    
    public void showSuccess(Parent parent, String header, String content) {
        utils.showSuccessDialog(header, content, parent);
    }
}
