package com.dreesis.afronews_graphql.service;

import com.dreesis.afronews_graphql.entities.Categorie;
import com.dreesis.afronews_graphql.repository.CategorieRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CategorieService {
    private final CategorieRepository cateRepo;
    private EntityManager entityManager;
    private static final Map<Long, Categorie> categoriesEntities =new ConcurrentHashMap<>();

    public CategorieService(CategorieRepository cateRepo, EntityManager entityManager){
        this.cateRepo = cateRepo;
        this.entityManager = entityManager;
    }

    public Map<Long, Categorie> getCategorie(List<Long> articleId){

        var articles = cateRepo.getArticleCategorieMappings(articleId);
        articles.forEach(
                article -> categoriesEntities.put(article.getId(), article.getCategorie())
        );
        return categoriesEntities;
    }

    public List<Categorie> getlistCategorie(){
        return cateRepo.getlistCategorie();
    }
}
