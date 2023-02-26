package com.api.backincdidents.controller;

import com.api.backincdidents.model.Incident;
import com.api.backincdidents.service.incidentsService;
import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncidentController {

  @Autowired
  private incidentsService service;

  // Hedha el api bch tzid incident jdid
  @CrossOrigin("*")
  @PostMapping("/addIncident")
  public Incident addIncident(@RequestBody Incident incident) {
    Incident newIncident = service.addIncident(incident);
    return newIncident;
  }

  @CrossOrigin("*")
  @GetMapping("/incidents")
  public List<Incident> getAllIncidents() {
    List<Incident> incidents = service.findAll();
    return incidents;
  }

  @CrossOrigin("*")
  @GetMapping("/incidents/{id}")
  public List<Incident> getIncidentsById(@PathVariable int id) {
    List<Incident> incidents = service.findById(id);
    return incidents;
  }

  @CrossOrigin("*")
  @PostMapping("/search")            //CHECK IT
  public List<Incident> Incidents(
    @RequestParam("assignerInput") String assigne,
    @RequestParam("declarantInput") String declarant,
    @RequestParam("statusSelect") String status,
    @RequestParam("dateInput") Date date
  ) {
    List<Incident> listIncident = service.searchIncidents(assigne , declarant,status,date);
    return listIncident;
  }
}
