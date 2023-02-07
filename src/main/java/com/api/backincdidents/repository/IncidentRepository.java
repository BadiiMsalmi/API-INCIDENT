package com.api.backincdidents.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.backincdidents.model.Incident;

public interface IncidentRepository extends JpaRepository<Incident,Integer> {
    
}
