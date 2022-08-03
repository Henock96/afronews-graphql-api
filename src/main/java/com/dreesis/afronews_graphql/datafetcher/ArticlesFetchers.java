package com.dreesis.afronews_graphql.datafetcher;

import com.dreesis.afronews_graphql.dataloaders.CategorieDataLoaderWithContext;
import com.dreesis.afronews_graphql.dataloaders.SourceDataLoaderWithContext;
import com.dreesis.afronews_graphql.entities.Article;
import com.dreesis.afronews_graphql.entities.Categorie;
import com.dreesis.afronews_graphql.entities.Source;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoader;

import java.util.concurrent.CompletableFuture;

@DgsComponent
@Slf4j
public class ArticlesFetchers {

    @DgsData(parentType = "Article")
    public CompletableFuture<Categorie> categorie(DgsDataFetchingEnvironment env){
        DataLoader<Long, Categorie> categorieDataLoader = env.getDataLoader(CategorieDataLoaderWithContext.class);
        Article article = env.getSource();
        return categorieDataLoader.load(article.getId());
    }
    @DgsData(parentType = "Article")
    public CompletableFuture<Source> source(DgsDataFetchingEnvironment env){
        DataLoader<Long, Source> sourceDataLoader = env.getDataLoader(SourceDataLoaderWithContext.class);
        Article article = env.getSource();
        //log.info("Source est entrain d'etre fetch pour l'article avec un ID- {}", article.getId());
        return sourceDataLoader.load(article.getId());
    }

}
