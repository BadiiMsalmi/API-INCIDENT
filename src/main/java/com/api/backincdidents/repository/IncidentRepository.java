package com.api.backincdidents.repository;

import com.api.backincdidents.model.Incident;
import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IncidentRepository extends JpaRepository<Incident, Integer> {
  
 
  @Query(
    "SELECT i FROM Incident i" 
  )
  public List<Incident> findAll(
    String assigne,
    String declarant,
    String status,
    Date date
  );
 

 public List<Incident> findByAssigne_FirstNameLikeAndDeclarant_FirstNameLikeAndStatus_LabelAndCreationdate(  String assigne,String declarant,String status,Date date);



}
