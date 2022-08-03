package com.dreesis.afronews_graphql.repository;

import com.dreesis.afronews_graphql.entities.Article;
import com.dreesis.afronews_graphql.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article findByTitre(String titre);
    @Query("Select a from Article a left join Categorie c on a.categorie.id = c.id  where c.nom = :categorie order by a.created desc")
    List<Article> findArticleByCategorie(String categorie);
    @Query("Select a from Article a left join Source s on a.source.id = s.id  where s.pays IN :pays order by a.created desc")
    List<Article> getArticleBySourcePays(String pays);

    @Query("Select a from Article a left join Source s on a.source.id = s.id  where s.nom = :sourceName order by a.created desc")
    List<Article> getArticleBySourceName(String sourceName);

    @Query("Select a from Article a left join Categorie c on a.categorie.id = c.id  where c.nom IN :prefs order by a.created desc")
    List<Article> getPreferences(List<String> prefs);

    @Query("select a from Article a where fts (:titre) = true")
    List<Article> search(@Param("titre") String recherche);
}
