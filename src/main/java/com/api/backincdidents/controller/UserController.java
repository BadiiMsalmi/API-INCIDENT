package com.api.backincdidents.controller;

import com.api.backincdidents.model.User;
import com.api.backincdidents.repository.UserRepository;
import com.api.backincdidents.service.UserService;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

  @Autowired
  private UserService userService;



  @Autowired
  private UserRepository userRepository;

  
  @GetMapping("/users")
  public List<User> getUsers() {
    List<User> user = userService.getAllUsers();
    return user;
  }

  
  @GetMapping("/admins")
  public List<User> getAdmins() {
    String role = "admin";
    List<User> user = userService.getAllAdmins(role);
    return user;
  }

  
  @GetMapping("/delarant")
  public List<User> getDeclarants() {
    String role = "declarant";
    List<User> user = userService.getAllDeclarant(role);
    return user;
  }

  // Autocomplete FOR ADMIN
  
  @GetMapping("/searchByAdmin")
  public List<User> searchByAdmin(@RequestParam String firstName) {
    String role = "Assigned";
    List<User> user = userRepository.findByFirstnameLikeAndRoleLike('%' + firstName + '%', role);
    System.out.println(firstName+"**************"+role);
    return user;
  }

  // Autocomplete FOR normal USER
  @GetMapping("/searchByUser")
  public List<User> searchByUser(@RequestParam String firstName) {
    String role = "Declarant";
    List<User> user = userRepository.findByFirstnameLikeAndRoleLike('%' + firstName + '%', role);
    return user;
  }

  
  @GetMapping("/user/{id}")
  public ResponseEntity<Object> getUserById(@PathVariable int id) {
    User user = userService.getUserById(id);
    if(user == null){
      String errorMessage = "No such user";
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
    return ResponseEntity.ok(user);
  }

  
  @DeleteMapping("/user/{id}")
  public String deleteUser(@PathVariable int id, HttpServletResponse response) {
    try {
      userService.deleteUser(id);
      return "User removed.";
    } catch (Exception exc) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Error removing the user.", exc);
    }
  }
  

  @GetMapping("/getUserByEmail")
  public User getUserByEmail(@RequestParam String email){
    return userService.getUserByEmailIgnoreCase(email);
  }
  
  
  @GetMapping("/getUserOpenTicket/{id}")
  public ResponseEntity<Object> getUserOpenTicketCount(@PathVariable int id){
    Integer count = userService.getOpenTicketsCount(id);
    if (count == null) {
      String errorMessage = "Error finding ticket count";
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
    return ResponseEntity.ok(count);
  }
}
