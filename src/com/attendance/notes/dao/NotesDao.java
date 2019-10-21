/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.notes.dao;

import com.attendance.notes.model.Notes;
import java.util.List;

/**
 *
 * @author Programmer Hrishav
 */
public interface NotesDao {

    public abstract Integer save(Notes notes);

    public abstract boolean update(Notes notes);

    public abstract boolean delete(Notes notes);

    public abstract List<Notes> findAll();

    public abstract List<Notes> findByFaculty(String facultyName);

    public abstract List<Notes> findByDate(String uploadDate);

    public abstract List<Notes> findByFileName(String fileName);

    public abstract List<Notes> findByDateSorted(String uploadDate, String order);

    public abstract List<Notes> sortBydate(String order);

    public abstract List<Notes> sortByFileSize(String order);
    
    public abstract List<Notes> findByDepartment(String department);
}
