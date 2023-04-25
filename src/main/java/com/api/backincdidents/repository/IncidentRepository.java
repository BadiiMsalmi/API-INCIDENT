package com.api.backincdidents.repository;
import java.sql.Date;
import com.api.backincdidents.model.Incident;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentRepository extends JpaRepository<Incident, Integer> {


public List<Incident> findAll();
public Incident findById(int id);
public List<Incident> findByAssigne_FirstnameLikeAndDeclarant_FirstnameLikeAndStatus_LabelAndCreationdate(  String assigne,String declarant,String status,Date date);
@SuppressWarnings("unchecked")
public Incident save(Incident incident);



}

