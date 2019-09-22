/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.user.principal.dao;

import com.attendance.user.principal.model.Principal;
import java.util.List;

/**
 *
 * @author Programmer Hrishav
 */
public interface PrincipalDao {
     public abstract String savePrincipal(Principal s);

    public abstract boolean updatePrincipal(Principal s);

    public abstract boolean deletePrincipal(Principal s);

    public abstract Principal findById(String id);

    public abstract List<Principal> findByGender(String gender);

    public abstract List<Principal> findAll();

    public abstract List<Principal> findByName(String name);
    
    public abstract boolean updatePrincipalId(String newId, String oldId);

    public abstract <T> List<T> get(String query, Class<T> t);
}
