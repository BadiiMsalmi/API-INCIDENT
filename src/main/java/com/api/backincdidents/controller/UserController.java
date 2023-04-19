package com.api.backincdidents.controller;

import com.api.backincdidents.model.Role;
import com.api.backincdidents.model.Status;
import com.api.backincdidents.model.User;
import com.api.backincdidents.repository.RoleRepository;
import com.api.backincdidents.repository.StatusRepository;
import com.api.backincdidents.repository.UserRepository;
import com.api.backincdidents.service.UserService;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;

import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

  @Autowired
  private UserService service;

  @Autowired
  private StatusRepository statusRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private UserRepository repo;

  
  @GetMapping("/users")
  public List<User> getUsers() {
    List<User> user = repo.findAll();
    System.out.print(user);
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
  
  @GetMapping("/searchByAdmin")
  public List<User> searchByAdmin(@Param("firstName") String firstName) {
    List<User> user = repo.findByFirstnameLikeAndRoleLike('%' + firstName + '%', "USER");
    System.out.println(firstName+"**************");
    return user;
  }

  // Autocomplete FOR normal USER
  
  @GetMapping("/searchByUser")
  public List<User> searchByUser(@Param("firstName") String firstName) {
    List<User> user = repo.findByFirstnameLikeAndRoleLike('%' + firstName + '%', "USER");
    System.out.println(firstName);
    return user;
  }

  
  @GetMapping("/user/{id}")
  public ResponseEntity<Object> getUserById(@PathVariable int id) {
    User user = service.getUserById(id);
    if(user == null){
      String errorMessage = "No such user";
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
    return ResponseEntity.ok(user);
  }

  
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
  
  
  @GetMapping("/allStatus")
  public List<Status> getAllStatus(){
    return statusRepository.findAll();
  }

  
  @GetMapping("/allRoles")
  public List<Role> getAllRoles(){
    return roleRepository.findAll();
  }


}
