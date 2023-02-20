package com.api.backincdidents.repository;

import com.api.backincdidents.model.Incident;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentRepository extends JpaRepository<Incident, Integer> {
  // @Query()
  // public List<Incident> findBy(
  //   String assigne,
  //   String declarant,
  //   String status,
  //   Date date
  // );

  public List<Incident> findAll();
}
