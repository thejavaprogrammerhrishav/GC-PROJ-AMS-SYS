/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.notes.service;

import com.attendance.notes.model.Notes;
import com.attendance.util.ExceptionDialog;
import java.util.List;
import javafx.scene.Parent;

/**
 *
 * @author AKSHAY KUMAR DHAR
 */
public interface NotesService {

    public abstract Integer saveNotes(Notes notes);

    public abstract boolean updateNotes(Notes notes);

    public abstract boolean deleteNotes(Notes notes);

    public abstract List<Notes> findAll();

    public abstract List<Notes> findByFaculty(String facultyName);

    public abstract List<Notes> findByDate(String uploadDate);

    public abstract List<Notes> findByFileName(String fileName);

    public abstract List<Notes> findByDateSorted(String uploadDate, String order);

    public abstract List<Notes> sortBydate(String order);

    public abstract List<Notes> sortByFileSize(String order);

    public abstract List<Notes> findByDepartment(String Department);
    
    public abstract void setParent(Parent parent);
    
    public abstract ExceptionDialog getEx();
}
