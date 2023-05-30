package com.api.backincdidents.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.backincdidents.enumm.TokenType;
import com.api.backincdidents.model.AuthenticationRequest;
import com.api.backincdidents.model.AuthenticationResponse;
import com.api.backincdidents.model.ConfirmationToken;
import com.api.backincdidents.model.RegisterRequest;
import com.api.backincdidents.model.RestorePasswordToken;
import com.api.backincdidents.model.Token;
import com.api.backincdidents.model.User;
import com.api.backincdidents.repository.ConfirmationTokenRepository;
import com.api.backincdidents.repository.RestorePasswordRepository;
import com.api.backincdidents.repository.TokenRepository;
import com.api.backincdidents.repository.UserRepository;
import com.api.backincdidents.validators.ObjectsValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

        private final ConfirmationTokenRepository confirmationTokenRepository;
        private final UserRepository repository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
        private final TokenRepository tokenRepository;
        private final ObjectsValidator<RegisterRequest> registerRequestValidator;
        private final ObjectsValidator<AuthenticationRequest> authenticationRequestValidator;
        private final EmailService emailService;
        private final RestorePasswordRepository restorePasswordRepository;

        public AuthenticationResponse register(RegisterRequest request) {
                var violations = registerRequestValidator.validate(request);
                if (!violations.isEmpty()) {
                        return AuthenticationResponse.builder()
                                        .error(String.join(" \n ", violations))
                                        .build();
                }
                if (repository.existsByEmail(request.getEmail())) {
                        return AuthenticationResponse.builder()
                                        .error("Email already in use")
                                        .build();
                }
                var user = User.builder()
                                .firstname(request.getFirstname())
                                .lastname(request.getLastname())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role(request.getRole())
                                .affiliate(request.getAffiliate())
                                .image(request.getImage())
                                .build();
                var savedUser = repository.save(user);
                ConfirmationToken confirmationToken = new ConfirmationToken(user);
                confirmationTokenRepository.save(confirmationToken);
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(user.getEmail());
                mailMessage.setSubject("Complete Registration!");
                mailMessage.setFrom("3KxCLFjkmZ9bN7e@mail.com");
                mailMessage.setText("Hello,"+user.getFirstname()+"\nTo confirm your account, please click here : http://localhost:8080/api/v1/auth/confirm-account?token="+confirmationToken.getConfirmationToken());
                emailService.sendEmail(mailMessage);
                var jwtToken = jwtService.generateToken(user);
                saveUserToken(savedUser, jwtToken);
                return AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build();
        }

        private void revokeAllUserToken(User user) {
                var validUserToken = tokenRepository.findByUserIdAndExpiredIsFalseAndRevokedIsFalse(user.getId());
                if (validUserToken.isEmpty())
                        return;
                validUserToken.forEach(t -> {
                        t.setExpired(true);
                        t.setRevoked(true);
                });
                tokenRepository.saveAll(validUserToken);

        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                var violations = authenticationRequestValidator.validate(request);
                if (!violations.isEmpty()) {
                        return AuthenticationResponse.builder()
                                        .error(String.join(" \n ", violations))
                                        .build();
                }
                try {
                        authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(
                                                        request.getEmail(),
                                                        request.getPassword()));
                                                        
                } catch (AuthenticationException ex) {
                        var user = repository.findByEmail(request.getEmail())
                                                        .orElseThrow();
                                    
                if(!user.isEnabled()){
                        return AuthenticationResponse.builder()
                        .error("Account not activated, check email.")
                        .build();   
                }
                if(!request.getPassword().equals(user.getPassword())){
                        return AuthenticationResponse.builder()
                        .error("Invalid email or password, Please check again.")
                        .build();
                }
                }
                var user = repository.findByEmail(request.getEmail())
                                                        .orElseThrow();
                var jwtToken = jwtService.generateToken(user);
                revokeAllUserToken(user);
                saveUserToken(user, jwtToken);
                return AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build();
        }

        private void saveUserToken(User user, String jwtToken) {
                var token = Token.builder()
                                .user(user)
                                .token(jwtToken)
                                .tokenType(TokenType.BEARER)
                                .expired(false)
                                .revoked(false)
                                .build();
                tokenRepository.save(token);
        }

        
    public ConfirmationToken getConfirmationToken(String confirmationToken) {
        return confirmationTokenRepository.findByConfirmationToken(confirmationToken);
    }
            
    public RestorePasswordToken generateToken(User user) {
        RestorePasswordToken token = new RestorePasswordToken(user);
        return restorePasswordRepository.save(token);
    }

    public RestorePasswordToken getRestorePasswordToken(String token) {
        return restorePasswordRepository.findByToken(token);
    }

    public void deleteRestorePasswordToken(RestorePasswordToken token) {
        restorePasswordRepository.delete(token);
    }
}
