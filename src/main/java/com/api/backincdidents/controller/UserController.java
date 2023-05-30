package com.api.backincdidents.controller;

import com.api.backincdidents.model.ImageModel;
import com.api.backincdidents.model.User;
import com.api.backincdidents.repository.ImageRepository;
import com.api.backincdidents.repository.UserRepository;
import com.api.backincdidents.service.UserService;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private ImageRepository imageRepository;

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
  public String deleteUserById(@PathVariable int id, HttpServletResponse response) {
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


  @PutMapping("/userUpdate/{id}")
  public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User user) {
    User existingUser = userService.getUserById(id);
    if (existingUser == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    if (user.getAffiliate() != null) {
      existingUser.setAffiliate(user.getAffiliate());
    }
    if (user.getEmail() != null) {
      existingUser.setEmail(user.getEmail());
    }
    if (user.getFirstname() != null) {
      existingUser.setFirstname(user.getFirstname());
    }
    if (user.getLastname() != null) {
      existingUser.setLastname(user.getLastname());
    }
    User updatedUser = userService.updateUser(existingUser);
    return new ResponseEntity<>(updatedUser, HttpStatus.OK);
  }

  @PostMapping("/upload")
  public Optional<ImageModel> uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
    System.out.println("Original Image Byte Size -  " + file.getBytes().length);
    ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(),
        compressBytes(file.getBytes()));
    this.imageRepository.save(img);
    return this.imageRepository.findById(img.getId());
  }

  public static byte[] compressBytes(byte[] data) {
    Deflater deflater = new Deflater();
    deflater.setInput(data);
    deflater.finish();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] buffer = new byte[1024];
    while (!deflater.finished()) {
      int count = deflater.deflate(buffer);
      outputStream.write(buffer, 0, count);
    }
    try {
      outputStream.close();
    } catch (IOException e) {
    }
    System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

    return outputStream.toByteArray();
  }

  @GetMapping(path = { "/get/{id}" })
  public ImageModel getImage(@PathVariable("id") Long id) throws IOException {

    final Optional<ImageModel> retrievedImage = imageRepository.findById(id);
    ImageModel img = new ImageModel(retrievedImage.get().getName(), retrievedImage.get().getType(),
        decompressBytes(retrievedImage.get().getPicByte()));
    return img;
  }

  public static byte[] decompressBytes(byte[] data) {
    Inflater inflater = new Inflater();
    inflater.setInput(data);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] buffer = new byte[4096];
    try {
      while (!inflater.finished()) {
        int count = inflater.inflate(buffer);
        outputStream.write(buffer, 0, count);
      }
      outputStream.close();
    } catch (IOException ioe) {
    } catch (DataFormatException e) {
    }
    return outputStream.toByteArray();
  }

}
