package com.api.backincdidents.model;

import java.time.LocalDate;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "incidents")
public class Incident {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "reference")
  private String reference;

  @Column(name = "libelle")
  private String libelle;

  @Column(name = "CreationDate")
  private LocalDate creationdate; // badelt hedhi mn string el localdate ken saret mochkla rajaaa3ha 

  @Column(name = "closure_date")
  private LocalDate closureDate;

  @ManyToOne
  @JoinColumn(name = "status_id")
  private Status status;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User declarant;

  @ManyToOne
  @JoinColumn(name = "assigne_id")
  private User assigne;

  @ManyToOne
  @JoinColumn(name = "image_id")
  private ImageModel image;

}
