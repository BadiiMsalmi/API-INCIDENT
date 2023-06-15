package com.api.backincdidents.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate date;

    private LocalTime time;

    private String text;

    private int ticketId;

    @ManyToOne
    @JoinColumn(name = "declarant_id")
    private User declarant;

    @ManyToOne
    @JoinColumn(name = "assigne_id")
    private User assigne;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;


}
