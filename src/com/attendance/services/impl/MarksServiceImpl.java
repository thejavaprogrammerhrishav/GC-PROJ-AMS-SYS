/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.services.impl;

import com.attendance.marks.dao.UnitTestDao;
import com.attendance.marks.model.UnitTest;
import com.attendance.marks.service.MarksService;
import com.attendance.util.ExceptionConverter;
import com.attendance.util.ExceptionDialog;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Parent;

/**
 *
 * @author pc
 */
public class MarksServiceImpl implements MarksService {

    private UnitTestDao dao;

    private final String header = "Unit Test Information Error";

    private ExceptionDialog ex;
    private Parent parent;

    public void setEx(ExceptionDialog ex) {
        this.ex = ex;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public UnitTestDao getDao() {
        return dao;
    }

    public ExceptionDialog getEx() {
        return ex;
    }

    public void setDao(UnitTestDao dao) {
        this.dao = dao;
    }

    @Override
    public int saveMarks(UnitTest ut) {
        try {
            return dao.save(ut);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

        }
        return -1;
    }

    @Override
    public boolean saveAllMarks(List<UnitTest> list) {
        try {
            return dao.saveAll(list);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

        }
        return false;
    }

    @Override
    public boolean updateMarks(UnitTest ut) {
        try {
            return dao.update(ut);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

        }
        return false;
    }

    @Override
    public boolean deleteMarks(UnitTest list) {
        try {
            return dao.delete(list);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

        }
        return false;
    }

    @Override
    public List<UnitTest> findAll() {
        try {
            return dao.findAll();
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<UnitTest> findByUnitTest(String unitTest) {
        try {
            return dao.findByUnitTest(unitTest);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<UnitTest> findBySemesterAndAcademicyearAndYear(String semester, String academicyesr, int year) {
        try {
            return dao.findBySemesterAndAcademicyearAndYear(semester, academicyesr, year);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public <T> List<T> get(String query, Class<T> type) {
        try {
            return dao.get(query, type);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

}
