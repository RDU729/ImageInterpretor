package com.api.imageinterpretor.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@ToString
@Getter
@Setter
@Table(name = "flow")
public class Flow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String pid;
    //
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_email", referencedColumnName = "email")
    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "img_id", referencedColumnName = "id")
    private Image image;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "last_update")
    private Timestamp lastUpdate;


}
