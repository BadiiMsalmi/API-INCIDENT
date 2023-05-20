package com.api.backincdidents.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    // trajaa3lek les ticket eli ma7loulin ma tsakrouch
    @GetMapping("/openincidents")
    public ResponseEntity<Object> getOpenIncidents() {
        List<Incident> openIncidents = statsService.getOpenIncidents();
        if (openIncidents.isEmpty()) {
            String errorMessage = "No open incidents available";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
        return ResponseEntity.ok(openIncidents);
    }

    // trajaalek les assigned w 9adech men ticket aandhom
    @GetMapping("/assignedStats")
    public List<UserWithTicketCount> getAssignedUsersWithIncidentCount() {
        return statsService.getAssignedUsersWithIncidentCount();
    }

    // taa3tiik pourcentage mtaa el incidents eli tsakrou fel periode eli theb
    // aaliha
    @PostMapping("/closureRate")
    public ResponseEntity<Double> calculateClosureRate(
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedStartDate = dateFormat.format(startDate);
        String formattedEndDate = dateFormat.format(endDate);

        double closureRate = statsService.calculateClosureRate(formattedStartDate, formattedEndDate);
        return ResponseEntity.ok(closureRate);
    }

    // trajaalek kol status w 9adeh mn incident fel status adhika bel pourcentege
    @GetMapping("/incidentsByStatusRate")
    public ResponseEntity<List<StatusRateDTO>> getIncidentsByStatusRate() {
        List<StatusRateDTO> incidentsByStatusRate = statsService.getIncidentsByStatusRate();
        return ResponseEntity.ok(incidentsByStatusRate);
    }

    // trajaalek lista mtaa el users el mawjoudin
    @GetMapping("/listofusers")
    public ResponseEntity<Object> getAllUsers() {
        List<User> usersList = userService.getAllUsers();
        return ResponseEntity.ok(usersList);
    }

    // trajaalek 9adeh el average age mtaa el incidetents el ma7loulin mazelou matsakrouch bel date
    @GetMapping("/averageIncidentAge")
    public ResponseEntity<Double> getAverageIncidentAge() {
        double averageAge = statsService.calculateAverageOpenIncidentAge();
        return ResponseEntity.ok(averageAge);
    }

    // Calculate average resolution time in days lel incidents eli tsakrou sayee
    @GetMapping("/averageResolutionTime")
    public ResponseEntity<Double> calculateAverageResolutionTime() {
        double averageResolutionTime = statsService.calculateAverageResolutionTime();
        return ResponseEntity.ok(averageResolutionTime);
    }
}
