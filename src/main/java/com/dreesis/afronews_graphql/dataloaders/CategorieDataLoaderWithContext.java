package com.dreesis.afronews_graphql.dataloaders;

import com.dreesis.afronews_graphql.entities.Categorie;
import com.dreesis.afronews_graphql.service.CategorieService;
import com.netflix.graphql.dgs.DgsDataLoader;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.BatchLoaderEnvironment;
import org.dataloader.MappedBatchLoaderWithContext;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DgsDataLoader(name = "categorieWithContext")
@Slf4j
public class CategorieDataLoaderWithContext implements MappedBatchLoaderWithContext<Long, Categorie> {
    private final CategorieService categorieService;

    public CategorieDataLoaderWithContext(CategorieService service){
        this.categorieService = service;
    }
    @Override
    public CompletionStage<Map<Long, Categorie>> load(Set<Long> set, BatchLoaderEnvironment batchLoaderEnvironment) {
        log.info("Category will be fetched for following article IDs: {}", set);
        return CompletableFuture.supplyAsync(
                () -> categorieService.getCategorie(new ArrayList<>(set))
        );
    }
}
