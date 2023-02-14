package com.api.backincdidents.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name ="user") 
public class User {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @Column(name= "firstName")
    private String firstName;

    @Column(name= "lastName")
    private String lastName;

    @Column(name= "role")
    private String role;

}
