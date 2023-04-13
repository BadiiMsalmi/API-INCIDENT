package com.api.backincdidents.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.backincdidents.model.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    
    public List<Status> findAll();
    
}
