package com.api.backincdidents.service;

import com.api.backincdidents.model.User;
import com.api.backincdidents.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userService {

  @Autowired
  private UserRepository userRepo;

  public List<User> getAllUsers() {
    return userRepo.findAll();
  }

  public List<User> getAllAdmins(String role){
    return userRepo.findByRoleLike(role);
  }

  public List<User> getAllDeclarant(String role){
    return userRepo.findByRoleLike(role);
  }

  public User getUserById(int id){
    return userRepo.findById(id);
  }


  public User deleteUser(int id){
    return userRepo.deleteById(id);
  }
}
