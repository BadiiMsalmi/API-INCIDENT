package com.api.backincdidents.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.api.backincdidents.repository.IncidentRepository;

import java.util.List;
import com.api.backincdidents.model.Incident;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/incidents")
public class IncidentController {
    
    @Autowired
    private IncidentRepository incidentRepository;

    @GetMapping
    public List<Incident> getAllIncidents(){
        return incidentRepository.findAll();

    }
}
