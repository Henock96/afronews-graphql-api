package com.dreesis.afronews_graphql.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
public class Source implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "source_id", nullable = false)
    private Long id;
    @Column(unique = true)
    private String nom;
    private String pays;
    private String titre;
    @Column(name = "url_logo")
    private String urlLogo;
    @Column(name = "url_source")
    private String urlSource;
    @Column(name = "url_contact")
    private String urlContact;
    private String contact;
    @Column(name = "url_contact_full")
    private String urlContactFull;

    public Source() {

    }
}
