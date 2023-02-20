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

  public List<User> getAllAdmins(){
    return userRepo.getAllAdmins();
  }

  public List<User> getAllDeclarant(){
    return userRepo.getAllDeclarant();
  }

  public List<User> getUserByHint(String hint){
    return userRepo.getUsersByHint(hint);
  }
}
