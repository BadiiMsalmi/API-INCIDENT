package com.api.backincdidents.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.backincdidents.Dto.AffiliateCountDTO;
import com.api.backincdidents.Dto.StatusRateDTO;
import com.api.backincdidents.Dto.UserWithTicketCount;
import com.api.backincdidents.model.Affiliate;
import com.api.backincdidents.model.Incident;
import com.api.backincdidents.model.Status;
import com.api.backincdidents.model.User;
import com.api.backincdidents.repository.AffiliateRepository;
import com.api.backincdidents.repository.IncidentRepository;
import com.api.backincdidents.repository.StatusRepository;
import com.api.backincdidents.repository.UserRepository;

@Service
public class StatsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private AffiliateRepository affiliateRepository;
    public List<Incident> getOpenIncidents() {
        String status1 = "EnCour";
        String status2 = "Declancher";
        return incidentRepository.findByStatus_LabelOrStatus_Label(status1, status2);
    }

    public List<UserWithTicketCount> getAssignedUsersWithIncidentCount() {
        String role = "Assigned";
        List<User> assignedUsers = userRepository.findByRoleLike(role);
        List<UserWithTicketCount> result = new ArrayList<>();

        for (User user : assignedUsers) {
            int incidentCount = incidentRepository.countByAssigne(user);
            UserWithTicketCount userDTO = new UserWithTicketCount(user, incidentCount);
            result.add(userDTO);
        }

        return result;
    }

    public double calculateClosureRate(LocalDate startDate, LocalDate endDate) {
        String closedStatus = "Terminer";
        long totalIncidents = incidentRepository.count();
        long closedIncidents = incidentRepository
                .findByStatus_LabelAndCreationdateBetween(closedStatus, startDate, endDate)
                .size();
    
        if (totalIncidents == 0) {
            return 0.0; // Handle the case when there are no incidents
        }
    
        return (closedIncidents / (double) totalIncidents) * 100;
    }

    public double calculateClosureRateAssigned(LocalDate startDate, LocalDate endDate,int id) {
        String closedStatus = "Terminer";
        long totalIncidents = incidentRepository.countByAssigne_Id(id);
        long closedIncidents = incidentRepository
                .findByStatus_LabelAndCreationdateBetweenAndAssigne_Id(closedStatus, startDate, endDate,id)
                .size();
    
        if (totalIncidents == 0) {
            return 0.0; // Handle the case when there are no incidents
        }
    
        return (closedIncidents / (double) totalIncidents) * 100;
    }

    public List<StatusRateDTO> getIncidentsByStatusRate() {
        List<Status> statuses = statusRepository.findAll();
        List<StatusRateDTO> statusRates = new ArrayList<>();

        for (Status status : statuses) {
            long totalIncidents = incidentRepository.countByStatus(status);
            long totalAllIncidents = incidentRepository.count();

            double rate = (totalIncidents / (double) totalAllIncidents) * 100;

            StatusRateDTO statusRate = new StatusRateDTO();
            statusRate.setStatus(status.getLabel());
            statusRate.setRate(rate);

            statusRates.add(statusRate);
        }

        return statusRates;
    }

    public List<StatusRateDTO> getIncidentsByStatusRateAssigned(int id) {
        List<Status> statuses = statusRepository.findAll();
        List<StatusRateDTO> statusRates = new ArrayList<>();

        for (Status status : statuses) {
            long totalIncidents = incidentRepository.countByAssigne_IdAndStatus(id,status);
            long totalAllIncidents = incidentRepository.countByAssigne_Id(id);

            double rate = (totalIncidents / (double) totalAllIncidents) * 100;

            StatusRateDTO statusRate = new StatusRateDTO();
            statusRate.setStatus(status.getLabel());
            statusRate.setRate(rate);

            statusRates.add(statusRate);
        }

        return statusRates;
    }

    public List<AffiliateCountDTO> getIncidentsByAffiliateCount() {
        List<Affiliate> affiliates = affiliateRepository.findAll();
        List<AffiliateCountDTO> affiliateCounts = new ArrayList<>();

        for (Affiliate affiliate : affiliates) {
            long totalIncidents = incidentRepository.countByDeclarant_Affiliate(affiliate);


            AffiliateCountDTO affiliateCount = new AffiliateCountDTO();
            affiliateCount.setAffiliate(affiliate.getLabel());
            affiliateCount.setCount(totalIncidents);

            affiliateCounts.add(affiliateCount);
        }

        return affiliateCounts;
    }
    
    public double calculateAverageOpenIncidentAge() {
        String status1 = "EnCour";
        String status2 = "Declancher";
        List<Incident> openIncidents = incidentRepository.findByStatus_LabelOrStatus_Label(status1, status2);

        if (openIncidents.isEmpty()) {
            return 0.0; // Handle the case when there are no open incidents
        }

        long totalAgeInDays = 0;
        for (Incident incident : openIncidents) {
            long incidentAgeInDays = ChronoUnit.DAYS.between(incident.getCreationdate(), LocalDate.now());
            totalAgeInDays += incidentAgeInDays;
        }

        double averageAgeInDays = totalAgeInDays / (double) openIncidents.size();

        return averageAgeInDays;
    }

    public double calculateAverageOpenIncidentAgeAssigned(int id) {
        String status1 = "EnCour";
        String status2 = "Declancher";
        List<Incident> openIncidents = incidentRepository.findByStatus_LabelOrStatus_LabelAndAssigne_Id(status1, status2,id);

        if (openIncidents.isEmpty()) {
            return 0.0; // Handle the case when there are no open incidents
        }

        long totalAgeInDays = 0;
        for (Incident incident : openIncidents) {
            long incidentAgeInDays = ChronoUnit.DAYS.between(incident.getCreationdate(), LocalDate.now());
            totalAgeInDays += incidentAgeInDays;
        }

        double averageAgeInDays = totalAgeInDays / (double) openIncidents.size();

        return averageAgeInDays;
    }

    public double calculateAverageResolutionTime() {
        String resolvedStatus = "Terminer";
        List<Incident> resolvedIncidents = incidentRepository.findByStatus_Label(resolvedStatus);
    
        if (resolvedIncidents.isEmpty()) {
            return 0.0; // Handle the case when there are no resolved incidents
        }
    
        long totalResolutionTimeInMillis = 0;
        for (Incident incident : resolvedIncidents) {
            LocalDate resolutionDateTime = incident.getClosureDate();
            LocalDate creationDateTime = incident.getCreationdate();
            LocalDate currentDateTime = LocalDate.now();
    
            LocalDate resolvedDateTime = resolutionDateTime != null ? resolutionDateTime : currentDateTime;
            Duration resolutionTime = Duration.between(creationDateTime.atStartOfDay(), resolvedDateTime.atStartOfDay());
            totalResolutionTimeInMillis += resolutionTime.toDays() * 24 * 60 * 60 * 1000; // Convert to milliseconds
        }
    
        double averageResolutionTimeInDays = totalResolutionTimeInMillis / (resolvedIncidents.size() * 24 * 60 * 60 * 1000.0); // Convert to days
    
        return averageResolutionTimeInDays;
    }

    public double calculateAverageResolutionTimeAssigned(int id) {
        String resolvedStatus = "Terminer";
        List<Incident> resolvedIncidents = incidentRepository.findByStatus_LabelAndAssigne_Id(resolvedStatus,id);
    
        if (resolvedIncidents.isEmpty()) {
            return 0.0; // Handle the case when there are no resolved incidents
        }
    
        long totalResolutionTimeInMillis = 0;
        for (Incident incident : resolvedIncidents) {
            LocalDate resolutionDateTime = incident.getClosureDate();
            LocalDate creationDateTime = incident.getCreationdate();
            LocalDate currentDateTime = LocalDate.now();
    
            LocalDate resolvedDateTime = resolutionDateTime != null ? resolutionDateTime : currentDateTime;
            Duration resolutionTime = Duration.between(creationDateTime.atStartOfDay(), resolvedDateTime.atStartOfDay());
            totalResolutionTimeInMillis += resolutionTime.toDays() * 24 * 60 * 60 * 1000; // Convert to milliseconds
        }
    
        double averageResolutionTimeInDays = totalResolutionTimeInMillis / (resolvedIncidents.size() * 24 * 60 * 60 * 1000.0); // Convert to days
    
        return averageResolutionTimeInDays;
    }

    public Integer getNumberOfSolvedTicketsAssigned(int id){
        String status = "Terminer";
        return this.incidentRepository.countByAssigne_IdAndStatus_Label(id,status);

    }

    public Integer getNumberOfSolvedTickets(){
        String status = "Terminer";
        return this.incidentRepository.countByStatus_Label(status);

    }

}
