package com.api.backincdidents.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.api.backincdidents.Dto.AccountResponse;
import com.api.backincdidents.Dto.NewPassword;
import com.api.backincdidents.Dto.ResetPassword;
import com.api.backincdidents.model.Affiliate;
import com.api.backincdidents.model.AuthenticationRequest;
import com.api.backincdidents.model.AuthenticationResponse;
import com.api.backincdidents.model.ConfirmationToken;
import com.api.backincdidents.model.RegisterRequest;
import com.api.backincdidents.model.RestorePasswordToken;
import com.api.backincdidents.model.Role;
import com.api.backincdidents.model.Status;
import com.api.backincdidents.model.User;
import com.api.backincdidents.repository.RoleRepository;
import com.api.backincdidents.repository.StatusRepository;
import com.api.backincdidents.repository.UserRepository;
import com.api.backincdidents.service.AffiliateService;
import com.api.backincdidents.service.AuthService;
import com.api.backincdidents.service.EmailService;
import com.api.backincdidents.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final StatusRepository statusRepository;
    private final AffiliateService affiliateService;



    @CrossOrigin("*")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @CrossOrigin("*")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @CrossOrigin("*")
    @GetMapping("/confirm-account")
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
        ConfirmationToken token = authService.getConfirmationToken(confirmationToken);
        if (token != null) {
            User user = userRepository.findByEmailIgnoreCase(token.getUserEntity().getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            modelAndView.setViewName("accountVerified");
        } else {
            modelAndView.addObject("message", "The link is invalid or broken!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    @CrossOrigin("*")
    @PostMapping("/checkEmail")
    public AccountResponse resetPasswordEmail(@RequestBody ResetPassword resetPassword) {
        User user = this.userRepository.findByEmailIgnoreCase(resetPassword.getEmail());
        AccountResponse accountResponse = new AccountResponse();
        if (user != null && user.isEnabled()) {
            RestorePasswordToken token = authService.generateToken(user);
            emailService.sendForgetPasswordEmail(user, token);
            accountResponse.setResult(1);
        } else {
            accountResponse.setResult(0);
        }
        return accountResponse;
    }

    @CrossOrigin("*")
    @PostMapping("/resetPassword")
    public AccountResponse resetPassword(@RequestBody NewPassword newPassword) {
        AccountResponse accountResponse = new AccountResponse();
        RestorePasswordToken restorePasswordToken = authService.getRestorePasswordToken(newPassword.getToken());
        if (restorePasswordToken != null && restorePasswordToken.getToken().equals(newPassword.getToken())) {
            User user = userService.getUserByEmailIgnoreCase(restorePasswordToken.getUserEntity().getEmail());
            userService.updateUserPassword(user, newPassword.getPassword());
            authService.deleteRestorePasswordToken(restorePasswordToken);
            accountResponse.setResult(1);
        } else {
            accountResponse.setResult(0);
        }
        return accountResponse;

    }

    @GetMapping("/allRoles")
  public List<Role> getAllRoles(){
    return roleRepository.findAll();
  }

  @GetMapping("/allStatus")
  public List<Status> getAllStatus(){
    return statusRepository.findAll();
  }

  @GetMapping("/getallaffiliates")
    public ResponseEntity<Object> getAllAffiliate(){
        List<Affiliate> affiliates = affiliateService.getAllAffiliates();

        if (affiliates.isEmpty()) {
            String errorMessage = "No affilates found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
          }
          
          return ResponseEntity.ok(affiliates);
    }
}
