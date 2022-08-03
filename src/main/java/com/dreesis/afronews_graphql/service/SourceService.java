package com.dreesis.afronews_graphql.service;

import com.dreesis.afronews_graphql.entities.Categorie;
import com.dreesis.afronews_graphql.entities.Source;
import com.dreesis.afronews_graphql.repository.SourceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SourceService {
    private final SourceRepository source;
    private static final Map<Long, Source> sourceEntities =new ConcurrentHashMap<>();


    public SourceService(SourceRepository source){
        this.source = source;
    }
    public Map<Long, Source> getSource(List<Long> articleId){
        var articles = source.getArticleSourceMappings(articleId);
        articles.forEach(
                article -> sourceEntities.put(article.getId(), article.getSource())
        );
        return sourceEntities;
    }

    public List<Source> getlistSources() {
        return source.findAll();
    }
}
