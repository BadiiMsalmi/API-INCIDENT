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
    String role = "admin";
    List<User> user = service.getAllAdmins(role);
    return user;
  }

  @GetMapping("/delarant")
  public List<User> getDeclarants() {
    String role = "declarant";
    List<User> user = service.getAllDeclarant(role);
    return user;
  }

  // Autocomplete FOR ADMIN
  @CrossOrigin("*")
  @GetMapping("/searchByAdmin")
  public List<User> searchByAdmin(@Param("firstName") String firstName){
    List<User> user = repo.findByFirstNameLikeAndRoleLike('%'+firstName+'%',"admin");
    return user;
  }


  // Autocomplete FOR normal USER 
  @CrossOrigin("*")
  @GetMapping("/searchByUser")
  public List<User> searchByUser(@Param("firstName") String firstName){
    List<User> user = repo.findByFirstNameLikeAndRoleLike('%'+firstName+'%',"declarant");
    System.out.println(firstName);
    return user;
  }


}
