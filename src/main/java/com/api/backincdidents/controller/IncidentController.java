package com.api.backincdidents.controller;

import com.api.backincdidents.Dto.FilterDto;
import com.api.backincdidents.Dto.WhereDto;
import com.api.backincdidents.model.ImageModel;
import com.api.backincdidents.model.Incident;
import com.api.backincdidents.model.Notification;
import com.api.backincdidents.model.Status;
import com.api.backincdidents.model.User;
import com.api.backincdidents.repository.ImageRepository;
import com.api.backincdidents.service.IncidentService;
import com.api.backincdidents.service.NotificationService;
import com.api.backincdidents.service.UserService;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

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

  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  private NotificationService notificationService;

  @Autowired
  private UserService userService;

  @GetMapping("/incidents")
  public ResponseEntity<Object> getAllIncidents() {
    List<Incident> incidents = service.findAll();
    if (incidents == null) {
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

  @PostMapping("/searcha")
  public ResponseEntity<Object> searcha(@RequestParam String email, @RequestBody FilterDto filter) {
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
        LocalDate creationDate = LocalDate.parse(modalities.get(0));
        predicates.add(criteriaBuilder.equal(root.get("creationdate"),creationDate));
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

  @PostMapping("/searchd")
  public ResponseEntity<Object> searchd(@RequestParam String email, @RequestBody FilterDto filter) {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<Incident> criteriaQuery = criteriaBuilder.createQuery(Incident.class);
    Root<Incident> root = criteriaQuery.from(Incident.class);
    List<Predicate> predicates = new ArrayList<>();

    Join<Incident, User> assigneJoin = root.join("declarant");
    predicates.add(criteriaBuilder.equal(assigneJoin.get("email"), email));

    for (WhereDto where : filter.getWhere()) {
      String field = where.getField();
      List<String> modalities = where.getModalities();

      if (field.equals("assigne")) {
        Join<Incident, User> declarantJoin = root.join("assigne");
        predicates.add(criteriaBuilder.equal(declarantJoin.get("firstname"), modalities.get(0)));
      } else if (field.equals("creationdate")) {
        LocalDate creationDate = LocalDate.parse(modalities.get(0));
        predicates.add(criteriaBuilder.equal(root.get("creationdate"),creationDate));
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
        LocalDate creationDate = LocalDate.parse(modalities.get(0));
        predicates.add(criteriaBuilder.equal(root.get("creationdate"),creationDate));
      } else if (field.equals("status")) {
        Join<Incident, Status> statusJoin = root.join("status");
        predicates.add(criteriaBuilder.equal(statusJoin.get("label"), modalities.get(0)));
      } else if (field.equals("closureDate")) {
        predicates.add(criteriaBuilder.equal(root.get("closureDate"), modalities.get(0)));
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
  public ResponseEntity<Incident> updateIncident(@PathVariable("id") int id, @RequestBody Incident incident) {
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

      if (incident.getStatus().getLabel().equals("EnCour")) {

        Notification newNotification = new Notification();
        newNotification.setDate(LocalDate.now());
        newNotification.setTime(LocalTime.now());
        newNotification.setText("Your ticket has been updated.");
        newNotification.setDeclarant(existingIncident.getDeclarant());
        notificationService.saveNotification(newNotification);

      } else if (incident.getStatus().getLabel().equals("Terminer")) {

        Notification newNotification = new Notification();
        newNotification.setDate(LocalDate.now());
        newNotification.setTime(LocalTime.now());
        newNotification.setText("Your ticket has been fixed.");
        newNotification.setDeclarant(existingIncident.getDeclarant());
        notificationService.saveNotification(newNotification);

        existingIncident.setClosureDate(LocalDate.now());

      }
    }
    if (incident.getDeclarant() != null) {
      existingIncident.setDeclarant(incident.getDeclarant());
    }
    if (incident.getAssigne() != null) {
      existingIncident.setAssigne(incident.getAssigne());

      Notification newNotification = new Notification();
      newNotification.setDate(LocalDate.now());
      newNotification.setTime(LocalTime.now());
      newNotification.setText("A new ticket has been assigned to you.");
      newNotification.setAssigne(incident.getAssigne());
      notificationService.saveNotification(newNotification);

      Notification newNotification1 = new Notification();
      newNotification1.setDate(LocalDate.now());
      newNotification1.setTime(LocalTime.now());
      newNotification1.setText("Your ticket has been updated.");
      newNotification1.setDeclarant(existingIncident.getDeclarant());
      notificationService.saveNotification(newNotification1);

    }if (incident.getTimeLimit() != null) {
      existingIncident.setTimeLimit(incident.getTimeLimit());
    }

    Incident updatedIncident = service.updateIncident(existingIncident);
    return new ResponseEntity<>(updatedIncident, HttpStatus.OK);
  }

  @GetMapping("/incidentsByUser")
  public ResponseEntity<Object> getIncidentsForUser(@RequestParam Map<String, String> requestBody) {
    String email = requestBody.get("email");
    System.out.println(email);
    List<Incident> incidents = service.getIncidentsForUser(email);
    if (incidents == null) {
      String errorMessage = "No incidents available";
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
    return ResponseEntity.ok(incidents);
  }

  @PostMapping(value = "/addIncident")
  public ResponseEntity<Object> addIncident(@RequestBody Incident incident) {
    Incident newIncident = service.addIncident(incident);

    if (newIncident == null) {
      String errorMessage = "Error adding the ticket";
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    User admin = userService.getUserById(65);

    Notification newNotification = new Notification();
    newNotification.setDate(LocalDate.now());
    newNotification.setTime(LocalTime.now());
    newNotification.setText("A new ticket has been added.");
    newNotification.setAdmin(admin);

    notificationService.saveNotification(newNotification);

    return ResponseEntity.ok(newIncident);
  }

  @PostMapping("/upload")
  public Optional<ImageModel> uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
    System.out.println("Original Image Byte Size -  " + file.getBytes().length);
    ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(),
        compressBytes(file.getBytes()));
    this.imageRepository.save(img);
    return this.imageRepository.findById(img.getId());
  }

  public static byte[] compressBytes(byte[] data) {
    Deflater deflater = new Deflater();
    deflater.setInput(data);
    deflater.finish();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] buffer = new byte[1024];
    while (!deflater.finished()) {
      int count = deflater.deflate(buffer);
      outputStream.write(buffer, 0, count);
    }
    try {
      outputStream.close();
    } catch (IOException e) {
    }
    System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

    return outputStream.toByteArray();
  }

  @GetMapping(path = { "/get/{id}" })
  public ImageModel getImage(@PathVariable("id") Long id) throws IOException {

    final Optional<ImageModel> retrievedImage = imageRepository.findById(id);
    ImageModel img = new ImageModel(retrievedImage.get().getName(), retrievedImage.get().getType(),
        decompressBytes(retrievedImage.get().getPicByte()));
    return img;
  }

  public static byte[] decompressBytes(byte[] data) {
    Inflater inflater = new Inflater();
    inflater.setInput(data);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] buffer = new byte[4096];
    try {
      while (!inflater.finished()) {
        int count = inflater.inflate(buffer);
        outputStream.write(buffer, 0, count);
      }
      outputStream.close();
    } catch (IOException ioe) {
    } catch (DataFormatException e) {
    }
    return outputStream.toByteArray();
  }

  @PostMapping("/reportAdmin")
  public ResponseEntity<Object> report(@RequestBody FilterDto filter) {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<Incident> criteriaQuery = criteriaBuilder.createQuery(Incident.class);
    Root<Incident> root = criteriaQuery.from(Incident.class);
    List<Predicate> predicates = new ArrayList<>();
    
    LocalDate startDate = null;
    LocalDate endDate = null;
    
    for (WhereDto where : filter.getWhere()) {
      String field = where.getField();
      List<String> modalities = where.getModalities();
    
      if (field.equals("startDate")) {
        startDate = LocalDate.parse(modalities.get(0));
      } else if (field.equals("endDate")) {
        endDate = LocalDate.parse(modalities.get(0));
      } else if (field.equals("status")) {
        Join<Incident, Status> statusJoin = root.join("status");
        predicates.add(criteriaBuilder.equal(statusJoin.get("label"), modalities.get(0)));
      } 
    }
    
    if (startDate != null && endDate != null) {
      predicates.add(criteriaBuilder.between(root.get("creationdate"), startDate, endDate));
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

  
  @PostMapping("/reportDeclarant")
  public ResponseEntity<Object> reportD(@RequestParam String email,@RequestBody FilterDto filter) {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<Incident> criteriaQuery = criteriaBuilder.createQuery(Incident.class);
    Root<Incident> root = criteriaQuery.from(Incident.class);
    List<Predicate> predicates = new ArrayList<>();
    
    Join<Incident, User> assigneJoin = root.join("declarant");
    predicates.add(criteriaBuilder.equal(assigneJoin.get("email"), email));
    
    LocalDate startDate = null;
    LocalDate endDate = null;
    
    for (WhereDto where : filter.getWhere()) {
      String field = where.getField();
      List<String> modalities = where.getModalities();
    
      if (field.equals("startDate")) {
        startDate = LocalDate.parse(modalities.get(0));
      } else if (field.equals("endDate")) {
        endDate = LocalDate.parse(modalities.get(0));
      } else if (field.equals("status")) {
        Join<Incident, Status> statusJoin = root.join("status");
        predicates.add(criteriaBuilder.equal(statusJoin.get("label"), modalities.get(0)));
      } 
    }
    
    if (startDate != null && endDate != null) {
      predicates.add(criteriaBuilder.between(root.get("creationdate"), startDate, endDate));
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


  @PostMapping("/reportAssigne")
  public ResponseEntity<Object> reportA(@RequestParam String email,@RequestBody FilterDto filter) {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<Incident> criteriaQuery = criteriaBuilder.createQuery(Incident.class);
    Root<Incident> root = criteriaQuery.from(Incident.class);
    List<Predicate> predicates = new ArrayList<>();
    
    Join<Incident, User> assigneJoin = root.join("assigne");
    predicates.add(criteriaBuilder.equal(assigneJoin.get("email"), email));
    
    LocalDate startDate = null;
    LocalDate endDate = null;
    
    for (WhereDto where : filter.getWhere()) {
      String field = where.getField();
      List<String> modalities = where.getModalities();
    
      if (field.equals("startDate")) {
        startDate = LocalDate.parse(modalities.get(0));
      } else if (field.equals("endDate")) {
        endDate = LocalDate.parse(modalities.get(0));
      } else if (field.equals("status")) {
        Join<Incident, Status> statusJoin = root.join("status");
        predicates.add(criteriaBuilder.equal(statusJoin.get("label"), modalities.get(0)));
      } 
    }
    
    if (startDate != null && endDate != null) {
      predicates.add(criteriaBuilder.between(root.get("creationdate"), startDate, endDate));
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
  
}
