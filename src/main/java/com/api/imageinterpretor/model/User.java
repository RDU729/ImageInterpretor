package com.api.imageinterpretor.model;

import lombok.*;

import javax.persistence.*;


@Entity
@ToString
@Getter
@Setter
@Table(name = "utilizatori")
public class User {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    private String name;
    //@Column(nullable = false, unique = true, length = 45)
    @Id
    private String email;

    //@Column(nullable = false, length = 64)
    private String password;

    private int enabled;
}



