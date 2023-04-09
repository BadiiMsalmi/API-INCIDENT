package com.api.backincdidents.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.api.backincdidents.model.AuthenticationRequest;
import com.api.backincdidents.model.AuthenticationResponse;
import com.api.backincdidents.model.ConfirmationToken;
import com.api.backincdidents.model.RegisterRequest;
import com.api.backincdidents.model.RestorePasswordToken;
import com.api.backincdidents.model.User;
import com.api.backincdidents.repository.ConfirmationTokenRepository;
import com.api.backincdidents.repository.RestorePasswordRepository;
import com.api.backincdidents.repository.UserRepository;
import com.api.backincdidents.service.AuthService;
import com.api.backincdidents.service.EmailService;

import lombok.RequiredArgsConstructor;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserRepository userRepository;
    private final RestorePasswordRepository restorePasswordRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    
    
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/confirm-account")
    public ModelAndView confirmUserAccount(ModelAndView modelAndView,@RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
        	User user = userRepository.findByEmailIgnoreCase(token.getUserEntity().getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            modelAndView.setViewName("accountVerified");
        }
        else
        {
            modelAndView.addObject("message","The link is invalid or broken!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    @PostMapping("/forgetpassword") //
    public AccountResponse resetPasswordEmail(@RequestBody ResetPassword resetPassword){
        User user = this.userRepository.findByEmailIgnoreCase(resetPassword.getEmail());
        AccountResponse accountResponse = new AccountResponse();
        if(user != null && user.isEnabled()){
            RestorePasswordToken Token = new RestorePasswordToken(user);
            restorePasswordRepository.save(Token);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Forget password!");
            mailMessage.setFrom("admibot69@outlook.fr");
            mailMessage.setText("Hello,"+user.getFirstname()+"\nHere's your code to change your password : "+Token.getToken());
            emailService.sendEmail(mailMessage);
            accountResponse.setResult(1);
        }else{
            accountResponse.setResult(0);
        }
        return accountResponse;
    }


    @PostMapping("/resetPassword")
    public AccountResponse resetPassword(@RequestBody NewPassword newPassword){
        AccountResponse accountResponse = new AccountResponse();
        RestorePasswordToken restorePasswordToken = restorePasswordRepository.findByToken(newPassword.getToken());
        if(restorePasswordToken != null && restorePasswordToken.getToken().equals(newPassword.getToken())){
            User user = userRepository.findByEmailIgnoreCase(restorePasswordToken.getUserEntity().getEmail());
            user.setPassword(passwordEncoder.encode(newPassword.getPassword()));
            userRepository.save(user);
            accountResponse.setResult(1);
        }else{
            accountResponse.setResult(0);
        }
        return accountResponse;

    }
}
