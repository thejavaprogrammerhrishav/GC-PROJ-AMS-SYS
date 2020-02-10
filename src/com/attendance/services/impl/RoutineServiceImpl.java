/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.services.impl;

import com.attendance.routine.service.RoutineService;
import com.attendance.routines.dao.RoutineDao;
import com.attendance.routines.model.Routine;
import com.attendance.util.ExceptionConverter;
import com.attendance.util.ExceptionDialog;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Parent;

/**
 *
 * @author pc
 */
public class RoutineServiceImpl implements RoutineService {

    private final String header = "Routine Error";

    private RoutineDao dao;
    private ExceptionDialog ex;
    private Parent parent;

    public void setEx(ExceptionDialog ex) {
        this.ex = ex;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public RoutineDao getDao() {
        return dao;
    }

    public ExceptionDialog getEx() {
        return ex;
    }

    public void setDao(RoutineDao dao) {
        this.dao = dao;
    }

    @Override
    public Integer saveRoutine(Routine R) {
        try {
            return dao.save(R);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
        }
        return -1;
    }

    @Override
    public boolean updateRoutine(Routine R) {
        try {
            return dao.update(R);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
        }
        return false;
    }

    @Override
    public boolean deleteRoutine(Routine R) {
        try {
            return dao.delete(R);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
        }

        return false;
    }

    @Override
    public List<Routine> findAll() {
        try {
            return dao.findAll();
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
        }
        return new ArrayList<>();

    }

    @Override
    public Routine findById(Integer id) {
        try {
            return dao.findById(id);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
            return new Routine();
        }
    }

    @Override
    public List<Routine> findByDepartment(String department) {
        try {
            return dao.findByDepartment(department);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
            return new ArrayList<>();
        }
    }

    @Override
    public List<Routine> findByDate(String date) {
        try {
            return dao.findByDate(date);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
            return new ArrayList<>();
        }
    }

    @Override
    public List<Routine> findBydepartmentAndDate(String department, String date) {
        try {
            return dao.findByDepartmentAndDate(department, date);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
            return new ArrayList<>();
        }
    }

    @Override
    public List<Routine> findByDepartmentAndYear(String department, int year) {
        try {
            return dao.findByDepartmentAndYear(department, department);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
            return new ArrayList<>();
        }
    }

    @Override
    public List<Routine> sortByDepartment(String department, String order) {
        try {
            return dao.sortByDepartment(department, order);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
            return new ArrayList<>();
        }
    }

    @Override
    public Routine findByDepartmentAndDateAndStatus(String department, String date, String status) {
        try {
            return dao.findByDepartmentAndDateAndStatus(department, date, status);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
            return new Routine();
        }

    }

    @Override
    public List<Routine> findByDepartmentAndStatus(String department, String status) {
        try {
            return dao.findByDepartmentAndStatus(department, status);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
            return new ArrayList<>();
        }
    }

    @Override
    public Integer hasActiveRoutine(String department, int year) {
        try {
            return dao.hasActiveRoutine(department, department);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
            return -1;
        }
    }

    @Override
    public <T> List<T> get(String sql, Class<T> type) {
        try {
            return dao.get(sql, type);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
            return new ArrayList<>();
        }
    }

}
