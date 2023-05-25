package com.api.backincdidents.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.api.backincdidents.model.Affiliate;

public interface AffiliateRepository extends JpaRepository<Affiliate, Integer> {
    
}
