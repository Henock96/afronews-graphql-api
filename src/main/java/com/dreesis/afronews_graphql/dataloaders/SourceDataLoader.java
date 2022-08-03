package com.dreesis.afronews_graphql.dataloaders;

import com.dreesis.afronews_graphql.entities.Source;
import com.dreesis.afronews_graphql.service.SourceService;
import com.netflix.graphql.dgs.DgsDataLoader;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.MappedBatchLoader;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DgsDataLoader(name = "source")
@Slf4j
public class SourceDataLoader implements MappedBatchLoader<Long, Source> {
    private final SourceService sourceService;

    public SourceDataLoader(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    @Override
    public CompletionStage<Map<Long, Source>> load(Set<Long> set) {
        return CompletableFuture.supplyAsync(() -> sourceService.getSource(new ArrayList<>(set)));
    }
}
