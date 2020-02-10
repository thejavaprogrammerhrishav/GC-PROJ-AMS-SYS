/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.services.impl;

import com.attendance.login.activity.dao.Activity;
import com.attendance.login.activity.model.LoginActivity;
import com.attendance.login.activity.service.LoginActivityService;
import com.attendance.util.ExceptionConverter;
import com.attendance.util.ExceptionDialog;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Parent;

/**
 *
 * @author pc
 */
public class LoginActivityServiceImpl implements LoginActivityService {

    private Activity dao;

    private final String header = "Login Activity Error";

    private ExceptionDialog ex;
    private Parent parent;

    public void setEx(ExceptionDialog ex) {
        this.ex = ex;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Activity getDao() {
        return dao;
    }

    public ExceptionDialog getEx() {
        return ex;
    }

    public void setDao(Activity dao) {
        this.dao = dao;
    }

    @Override
    public int saveactivity(LoginActivity login) {
        try {
            return dao.save(login);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

        }
        return -1;
    }

    @Override
    public int updateactivity(LoginActivity login) {
        try {
            return dao.update(login);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

        }
        return -1;
    }

    @Override
    public LoginActivity findByUsername(String username) {
        try {
            return dao.findByUsername(username);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new LoginActivity();
        }
    }

    @Override
    public List<LoginActivity> findByDate(String date) {
        try {
            return dao.findByDate(date);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<LoginActivity> findByName(String name) {
        try {
            return dao.findByName(name);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<LoginActivity> findByDepartment(String department) {
        try {
            return dao.findByDepartment(department);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<LoginActivity> findByStatus(String status) {
        try {
            return dao.findByStatus(status);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<LoginActivity> findByUserType(String type) {
        try {
            return dao.findByUserType(type);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<LoginActivity> findAll() {
        try {
            return dao.findAll();
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<LoginActivity> get(String sql) {
        try {
            return dao.get(sql);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

}
