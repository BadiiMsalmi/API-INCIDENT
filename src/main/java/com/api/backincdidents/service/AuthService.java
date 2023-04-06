package com.api.backincdidents.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.backincdidents.enumm.Role;
import com.api.backincdidents.enumm.TokenType;
import com.api.backincdidents.model.AuthenticationRequest;
import com.api.backincdidents.model.AuthenticationResponse;
import com.api.backincdidents.model.RegisterRequest;
import com.api.backincdidents.model.Token;
import com.api.backincdidents.model.User;
import com.api.backincdidents.repository.TokenRepository;
import com.api.backincdidents.repository.UserRepository;
import com.api.backincdidents.validators.ObjectsValidator;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

        private final UserRepository repository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
        private final UserRepository userRepository;
        private final TokenRepository tokenRepository;
        private final ObjectsValidator<RegisterRequest> registerRequestValidator;
        private final ObjectsValidator<AuthenticationRequest> authenticationRequestValidator;

        public AuthenticationResponse register(RegisterRequest request) {
                var violations = registerRequestValidator.validate(request);
                if (!violations.isEmpty()) {
                        return AuthenticationResponse.builder()
                                        .error(String.join(" \n ", violations))
                                        .build();
                }
                if (userRepository.existsByEmail(request.getEmail())) {
                        return AuthenticationResponse.builder()
                                        .error("Email already in use")
                                        .build();
                }
                var user = User.builder()
                                .firstname(request.getFirstname())
                                .lastname(request.getLastname())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role(Role.USER)
                                .build();
                var savedUser = repository.save(user);
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
                        return AuthenticationResponse.builder()
                                        .error("Invalid email or password")
                                        .build();
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

        
            

}
