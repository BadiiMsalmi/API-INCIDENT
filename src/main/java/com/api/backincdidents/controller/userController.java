package com.api.backincdidents.controller;

import com.api.backincdidents.model.User;
import com.api.backincdidents.service.userService;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class userController {

  @Autowired
  private userService service;

  @GetMapping("/users")
  public List<User> getUsers() {
    List<User> user = service.getAllUsers();
    return user;
  }

  @GetMapping("/admins")
  public List<User> getAdmins( ) {
    List<User> user = service.getAllAdmins();
    return user;
  }

  @GetMapping("/delarant")
  public List<User> getDeclarants() {
    List<User> user = service.getAllDeclarant();
    return user;
  }

  @GetMapping("/userHint")
  public List<User> getUserByHint(@Param("hint") String hint){
    List<User> user = service.getUserByHint(hint);
    return user;
  }
}
