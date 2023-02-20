package com.api.backincdidents.repository;

import com.api.backincdidents.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
  @Query("SELECT u FROM User u WHERE role LIKE '%admin%'")
  public List<User> getAllAdmins();

  @Query("SELECT u FROM User u WHERE role LIKE '%declarant%'")
  public List<User> getAllDeclarant();

  
  public List<User> getUsersByHint(String hint);
}
