package com.dreesis.afronews_graphql.service;

import com.dreesis.afronews_graphql.entities.*;
import com.dreesis.afronews_graphql.repository.ArticleRepository;
import com.dreesis.afronews_graphql.repository.CategorieRepository;
import com.dreesis.afronews_graphql.repository.SourceRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Service
public class ArticleService  {
    private final ArticleRepository articleRepo;
    private final SourceRepository sourceRepo;
    private final CategorieRepository categorieRepo;

    private static final Map<Long, Article> articleEntities =new ConcurrentHashMap<>();
    private static final Map<String, Categorie> categorieEntities =new ConcurrentHashMap<>();

    public ArticleService(ArticleRepository articleRepo, SourceRepository sourceRepo, CategorieRepository categorieRepo) {
        this.articleRepo = articleRepo;
        this.sourceRepo = sourceRepo;
        this.categorieRepo = categorieRepo;
    }
    @Transactional
    public List<Article> saveAllArticles(List<Article> articles){
        List<Article> articleList = new ArrayList<>();
        for (int i = 0 ; i < articles.size(); i++) {
            Source source = this.sourceRepo.findByNom(articles.get(i).getSource().getNom());
            if(source != null){
                articles.get(i).getSource().setId(source.getId());
            }
            Categorie categorie1 = null;
            if(articles.get(i).getSource() != null){
                Source source1 = sourceRepo.save(articles.get(i).getSource());
                articles.get(i).setSource(source1);
            }

            if (articles.get(i).getCategorie() != null){
                Categorie categorie = this.categorieRepo.findByNom(articles.get(i).getCategorie().getNom());
                if(categorie != null){
                    categorie1 = categorie;
                    articles.get(i).getCategorie().setId(categorie.getId());
                }else {
                    String categorieMin = articles.get(i).getCategorie().getNom().toUpperCase().trim();
                    articles.get(i).getCategorie().setNom(categorieMin);
                    Optional<Categorie> optCategorie = categorieRepo.findByNomExists(articles.get(i).getCategorie().getNom());
                    if(optCategorie.isPresent()){
                        categorie1 = optCategorie.get();
                        articles.get(i).setCategorie(categorie1);
                    }else{
                        categorie1 = categorieRepo.save(articles.get(i).getCategorie());
                        articles.get(i).setCategorie(categorie1);
                    }
                }
            }else {
                Optional<Categorie> categorie = categorieRepo.findById(20L);
                //categorie1 = categorie.get();
                articles.get(i).setCategorie(categorie.get());
            }
            //System.err.println(articles.get(i));
            Article article2 = articleRepo.findByTitre(articles.get(i).getTitre());
            if(categorie1 != null){
                articles.get(i).setCategorie(new Categorie(categorie1.getId()));
            }else{
                Optional<Categorie> categorie = categorieRepo.findById(20L);
                articles.get(i).setCategorie(categorie.get());
            }
            if (article2 == null){
                Article article1 = this.articleRepo.saveAndFlush(articles.get(i));
                articleList.add(article1);
            }

        }

        articleList.forEach(System.out::println);

        //  List<Article> articleList = this.repository.saveAll(articles);
        return articleList;
    }
    public List<Article> findAll(){return articleRepo.findAll();}
    @Transactional
    public List<Article> findArticleByCategorie(String categorie){
        return articleRepo.findArticleByCategorie(categorie).stream().filter(
                article -> article.getUrlImage() != null
        ).collect(Collectors.toList());
    }
    @Transactional
    public List<Article> findArticleBySourcePays(String pays) {
        return articleRepo.getArticleBySourcePays(pays).stream().filter(
                article -> article.getUrlImage() != null
        ).collect(Collectors.toList());
    }
    public List<Article> findArticleByCategorieAfter(Long id, String categorie) {
        return findArticleByCategorie(categorie).stream()
                .dropWhile(article -> article.getId().compareTo(id) != 1).toList();
    }
    public List<Article> findArticleBySourceName(String nom) {
        return articleRepo.getArticleBySourceName(nom);
    }

    public List<Article> getPreferences(List<String> prefs){
        return articleRepo.getPreferences(prefs).stream().filter(
                article -> article.getUrlImage() != null
        ).collect(Collectors.toList());
    }
    @Transactional
    public List<Article> getArticlePreferences(List<String> prefs) {
        //List<String> tags = prefs.getTags().stream().map(ti-> ti.getNom()).collect(Collectors.toList());
        return articleRepo.getPreferences(prefs);
    }
    @Transactional
    public List<Article> search(String recherche) {
       return articleRepo.search(recherche);}

    public static interface ContactRepository extends CrudRepository<Long, Contact> {
    }
}
