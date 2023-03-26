package com.api.backincdidents.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.backincdidents.model.Token;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    List<Token> findByUserIdAndExpiredIsFalseAndRevokedIsFalse(Integer userId);

    Optional<Token> findByToken(String Token);
}
