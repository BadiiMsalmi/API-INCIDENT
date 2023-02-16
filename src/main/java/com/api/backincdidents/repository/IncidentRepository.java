package com.api.backincdidents.repository;

import com.api.backincdidents.model.Incident;
import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IncidentRepository extends JpaRepository<Incident, Integer> {
  @Query(
    "SELECT i FROM Incident i" +
    " INNER JOIN Status s ON i.status = s.status_id" +
    " INNER JOIN User u ON i.declarant = u.user_id" +
    " INNER JOIN User u1 ON i.assigne = u1.user_id" +
    " WHERE (:status is null or s.label LIKE :status)" +
    " AND ( :date is null or i.CreationDate = :date)" +
    " AND ( :declarant is null or (u.firstName LIKE  :declarant  OR u.lastName LIKE  :declarant)) " +
    " AND ( :assigne is null or (u1.firstName LIKE :assigne  OR u1.lastName LIKE  :assigne))"
  )
  public List<Incident> findAll(
    String assigne,
    String declarant,
    String status,
    Date date
  );
}
