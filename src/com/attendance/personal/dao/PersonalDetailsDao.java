/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.personal.dao;

import com.attendance.personal.model.PersonalDetails;
import java.util.List;

/**
 *
 * @author Programmer Hrishav
 */
public interface PersonalDetailsDao {

    public abstract int save(PersonalDetails user);

    public abstract boolean update(PersonalDetails user);

    public abstract boolean delete(PersonalDetails user);

    public abstract PersonalDetails findById(int userId);

    public abstract List< PersonalDetails> findAll();

    public abstract List< PersonalDetails> findByName(String name);

    public abstract List< PersonalDetails> findByGender(String gender);
}
