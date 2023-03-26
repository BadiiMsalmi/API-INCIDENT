package com.api.backincdidents.model;

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
  private String creationdate;

  @ManyToOne 
  @JoinColumn(name = "status_id")
  private Status status;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User declarant;

  @ManyToOne
  @JoinColumn(name = "assigne_id")
  private User assigne;
}
