/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.forgot;

import com.attendance.login.dao.Login;
import com.attendance.login.service.LoginService;
import com.attendance.login.user.model.SecurityQuestion;
import com.attendance.main.Start;
import com.attendance.util.ExceptionDialog;
import com.attendance.util.Fxml;
import com.attendance.util.RootFactory;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.attendance.util.ValidationUtils;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javax.validation.ConstraintViolation;

/**
 *
 * @author Programmer Hrishav
 */
public class SecurityQuestionController extends AnchorPane {

    @FXML
    private Pane pane;

    @FXML
    private JFXButton proceed;

    @FXML
    private Label admin;

    @FXML
    private Label department;

    @FXML
    private TextField answer1;

    @FXML
    private ComboBox<String> question1;

    @FXML
    private TextField answer2;

    @FXML
    private ComboBox<String> question2;

    @FXML
    private TextField answer3;

    @FXML
    private ComboBox<String> question3;

    @FXML
    private JFXButton updateanswer;

    @FXML
    private JFXButton close;

    private FXMLLoader fxml;
    private Parent parent;
    private String mode;

    private LoginService dao;
    private ExceptionDialog dialog;

    public SecurityQuestionController(Parent parent, String mode) {
        this.parent = parent;
        this.mode = mode;
        fxml = Fxml.getSecurityQuestionFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(SecurityQuestionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao = (LoginService) Start.app.getBean("loginservice");
        dao.setParent(this);
        dialog = dao.getEx();

        close.setOnAction(e -> this.getScene().getWindow().hide());
        admin.setText(SystemUtils.getCurrentUser().getType());
        department.setText(SystemUtils.getDepartment());
        question1.getItems().setAll(getQuestions1());
        question2.getItems().setAll(getQuestion2());
        question3.getItems().setAll(getQuestion3());
        proceed.setVisible(false);
        updateanswer.setVisible(false);
        pane.setVisible(false);
        if (mode.equals("Forgot")) {
            question1.getSelectionModel().select(SystemUtils.getCurrentUser().getSecurityquestion().getQuestion1());
            question2.getSelectionModel().select(SystemUtils.getCurrentUser().getSecurityquestion().getQuestion2());
            question3.getSelectionModel().select(SystemUtils.getCurrentUser().getSecurityquestion().getQuestion3());
            question1.setDisable(true);
            question2.setDisable(true);
            question3.setDisable(true);
            proceed.setVisible(true);
            updateanswer.setVisible(false);
            proceed.setOnAction(this::proceed);
        }
        if (mode.equals("New")) {
            if (SystemUtils.getCurrentUser().hasSecurityQuestion()) {
                question1.getSelectionModel().select(SystemUtils.getCurrentUser().getSecurityquestion().getQuestion1());
                question2.getSelectionModel().select(SystemUtils.getCurrentUser().getSecurityquestion().getQuestion2());
                question3.getSelectionModel().select(SystemUtils.getCurrentUser().getSecurityquestion().getQuestion3());
            }
            proceed.setVisible(false);
            updateanswer.setVisible(true);
            updateanswer.setOnAction(this::update);
        }
    }

    private List<String> getQuestions1() {
        return Arrays.asList("What is your favourate colour?",
                "What is the name of your college?",
                "How many depertment are there in your college or school?",
                "What is the passout year of your college or school?",
                "What is your fabourate car?",
                "What is your phone number?",
                "How many number of faculty member are there in your college depertment?",
                "What is the name of your idol?",
                "In which city or town was your first job?",
                "What is the name of your first boss?",
                "What is the zip code of your college or school?",
                "What is the number of digit in Postal Index Number (PIN) in India?",
                "What is the address of your college or school?",
                "What is the name of the favourate teacher of your college or school?",
                "What is the name of the principal of your college or school?",
                "In which year your college or school was estabilshed?",
                "What is your childhood name?",
                "What is your surname?",
                "What was the name of your pet?",
                "When did your parents meet?");
    }

    private List<String> getQuestion2() {
        return Arrays.asList(" What was the amount of your first salary?",
                " What was the name of your first school you attended?",
                " What was the first company you worked for?",
                " Who was your most memorable elementry school teacher?",
                " What is the nickname of your youngest sibling?",
                " In which city did you meet your spouse or significant other?",
                " What is your oldest cousin's first and last name?",
                " What was your childhood phone number including area code?",
                " What was the last name of the next door neighbour where you grew up?",
                " From which school did you pass matric exam?",
                " What street did you live in childhood?",
                " What is the name of the first school you attended?",
                " What is your maternal mother's maiden name?",
                " What is your paternal mother's maiden name?",
                " What is the name of your first mobile phone?",
                " What is the name of the Prime Minister of your country?",
                " What is the model of your first car?",
                " When did the last census take place in India?",
                " How many neighbouring countries are there in India?",
                " What is the number of states in your country?");
    }

    private List<String> getQuestion3() {
        return Arrays.asList("How many number of districts are there in your state?",
                " How many number of articles are there in Indian constitution?",
                " Who is your favourate TV anchor/host?",
                " Who is your favourate news reporter?",
                " Who is your favourate Politician?",
                " Who is your favourate Prime Minister?",
                " What is your favourate country?",
                " What is your favourate Place?",
                " Who is your favourate singer?",
                " Who is your favourate Bollywood Actor?",
                " Who is your favourate Bollywood Actress?",
                " What is your favourate Film?",
                " What is your favourate Book?",
                " What is your favourate Serial?",
                " What is your favourate Game?",
                " Who is your favourate Cricketer?",
                " What is your favourate TV channel?",
                " What is your favourate Animal?",
                " What is your favourate Fruit?",
                " What is your favourate dish?");
    }

    private void proceed(ActionEvent evt) {
        if (answer1.getText().equals(SystemUtils.getCurrentUser().getSecurityquestion().getAnswer1())
                && answer2.getText().equals(SystemUtils.getCurrentUser().getSecurityquestion().getAnswer2())
                && answer3.getText().equals(SystemUtils.getCurrentUser().getSecurityquestion().getAnswer3())) {
            SwitchRoot.switchRoot(Start.st, RootFactory.getRestPassword3Root(Start.st.getScene().getRoot()));
            pane.setVisible(false);
        } else {
            pane.setVisible(true);
        }
    }

    private void update(ActionEvent event) {
        SecurityQuestion question = SystemUtils.getCurrentUser().getSecurityquestion();
        question.setQuestion1(question1.getSelectionModel().getSelectedItem());
        question.setAnswer1(answer1.getText());

        question.setQuestion2(question2.getSelectionModel().getSelectedItem());
        question.setAnswer2(answer2.getText());

        question.setQuestion3(question3.getSelectionModel().getSelectedItem());
        question.setAnswer3(answer3.getText());


        Set<ConstraintViolation<SecurityQuestion>> validate = ValidationUtils.getValidator().validate(question);
        if (validate.isEmpty()) {
            dao.updateUser(SystemUtils.getCurrentUser());
            SystemUtils.getCurrentUser().setSecurityquestion(question);
            this.getScene().getWindow().hide();
        } else {
            validate.stream().forEach(c -> dialog.showError(this, "Security Questions", c.getMessage()));
        }
    }
}
