/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.student.service;

import com.attendance.student.model.Student;
import java.util.List;

/**
 *
 * @author AKSHAY KUMAR DHAR
 */
public interface StudentService {

    public abstract String saveStudent(Student s);

    public abstract boolean updateStudent(Student s);

    public abstract boolean deleteStudent(Student s);

    public abstract List<Student> findById(String id);

    public abstract List<Student> findByYear(String year);

    public abstract List<Student> findByAcadamicyear(String acadamicyear);

    public abstract List<Student> findByGender(String gender);

    public abstract List<Student> findByAll();

    public abstract List<Student> findByRollNumber(int rollno);

    public abstract List<Student> findByName(String name);

    public abstract List<Student> findByAcadamicYearAndyear(String acadamicyear, String year);

    public abstract boolean updateStudentId(String newId, String oldId);

    public abstract <T> List<T> get(String query, Class<T> t);

    public abstract int countStudents(String acadamicyear, String department);

    public abstract int countStudents(String acadamicyear, String year, String department);

    public abstract List<Student> findByCourseType(String courseType);

    public abstract List<Student> findByDepartment(String department);

    public abstract List<String> findAllYear();
}
