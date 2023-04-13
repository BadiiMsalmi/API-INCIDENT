package com.api.backincdidents.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.backincdidents.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    
    public List<Role> findAll();
}
