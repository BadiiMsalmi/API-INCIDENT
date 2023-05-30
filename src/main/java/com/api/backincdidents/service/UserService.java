package com.api.backincdidents.service;

import com.api.backincdidents.model.User;
import com.api.backincdidents.repository.IncidentRepository;
import com.api.backincdidents.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepo;
  
  @Autowired
  private IncidentRepository incidentRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

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

  public Optional<User> getUserByemail(String email){
    return userRepo.findByEmail(email);
  }

  public User deleteUser(int id){
    return userRepo.deleteById(id);
  }

  public boolean userExistsByEmail(String email){
    return userRepo.existsByEmail(email);
  }

  public User getUserByEmailIgnoreCase(String email) {
    return userRepo.findByEmailIgnoreCase(email);
  }

  public void updateUserPassword(User user, String password) {
    user.setPassword(passwordEncoder.encode(password));
    userRepo.save(user);
  }

  public Integer getOpenTicketsCount(int id){
    String label = "EnCour";
    System.out.print(incidentRepository.countByAssigne_IdAndStatus_Label(id, label)+"********kkkkkkkk*************************");
    return incidentRepository.countByAssigne_IdAndStatus_Label(id, label);
  }

  public User saveUser(User user){
    return userRepo.save(user);
  }


  public void updateUserOpenTickets(int userId, int openTickets) {
    User user = userRepo.findById(userId);
    user.setOpenTickets(openTickets);
    userRepo.save(user);
  }

  public User updateUser(User user) {
    return userRepo.save(user);
  }
}
