package com.api.backincdidents.controller;

import com.api.backincdidents.model.Incident;
import com.api.backincdidents.repository.IncidentRepository;
import com.api.backincdidents.service.incidentsService;
import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncidentController {

  @Autowired
  private incidentsService service;

  @Autowired
  private IncidentRepository repo;

  // @PostMapping("/search")
  // public List<Incident> getAllIncidents(
  //   @Param("assigne") String assigne,
  //   @Param("declarant") String declarant,
  //   @Param("status") String status,
  //   @Param("date") Date date
  // ) {
  //   List<Incident> listIncident = service.listAll( assigne, declarant, status, date);
  //   return listIncident;
  // }

  @PostMapping("/addIncident")
  public Incident addIncident(@RequestBody Incident incident) {
    Incident newIncident = service.addIncident(incident);
    return newIncident;
  }

  @CrossOrigin("*")
  @GetMapping("/incidents")
  public List<Incident> getAllIncidents(){
    List<Incident> incidents = service.findAll();
    return incidents;
  }

  @CrossOrigin("*")
  @PostMapping("/search")
  public List<Incident> Incidents(
    @Param("assigne") String assigne,
    @Param("declarant") String declarant,
    @Param("status") String status,
    @Param("date") Date date
  ) {
    List<Incident> listIncident = repo.findByAssigne_FirstNameLikeAndDeclarant_FirstNameLikeAndStatus_LabelAndCreationdate( '%'+assigne+'%','%'+declarant+'%',status,date);
    return listIncident;
  }

}
