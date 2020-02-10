/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author pc
 */
public class ValidationUtils {
    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    
    public static Validator getValidator() {
        return factory.getValidator();
    }
    
}
