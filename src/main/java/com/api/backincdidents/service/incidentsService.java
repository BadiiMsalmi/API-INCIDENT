package com.api.backincdidents.service;

import com.api.backincdidents.model.Incident;
import com.api.backincdidents.repository.IncidentRepository;
import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class incidentsService {

  @Autowired
  private IncidentRepository repo;

  public List<Incident> listAll(
    String assigne,
    String declarant,
    String status,
    Date date
  ) {
    if (
      assigne != null || declarant != null || status != null || date != null
    ) {
      return repo.findAll(assigne, declarant, status, date);
    }
    return repo.findAll();
  }

  public Incident get(int id) {
    return repo.findById(id).get();
  }
}
