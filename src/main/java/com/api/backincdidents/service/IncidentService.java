package com.api.backincdidents.service;

import com.api.backincdidents.model.Incident;
import com.api.backincdidents.repository.IncidentRepository;
import java.util.List;
import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncidentService {

  @Autowired
  private IncidentRepository repo;

  public Incident addIncident(Incident incident) {
    return repo.save(incident);
  }

  public List<Incident> findAll() {
    return repo.findAll();
  }

  public Incident getIncidentById(int id) {
    return repo.findById(id);
  }

  public List<Incident> searchIncidents(String assigne ,String  declarant,String status,Date date){
    return repo.findByAssigne_FirstnameLikeAndDeclarant_FirstnameLikeAndStatus_LabelAndCreationdate('%' + assigne + '%','%' + declarant + '%',status,date);
  }

  public Incident updateIncident(Incident incident) {
    return repo.save(incident);
  }

  public List<Incident> getIncidentsForUser(String email) {
    return repo.findByDeclarantEmailOrAssigneEmail(email, email);
}


}
