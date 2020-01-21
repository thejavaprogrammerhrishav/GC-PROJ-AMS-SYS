/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.user.model;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Programmer Hrishav
 */
public class SecurityQuestion {

    private long id;

    @NotEmpty(message = "{attendance.question}")
    private String question1;

    @NotEmpty(message = "{attendance.question}")
    private String question2;

    @NotEmpty(message = "{attendance.question}")
    private String question3;

    @NotEmpty(message = "{attendance.answer}")
    private String answer1;

    @NotEmpty(message = "{attendance.answer}")
    private String answer2;

    @NotEmpty(message = "{attendance.answer}")
    private String answer3;

    public SecurityQuestion() {
    }

    public SecurityQuestion(long id, String question1, String question2, String question3, String answer1, String answer2, String answer3) {
        this.id = id;
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getQuestion3() {
        return question3;
    }

    public void setQuestion3(String question3) {
        this.question3 = question3;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

}
