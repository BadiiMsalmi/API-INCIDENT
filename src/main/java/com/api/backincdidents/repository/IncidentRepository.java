package com.api.backincdidents.repository;

import java.time.LocalDate;
import java.util.Date;
import com.api.backincdidents.model.Incident;
import com.api.backincdidents.model.Status;
import com.api.backincdidents.model.User;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentRepository extends JpaRepository<Incident, Integer> {

    public List<Incident> findAll();

    public Incident findById(int id);

    public List<Incident> findByAssigne_FirstnameLikeAndDeclarant_FirstnameLikeAndStatus_LabelAndCreationdate(
            String assigne, String declarant, String status, Date date);

    @SuppressWarnings("unchecked")
    public Incident save(Incident incident);

    List<Incident> findByDeclarantEmailOrAssigneEmail(String email1, String email2);

    List<Incident> findByStatus_LabelOrStatus_Label(String label1, String label2);

    int countByAssigne(User user);

    List<Incident> findByStatus_LabelAndCreationdateBetween(String status, LocalDate  startDate, LocalDate  endDate);

    int countByStatus(Status Status);

    List<Incident> findByStatus_Label(String label);

    Integer countByAssigne_IdAndStatus_Label(int id,String status);

}
