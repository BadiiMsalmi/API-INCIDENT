package com.api.backincdidents.service;

import com.api.backincdidents.model.User;
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
}
