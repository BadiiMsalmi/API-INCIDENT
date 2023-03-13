package com.api.backincdidents.controller;

import com.api.backincdidents.Dto.FilterDto;
import com.api.backincdidents.Dto.WhereDto;
import com.api.backincdidents.model.Incident;
import com.api.backincdidents.model.Status;
import com.api.backincdidents.model.User;
import com.api.backincdidents.service.incidentsService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  @GetMapping("/incident/{id}")
  public Incident getIncidentsById(@PathVariable int id) {
    Incident incident = service.getIncidentById(id);
    return incident;
  }

  // @CrossOrigin("*")
  // @PostMapping("/search") // CHECK IT
  // public List<Incident> Incidents(
  // @RequestParam("assignerInput") String assigne,
  // @RequestParam("declarantInput") String declarant,
  // @RequestParam("statusSelect") String status,
  // @RequestParam("dateInput") Date date) {
  // List<Incident> listIncident = service.searchIncidents(assigne, declarant,
  // status, date);
  // System.out.println(status);
  // return listIncident;
  // }

  @PersistenceContext
  private EntityManager em;

  @CrossOrigin("*")
  @PostMapping("/search")
  public List<Incident> search(@RequestBody FilterDto filter) {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<Incident> criteriaQuery = criteriaBuilder.createQuery(Incident.class);
    Root<Incident> root = criteriaQuery.from(Incident.class);
    List<Predicate> predicates = new ArrayList<>();

    for (WhereDto where : filter.getWhere()) {
      String field = where.getField();
      String operator = where.getOperator();
      List<String> modalities = where.getModalities();

      if (field.equals("assigne")) {
        Join<Incident, User> assigneJoin = root.join("assigne");
        predicates.add(
          criteriaBuilder.equal(assigneJoin.get("firstName"), modalities.get(0))
        );
      } else if (field.equals("declarant")) {
        Join<Incident, User> declarantJoin = root.join("declarant");
        predicates.add(
          criteriaBuilder.equal(
            declarantJoin.get("firstName"),
            modalities.get(0)
          )
        );
      } else if (field.equals("creationdate")) {
        predicates.add(
          criteriaBuilder.equal(root.get("creationdate"), modalities.get(0))
        );
      } else if (field.equals("status")) {
        Join<Incident, Status> statusJoin = root.join("status");
        predicates.add(
          criteriaBuilder.equal(statusJoin.get("label"), modalities.get(0))
        );
      }
    }

    criteriaQuery.select(root).where(predicates.toArray(new Predicate[] {}));
    TypedQuery<Incident> query = em.createQuery(criteriaQuery);
    List<Incident> incidents = query.getResultList();
    return incidents;
  }


  @CrossOrigin("*")
  @PutMapping("/incidents/{id}")
  public ResponseEntity<Incident> updateIncident(
    @PathVariable("id") int id,
    @RequestBody Incident incident
  ) {
    Incident existingIncident = service.getIncidentById(id);
    if (existingIncident == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    if (incident.getReference() != null) {
      existingIncident.setReference(incident.getReference());
    }
    if (incident.getLibelle() != null) {
      existingIncident.setLibelle(incident.getLibelle());
    }
    if (incident.getCreationdate() != null) {
      existingIncident.setCreationdate(incident.getCreationdate());
    }
    if (incident.getStatus() != null) {
      existingIncident.setStatus(incident.getStatus());
    }
    if (incident.getDeclarant() != null) {
      existingIncident.setDeclarant(incident.getDeclarant());
    }
    if (incident.getAssigne() != null) {
      existingIncident.setAssigne(incident.getAssigne());
    }

    Incident updatedIncident = service.updateIncident(existingIncident);
    return new ResponseEntity<>(updatedIncident, HttpStatus.OK);
  }
}
