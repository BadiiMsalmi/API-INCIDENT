package com.api.backincdidents.repository;

import com.api.backincdidents.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
  
  public List<User> findByRoleLike(String role);

  public List<User> findByFirstNameLikeAndRoleLike(String firstName,String role);

  public User findById(int user_id);

  public User deleteById(int user_id);

}
