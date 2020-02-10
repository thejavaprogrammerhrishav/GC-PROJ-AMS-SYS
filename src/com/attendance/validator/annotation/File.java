
package com.attendance.validator.annotation;

import com.attendance.validator.annotation.check.FileValidator;
import com.attendance.validator.annotation.check.PercentageValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pc
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy=FileValidator.class)
public @interface File {
    String message() default "Empty File";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
