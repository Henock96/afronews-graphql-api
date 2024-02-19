package com.dreesis.afronews_graphql.repository;

import com.dreesis.afronews_graphql.entities.Article;
import com.dreesis.afronews_graphql.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    Categorie findByNom(String nom);

    @Query(value = "select c from Categorie c where c.nom = :nom")
    Optional<Categorie> findByNomExists(@Param("nom") String nom);

    @Query("Select a from Article a left join Categorie c on a.categorie.id = c.id  where a.id in (:articleIds)")
    List<Article> getArticleCategorieMappings(List<Long> articleIds);

    @Query("select c from Categorie c where c.id in (1,2,3,4,8,10,12,13,22,27,28,31,35,36,39,53,57,63,82,159)")
    List<Categorie> getlistCategorie();
}
