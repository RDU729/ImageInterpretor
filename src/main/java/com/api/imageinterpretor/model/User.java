package com.api.imageinterpretor.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;


@Entity
@ToString
@Getter
@Setter
@Table(name = "utilizatori")
public class User {


    private String name;
    //@Column(nullable = false, unique = true, length = 45)
    @Id
    private String email;

    //@Column(nullable = false, length = 64)
    private String password;

    private int enabled;

    private String activationCode;

    @OneToMany(mappedBy = "user")
    private List<Flow> flow;
}



