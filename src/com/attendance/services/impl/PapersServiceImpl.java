/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.services.impl;

import com.attendance.papers.dao.PapersDao;
import com.attendance.papers.model.Paper;
import com.attendance.papers.service.PapersService;
import com.attendance.util.ExceptionConverter;
import com.attendance.util.ExceptionDialog;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Parent;

/**
 *
 * @author pc
 */
public class PapersServiceImpl implements PapersService {

    private PapersDao dao;

    private final String header = "Paper Information Error";

    private ExceptionDialog ex;
    private Parent parent;

    public void setEx(ExceptionDialog ex) {
        this.ex = ex;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public PapersDao getDao() {
        return dao;
    }

    public ExceptionDialog getEx() {
        return ex;
    }

    public void setDao(PapersDao dao) {
        this.dao = dao;
    }

    @Override
    public Long savePaper(Paper p) {
        try {
            return dao.save(p);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return -1l;
        }
    }

    @Override
    public boolean updatePaper(Paper p) {
        try {
            return dao.update(p);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

        }
        return false;
    }

    @Override
    public boolean deletePaper(Paper p) {
        try {
            return dao.delete(p);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

        }
        return false;
    }

    @Override
    public Paper findByCode(String code) {
        try {
            return dao.findByCode(code);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new Paper();
        }
    }

    @Override
    public List<Paper> findAll() {
        try {
            return dao.findAll();
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<Paper> findBySemester(String sem) {
        try {
            return dao.findBySemester(sem);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public boolean updatePaperId(String newID, String oldID) {
        try {
            return dao.updatePaperId(newID, oldID);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return false;
        }
    }

    @Override
    public List<Paper> findByDepartment(String Department) {
        try {
            return dao.findByDepartment(Department);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<Paper> findByCourseType(String courseType) {
        try {
            return dao.findByCourseType(courseType);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<Paper> findByDepartmentAndCourseType(String department, String courseType) {
        try {
            return dao.findByDepartmentAndCourseType(department, courseType);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public boolean exists(String code) {
        try {
            return dao.exists(code);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return false;
        }
    }

}
