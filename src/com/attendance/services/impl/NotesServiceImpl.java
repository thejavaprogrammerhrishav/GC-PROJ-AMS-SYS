/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.services.impl;

import com.attendance.notes.dao.NotesDao;
import com.attendance.notes.model.Notes;
import com.attendance.notes.service.NotesService;
import com.attendance.util.ExceptionConverter;
import com.attendance.util.ExceptionDialog;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Parent;

/**
 *
 * @author pc
 */
public class NotesServiceImpl implements NotesService {

    private NotesDao dao;

    private final String header = "Notes Information Error";

    private ExceptionDialog ex;
    private Parent parent;

    public void setEx(ExceptionDialog ex) {
        this.ex = ex;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public NotesDao getDao() {
        return dao;
    }

    public ExceptionDialog getEx() {
        return ex;
    }

    public void setDao(NotesDao dao) {
        this.dao = dao;
    }

    @Override
    public Integer saveNotes(Notes notes) {
        try {
            return dao.save(notes);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
        }
        return -1;
    }

    @Override
    public boolean updateNotes(Notes notes) {
        try {
            return dao.update(notes);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

        }
        return false;
    }

    @Override
    public boolean deleteNotes(Notes notes) {
        try {
            return dao.delete(notes);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

        }
        return false;
    }

    @Override
    public List<Notes> findAll() {
        try {
            return dao.findAll();
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<Notes> findByFaculty(String facultyName) {
        try {
            return dao.findByFaculty(facultyName);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<Notes> findByDate(String uploadDate) {
        try {
            return dao.findByDate(uploadDate);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<Notes> findByFileName(String fileName) {
        try {
            return dao.findByFileName(fileName);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<Notes> findByDateSorted(String uploadDate, String order) {
        try {
            return dao.findByDateSorted(uploadDate, order);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<Notes> sortBydate(String order) {
        try {
            return dao.sortBydate(order);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<Notes> sortByFileSize(String order) {
        try {
            return dao.sortByFileSize(order);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<Notes> findByDepartment(String Department) {
        try {
            return dao.findByDepartment(Department);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

}
