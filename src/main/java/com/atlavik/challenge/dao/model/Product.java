package com.atlavik.challenge.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;

    private String category;

    private Float price;

    private Date created;

    private Date updated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cartId")
    @JsonIgnore
    private Cart cart;

}
