package com.api.backincdidents.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.api.backincdidents.model.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}