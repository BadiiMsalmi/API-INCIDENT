package com.api.backincdidents.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.backincdidents.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
	Optional<Image> findByName(String name);
}