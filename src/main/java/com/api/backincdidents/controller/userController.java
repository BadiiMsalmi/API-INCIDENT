package com.api.backincdidents.controller;

import com.api.backincdidents.model.User;
import com.api.backincdidents.repository.UserRepository;
import com.api.backincdidents.service.userService;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class userController {

  @Autowired
  private userService service;

  @Autowired
  private UserRepository repo;

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
  public List<User> getUserByHint(@Param("firstName") String firstName){
    List<User> user = service.getUserByHint(firstName);
    return user;
  }


  // FOR ADMIN
  @CrossOrigin("*")
  @GetMapping("/searchByAdmin")
  public List<User> xxx(@Param("firstName") String firstName){
    List<User> user = repo.findByFirstNameLikeAndRoleLike('%'+firstName+'%',"admin");
    return user;
  }


  // FOR USER 
  @CrossOrigin("*")
  @GetMapping("/searchByUser")
  public List<User> xx(@Param("firstName") String firstName){
    List<User> user = repo.findByFirstNameLikeAndRoleLike('%'+firstName+'%',"declarant");
    System.out.println(firstName);
    return user;
  }


}
