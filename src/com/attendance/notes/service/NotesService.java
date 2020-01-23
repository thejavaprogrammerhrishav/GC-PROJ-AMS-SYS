/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.notes.service;

import com.attendance.notes.model.Notes;
import java.util.List;

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

    public abstract List<Notes> findBydate(String order);

    public abstract List<Notes> findByFileSize(String order);

    public abstract List<Notes> findByDepartment(String Department);
}
