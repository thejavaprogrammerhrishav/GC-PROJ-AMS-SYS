/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

/**
 *
 * @author pc
 */
public class ExceptionConverter {
    public static String getException(Exception ex) {
        if(ex.getMessage().contains("ConstraintViolationException")) {
            return "Data Already Exists";
        }else if(ex.getMessage().contains("JDBCConnectionException")) {
            return "Connection Lost";
        }else if(ex.getMessage().contains("DataIntegrityViolationException")) {
            return "Data Already Exists";
        }else if(ex.getMessage().contains("CommunicationException")) {
            return "Connection Lost";
        }else {
            return ex.getMessage();
        }
    } 
    
}
