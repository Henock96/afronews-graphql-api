package com.dreesis.afronews_graphql.repository;

import com.dreesis.afronews_graphql.entities.Article;
import com.dreesis.afronews_graphql.entities.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface SourceRepository extends JpaRepository<Source, Long> {
    Source findByNom(String nom);
    @Query("Select a from Article a left join Source s on a.source.id = s.id  where a.id in (:articleIds)")
    List<Article> getArticleSourceMappings(List<Long> articleIds);
}
