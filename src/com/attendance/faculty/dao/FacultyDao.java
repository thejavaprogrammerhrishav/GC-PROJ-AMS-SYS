/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.faculty.dao;

import com.attendance.faculty.model.Faculty;
import java.util.List;

/**
 *
 * @author Programmer Hrishav
 */
public interface FacultyDao {

    public abstract String saveFaculty(Faculty s);

    public abstract boolean updateFaculty(Faculty s);

    public abstract boolean deleteFaculty(Faculty s);

    public abstract Faculty findById(String id);

    public abstract List<Faculty> findByGender(String gender);

    public abstract List<Faculty> findAll();

    public abstract List<Faculty> findByFirstName(String firstname);

    public abstract List<Faculty> findByLastName(String lastname);
    
    public abstract List<Faculty> findByDepartment(String department);

    public abstract boolean updateFacultyId(String newId, String oldId);

    public abstract <T> List<T> get(String query, Class<T> t);

}
