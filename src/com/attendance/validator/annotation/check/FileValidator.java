/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.validator.annotation.check;

import com.attendance.validator.annotation.File;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author pc
 */
public class FileValidator implements ConstraintValidator<File, Byte[]> {

    @Override
    public void initialize(File a) {
    }

    @Override
    public boolean isValid(Byte[] t, ConstraintValidatorContext cvc) {
        return true;
    }

}
