package com.api.backincdidents.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.backincdidents.model.Affiliate;
import com.api.backincdidents.service.AffiliateService;

import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/affiliate")
@RequiredArgsConstructor
public class AffiliateController {
    
    @Autowired
    private AffiliateService affiliateService ; 

    @GetMapping("/getaffiliate/{id}")
    public ResponseEntity<Object> getAffiliateById(@PathVariable("id") int id){
        Affiliate affiliate = affiliateService.getAffiliateById(id);

        if (affiliate.getLabel().isEmpty()) {
            String errorMessage = "No affilate found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
          }
          
          return ResponseEntity.ok(affiliate);
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

    @PostMapping("/addaffiliate")
    public ResponseEntity<Object> addAffilate(@RequestBody Affiliate affiliate){
        Affiliate newaffiliate = affiliateService.addAffiliate(affiliate);
        if (newaffiliate == null) {
            String errorMessage = "Error adding the affiliate";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
          }
          
          return ResponseEntity.ok(newaffiliate);
    }

    @DeleteMapping("/deleteaffiliate/{id}")
    public ResponseEntity<Object> deleteService(@PathVariable("id") int id){
        affiliateService.deleteAffiliate(id);
        return ResponseEntity.ok(200);
    }

    @PutMapping("/updateaffiliate/{id}")
    public ResponseEntity<Affiliate> updateUser(@PathVariable("id") int id, @RequestBody Affiliate A) {
        Affiliate existingAffiliate = affiliateService.getAffiliateById(id);
        if (existingAffiliate == null) {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (A.getLabel() != null) {
            existingAffiliate.setLabel(A.getLabel());
        }
        Affiliate updatedAffiliate = affiliateService.updateAffiliate(existingAffiliate);
        return new ResponseEntity<>(updatedAffiliate, HttpStatus.OK);
      }

}
