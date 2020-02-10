/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.student.service;

import com.attendance.student.model.Student;
import com.attendance.util.ExceptionDialog;
import java.util.List;
import javafx.scene.Parent;

/**
 *
 * @author AKSHAY KUMAR DHAR
 */
public interface StudentService {

    public abstract String saveStudent(Student s);

    public abstract boolean updateStudent(Student s,String ns);

    public abstract boolean deleteStudent(Student s);

    public abstract Student findById(String id);

    public abstract List<Student> findByYear(int year);

    public abstract List<Student> findByAcadamicyear(String acadamicyear);

    public abstract List<Student> findByGender(String gender);

    public abstract List<Student> findAll();

    public abstract List<Student> findByRollNumber(int rollno);

    public abstract List<Student> findByName(String name);

    public abstract List<Student> findByAcadamicYearAndyear(String acadamicyear, int year);

    public abstract boolean updateStudentId(String newId, String oldId);

    public abstract <T> List<T> get(String query, Class<T> t);

    public abstract int countStudents(String acadamicyear, String department);

    public abstract int countStudents(String acadamicyear, int year, String department);

    public abstract List<Student> findByCourseType(String courseType);

    public abstract List<Student> findByDepartment(String department);

    public abstract List<String> findAllYear();

    public abstract void setParent(Parent parent);

    public abstract ExceptionDialog getEx();
}
