package com.api.backincdidents.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.backincdidents.Dto.AffiliateCountDTO;
import com.api.backincdidents.Dto.StatusRateDTO;
import com.api.backincdidents.Dto.UserWithTicketCount;
import com.api.backincdidents.model.Incident;
import com.api.backincdidents.model.User;
import com.api.backincdidents.service.StatsService;
import com.api.backincdidents.service.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/stats")
@RequiredArgsConstructor
public class StatistiquesController {

    @Autowired
    private StatsService statsService;

    @Autowired
    private UserService userService;


    @GetMapping("/openincidents")
    public ResponseEntity<Object> getOpenIncidents() {
        List<Incident> openIncidents = statsService.getOpenIncidents();
        if (openIncidents.isEmpty()) {
            String errorMessage = "No open incidents available";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
        return ResponseEntity.ok(openIncidents);
    }

    @GetMapping("/assignedStats")
    public List<UserWithTicketCount> getAssignedUsersWithIncidentCount() {
        return statsService.getAssignedUsersWithIncidentCount();
    }

    
    @PostMapping("/closureRate")
    public ResponseEntity<Double> calculateClosureRate(
        @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
        @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate) {

    double closureRate = statsService.calculateClosureRate(startDate, endDate);
    return ResponseEntity.ok(closureRate);
    }


    @GetMapping("/incidentsByStatusRate")
    public ResponseEntity<List<StatusRateDTO>> getIncidentsByStatusRate() {
        List<StatusRateDTO> incidentsByStatusRate = statsService.getIncidentsByStatusRate();
        return ResponseEntity.ok(incidentsByStatusRate);
    }

    @GetMapping("/listofusers")
    public ResponseEntity<Object> getAllUsers() {
        List<User> usersList = userService.getAllUsers();
        return ResponseEntity.ok(usersList);
    }

    @GetMapping("/averageIncidentAge")
    public ResponseEntity<Double> getAverageIncidentAge() {
        double averageAge = statsService.calculateAverageOpenIncidentAge();
        return ResponseEntity.ok(averageAge);
    }


    @GetMapping("/averageResolutionTime")
    public ResponseEntity<Double> calculateAverageResolutionTime() {
        double averageResolutionTime = statsService.calculateAverageResolutionTime();
        return ResponseEntity.ok(averageResolutionTime);
    }

    @GetMapping("/incidentsByAffiliteCount")
     public ResponseEntity<List<AffiliateCountDTO>> getIncidentsByAffiliteCount() {
         List<AffiliateCountDTO> incidentsByAffiliateCount = statsService.getIncidentsByAffiliateCount();
         return ResponseEntity.ok(incidentsByAffiliateCount);
     }

     @GetMapping("/getNumberOfSolvedTickets")
    public ResponseEntity<Double> getNumberOfSolvedTickets() {
        double averageResolutionTime = statsService.getNumberOfSolvedTickets();
        return ResponseEntity.ok(averageResolutionTime);
    }


    


     @GetMapping("/incidentsByStatusRateAssigned/{id}")
     public ResponseEntity<List<StatusRateDTO>> getIncidentsByStatusRateAssigned(@PathVariable("id") int id) {
         List<StatusRateDTO> incidentsByStatusRate = statsService.getIncidentsByStatusRateAssigned(id);
         return ResponseEntity.ok(incidentsByStatusRate);
     }

    @GetMapping("/averageIncidentAgeAssigned/{id}")
    public ResponseEntity<Double> getAverageIncidentAgeAssigned(@PathVariable("id") int id) {
        double averageAge = statsService.calculateAverageOpenIncidentAgeAssigned(id);
        return ResponseEntity.ok(averageAge);
    }

    @GetMapping("/averageResolutionTimeAssigned/{id}")
    public ResponseEntity<Double> calculateAverageResolutionTimeAssigned(@PathVariable("id") int id) {
        double averageResolutionTime = statsService.calculateAverageResolutionTimeAssigned(id);
        return ResponseEntity.ok(averageResolutionTime);
    }

    @PostMapping("/closureRateAssigned/{id}")
    public ResponseEntity<Double> calculateClosureRateAssigned(
        @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
        @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate,
        @PathVariable("id") int id) {

    double closureRate = statsService.calculateClosureRateAssigned(startDate, endDate,id);
    return ResponseEntity.ok(closureRate);
    }

    @GetMapping("/getNumberOfSolvedTickets/{id}")
    public ResponseEntity<Double> getNumberOfSolvedTicketsAssigned(@PathVariable("id") int id) {
        double averageResolutionTime = statsService.getNumberOfSolvedTicketsAssigned(id);
        return ResponseEntity.ok(averageResolutionTime);
    }

    
}
