package com.api.backincdidents.controller;

import com.api.backincdidents.model.User;
import com.api.backincdidents.repository.UserRepository;
import com.api.backincdidents.service.UserService;

import javax.servlet.http.HttpServletResponse;

import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController {

  @Autowired
  private UserService service;

  @Autowired
  private UserRepository repo;

  @GetMapping("/users")
  public List<User> getUsers() {
    List<User> user = service.getAllUsers();
    return user;
  }

  @GetMapping("/admins")
  public List<User> getAdmins() {
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
  public List<User> searchByAdmin(@Param("firstName") String firstName) {
    List<User> user = repo.findByFirstNameLikeAndRoleLike('%' + firstName + '%', "admin");
    return user;
  }

  // Autocomplete FOR normal USER
  @CrossOrigin("*")
  @GetMapping("/searchByUser")
  public List<User> searchByUser(@Param("firstName") String firstName) {
    List<User> user = repo.findByFirstNameLikeAndRoleLike('%' + firstName + '%', "declarant");
    System.out.println(firstName);
    return user;
  }

  @CrossOrigin("*")
  @GetMapping("/user/{id}")
  public User getUserById(@PathVariable int id) {
    User user = service.getUserById(id);
    return user;
  }

  @CrossOrigin("*")
  @DeleteMapping("/user/{id}")
  public String deleteUser(@PathVariable int id, HttpServletResponse response) {
    try {
      service.deleteUser(id);
      return "User removed.";
    } catch (Exception exc) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Error removing the user.", exc);
    }
  }
}
