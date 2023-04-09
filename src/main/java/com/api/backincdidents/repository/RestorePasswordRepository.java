package com.api.backincdidents.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.backincdidents.model.RestorePasswordToken;

public interface RestorePasswordRepository extends JpaRepository<RestorePasswordToken,String> {
    RestorePasswordToken findByToken(String Token);
}
