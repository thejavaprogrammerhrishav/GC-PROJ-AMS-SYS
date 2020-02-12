/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.validator.annotation.check;

import com.attendance.validator.annotation.Percentage;
import static java.lang.ProcessBuilder.Redirect.to;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author pc
 */
public class PercentageValidator implements ConstraintValidator<Percentage, Double> {

    @Override
    public void initialize(Percentage a) {

    }

    @Override
    public boolean isValid(Double t, ConstraintValidatorContext cvc) {
        return (t.doubleValue() >= 0.0 && t.doubleValue() <= 100.0);
    }
}
