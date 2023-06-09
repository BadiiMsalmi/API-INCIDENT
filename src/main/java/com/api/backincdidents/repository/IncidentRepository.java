package com.api.backincdidents.repository;

import java.time.LocalDate;
import java.util.Date;

import com.api.backincdidents.model.Affiliate;
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

    List<Incident> findByStatus_LabelOrStatus_LabelAndAssigne_Id(String label1, String label2,int id);

    int countByAssigne(User user);

    List<Incident> findByStatus_LabelAndCreationdateBetween(String status, LocalDate  startDate, LocalDate  endDate);

    List<Incident> findByStatus_LabelAndCreationdateBetweenAndAssigne_Id(String status, LocalDate  startDate, LocalDate  endDate,int id);

    int countByStatus(Status Status);

    int countByAssigne_IdAndStatus(int id,Status Status);

    List<Incident> findByStatus_LabelAndAssigne_Id(String label,int id);

    List<Incident> findByStatus_Label(String label);

    Integer countByAssigne_IdAndStatus_Label(int id,String status);

    int countByAssigne_Id(int id);

    int countByDeclarant_Affiliate(Affiliate a);

    int countByStatus_Label(String status);
}
