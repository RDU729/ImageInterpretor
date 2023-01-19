package com.api.imageinterpretor.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Getter
@Setter
//@ToString
@Table(name = "img")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "img_generator")
    @SequenceGenerator(name = "img_sequence", allocationSize = 1)
    private Long id;

    @Lob
    private byte[] base64;

    @OneToOne(mappedBy = "image")
    private Flow flow;
}
