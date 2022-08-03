package com.dreesis.afronews_graphql.entities;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Categorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "categorie_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nom;
    //@OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL)
    //private Set<Article> articles = new HashSet<>();

    public Categorie(Long id) {
        this.id = id;
    }
    public Categorie() {
    }
    public Categorie(String nom) {
        this.nom = nom;
    }
}
