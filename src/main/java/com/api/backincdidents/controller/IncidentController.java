package com.api.backincdidents.controller;

import com.api.backincdidents.Dto.FilterDto;
import com.api.backincdidents.Dto.WhereDto;
import com.api.backincdidents.model.Incident;
import com.api.backincdidents.model.Status;
import com.api.backincdidents.model.User;
import com.api.backincdidents.service.IncidentService;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/incident")
@RequiredArgsConstructor
public class IncidentController {

  @Autowired
  private IncidentService service;

  public static String uploadDirectory = System.getProperty("user.dir")+"resources/imageData";

  

  @GetMapping("/incidents")
  public ResponseEntity<Object> getAllIncidents() {
    List<Incident> incidents = service.findAll();
    if(incidents == null){
      String errorMessage = "No incidents available";
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
    return ResponseEntity.ok(incidents);
  }

 
  @GetMapping("/getincident/{id}")
  public ResponseEntity<Object> getIncidentsById(@PathVariable int id) {
    Incident incident = service.getIncidentById(id);
    if (incident == null) {
      String errorMessage = "Incident not found with ID: " + id;
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
    return ResponseEntity.ok(incident);
  }

  @PersistenceContext
  private EntityManager em;

  @PostMapping("/search")
  public ResponseEntity<Object> search(@RequestParam String email,@RequestBody FilterDto filter) {
      CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
      CriteriaQuery<Incident> criteriaQuery = criteriaBuilder.createQuery(Incident.class);
      Root<Incident> root = criteriaQuery.from(Incident.class);
      List<Predicate> predicates = new ArrayList<>();
  
      Join<Incident, User> assigneJoin = root.join("assigne");
      predicates.add(criteriaBuilder.equal(assigneJoin.get("email"), email));
  
      for (WhereDto where : filter.getWhere()) {
          String field = where.getField();
          List<String> modalities = where.getModalities();
  
          if (field.equals("declarant")) {
            Join<Incident, User> declarantJoin = root.join("declarant");
            predicates.add(criteriaBuilder.equal(declarantJoin.get("firstname"), modalities.get(0)));
        } else if (field.equals("creationdate")) {
            predicates.add(criteriaBuilder.equal(root.get("creationdate"), modalities.get(0)));
        } else if (field.equals("status")) {
            Join<Incident, Status> statusJoin = root.join("status");
            predicates.add(criteriaBuilder.equal(statusJoin.get("label"), modalities.get(0)));
        }
      }
  
      criteriaQuery.select(root).where(predicates.toArray(new Predicate[] {}));
      TypedQuery<Incident> query = em.createQuery(criteriaQuery);
      List<Incident> incidents = query.getResultList();
  
      if (incidents.isEmpty()) {
          String errorMessage = "No incidents found matching the search criteria";
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
      }
  
      return ResponseEntity.ok(incidents);
  }

  @PostMapping("/searchAdmin")
public ResponseEntity<Object> search(@RequestBody FilterDto filter) {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<Incident> criteriaQuery = criteriaBuilder.createQuery(Incident.class);
    Root<Incident> root = criteriaQuery.from(Incident.class);
    List<Predicate> predicates = new ArrayList<>();

    for (WhereDto where : filter.getWhere()) {
        String field = where.getField();
        List<String> modalities = where.getModalities();

        if (field.equals("assigne")) {
            Join<Incident, User> assigneJoin = root.join("assigne");
            predicates.add(criteriaBuilder.equal(assigneJoin.get("firstname"), modalities.get(0)));
        } else if (field.equals("declarant")) {
            Join<Incident, User> declarantJoin = root.join("declarant");
            predicates.add(criteriaBuilder.equal(declarantJoin.get("firstname"), modalities.get(0)));
        } else if (field.equals("creationdate")) {
            predicates.add(criteriaBuilder.equal(root.get("creationdate"), modalities.get(0)));
        } else if (field.equals("status")) {
            Join<Incident, Status> statusJoin = root.join("status");
            predicates.add(criteriaBuilder.equal(statusJoin.get("label"), modalities.get(0)));
        }
    }

    criteriaQuery.select(root).where(predicates.toArray(new Predicate[] {}));
    TypedQuery<Incident> query = em.createQuery(criteriaQuery);
    List<Incident> incidents = query.getResultList();

    if (incidents.isEmpty()) {
        String errorMessage = "No incidents found matching the search criteria";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    return ResponseEntity.ok(incidents);
}




  @PutMapping("/incidents/{id}")
  public ResponseEntity<Incident> updateIncident(
      @PathVariable("id") int id,
      @RequestBody Incident incident) {
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

  @GetMapping("/incidentsByUser")
  public ResponseEntity<Object> getIncidentsForUser(@RequestParam Map<String, String>  requestBody){
    String email = requestBody.get("email");
    System.out.println(email);
    List<Incident> incidents = service.getIncidentsForUser(email);
    if(incidents == null){
      String errorMessage = "No incidents available";
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
    return ResponseEntity.ok(incidents);
  }

  @PostMapping(value = "/addIncident")
  public Incident addIncident(@RequestBody Incident incident) {
    Incident newIncident = service.addIncident(incident);
    return newIncident;
  }
}
