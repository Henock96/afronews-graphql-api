package com.dreesis.afronews_graphql.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@ToString
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, columnDefinition = "TEXT")
    private String titre;
    private String description;
    @Column(name = "url_image")
    private String urlImage;
    @Column(name = "url_article")
    private String urlArticle;
    @Column(name = "date_publication")
    private String datePublication;
    @Column(name = "date_publication_format")
    private String datePublicationFormat;
    private String auteur;
    @ManyToOne//(cascade = CascadeType.ALL)
    // @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "source_id")
    private Source source;



    private LocalDateTime created;

    public Article() {
    }
}
