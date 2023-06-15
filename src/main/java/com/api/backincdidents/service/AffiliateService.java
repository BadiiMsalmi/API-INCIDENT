package com.api.backincdidents.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import com.api.backincdidents.model.Affiliate;
import com.api.backincdidents.repository.AffiliateRepository;

@Service
public class AffiliateService {
    
    @Autowired
    private AffiliateRepository affiliateRepository;

    public List<Affiliate> getAllAffiliates(){
        return affiliateRepository.findAll();
    }

    public Affiliate addAffiliate(Affiliate affiliate){
        return affiliateRepository.save(affiliate);
    }

    

    public void deleteAffiliate(int id){
        affiliateRepository.deleteById(id);
    }

    public Affiliate getAffiliateById(int id){
        return affiliateRepository.getReferenceById(id);
    }

    public Affiliate updateAffiliate(Affiliate affiliate) {
        return affiliateRepository.save(affiliate);
      }
    
}
