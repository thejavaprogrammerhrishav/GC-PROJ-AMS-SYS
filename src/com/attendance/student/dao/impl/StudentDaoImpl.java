/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.student.dao.impl;

import com.attendance.student.dao.StudentDao;
import com.attendance.student.model.Student;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Programmer Hrishav
 */
public class StudentDaoImpl implements StudentDao {

    private JdbcTemplate jdbcTemplate;
    private HibernateTemplate hibernateTemplate;

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public String saveStudent(Student s) {
        String stud = (String) hibernateTemplate.save(s);
        return stud;
    }

    @Override
    @Transactional
    public boolean updateStudent(Student s) {
        try {
            hibernateTemplate.update(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteStudent(Student s) {
        try {
            hibernateTemplate.delete(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public Student findById(String id) {
        return hibernateTemplate.get(Student.class, id);
    }

    @Override
    public List<Student> findByYear(int year) {
        return jdbcTemplate.query("select * from student where year=" + year, new BeanPropertyRowMapper<Student>(Student.class));
    }

    @Override
    @Transactional
    public List<Student> findByAcadamicYear(String acadamicyear) {
        return (List<Student>) hibernateTemplate.find("from Student where acadamicyear=?", acadamicyear);
    }

    @Override
    public List<Student> findByGender(String gender) {
        return jdbcTemplate.query("select * from student where gender='" + gender + "'", new BeanPropertyRowMapper<Student>(Student.class));
    }

    @Override
    @Transactional
    public List<Student> findAll() {
        return hibernateTemplate.loadAll(Student.class);
    }

    @Override
    @Transactional
    public List<Student> findByRollNumber(int rollno) {
        return ((List<Student>) hibernateTemplate.find("from Student where rollno=?", rollno));
    }

    @Override
    public List<Student> findByName(String name) {
        return jdbcTemplate.query("select * from student where name=" + name, new BeanPropertyRowMapper<Student>(Student.class));
    }

    @Override
    public boolean updateStudentId(String newId, String oldId) {
        try {
            jdbcTemplate.execute("update student set id='" + newId + "' where id='" + oldId + "'");
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public <T> List<T> get(String query, Class<T> t) {
        return jdbcTemplate.queryForList(query, t);
    }

    @Override
    public int countStudents(String acadamicyear, String department) {
        return jdbcTemplate.queryForObject("select count(*) from student where acadamicyear='" + acadamicyear + "' and department = '" + department + "'", Integer.class);
    }

    @Override
    public int countStudents(String acadamicyear, String year, String department) {
        return jdbcTemplate.queryForObject("select count(*) from student where acadamicyear='" + acadamicyear + "' and year=" + year + " and department = '" + department + "'", Integer.class);
    }

    @Override
    @Transactional
    public List<Student> findByAcadamicYearAndYear(String acadamicyear, int year) {
        return (List<Student>) hibernateTemplate.find("from Student where acadamicyear=? and year=?", acadamicyear, year);
    }

    @Override
    @Transactional
    public List<Student> findByCourseType(String courseType) {
        return (List<Student>) hibernateTemplate.find("from Student where courseType=?", courseType);
    }

    @Override
    @Transactional
    public List<Student> findByDepartment(String department) {
        return (List<Student>) hibernateTemplate.find("from Student where department=?", department);
    }

    @Override
    public List<String> findAllYears() {
        return jdbcTemplate.queryForList("select distinct(year) from student", String.class);
    }
}
