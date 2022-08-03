package com.dreesis.afronews_graphql.dataloaders;

import com.dreesis.afronews_graphql.entities.Categorie;
import com.dreesis.afronews_graphql.service.CategorieService;
import com.netflix.graphql.dgs.DgsDataLoader;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.MappedBatchLoader;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DgsDataLoader(name= "categorie")
@Slf4j
public class CategorieDataLoader implements MappedBatchLoader<Long, Categorie> {
    private final CategorieService cateService;

    public CategorieDataLoader(CategorieService cateService) {
        this.cateService = cateService;
    }

    @Override
    public CompletionStage<Map<Long, Categorie>> load(Set<Long> set) {
        log.info("Categorie will be fetched for following articles IDs: {}", set);
        return CompletableFuture.supplyAsync(() -> cateService.getCategorie(new ArrayList<>(set)));
    }
}
