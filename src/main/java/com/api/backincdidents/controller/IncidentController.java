package com.api.backincdidents.controller;

import com.api.backincdidents.model.Incident;
import com.api.backincdidents.service.incidentsService;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncidentController {

  @Autowired
  private incidentsService service;

  @PostMapping("/search")
  public List<Incident> getAllIncidents(
    @Param("assigne") String assigne,
    @Param("declarant") String declarant,
    @Param("status") String status,
    @Param("date") Date date
  ) {
    List<Incident> listIncident = service.listAll(assigne, declarant,status,date);
    System.out.println(assigne +" "+ declarant +" "+ status + " " + date );
    return listIncident;
  }

//   @PostMapping("/search")
//   public Incident searchIncidentById(
//     @ModelAttribute("incidentSearchFormData") Incident formData
//   ) {
//     Incident inc = service.get(formData.getId());
//     return inc;
//   }
}
