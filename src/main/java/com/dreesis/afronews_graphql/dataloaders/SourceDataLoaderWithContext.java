package com.dreesis.afronews_graphql.dataloaders;

import com.dreesis.afronews_graphql.entities.Source;
import com.dreesis.afronews_graphql.service.SourceService;
import com.netflix.graphql.dgs.DgsDataLoader;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.BatchLoaderEnvironment;
import org.dataloader.MappedBatchLoaderWithContext;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DgsDataLoader(name = "sourceWithContext")
@Slf4j
public class SourceDataLoaderWithContext implements MappedBatchLoaderWithContext<Long, Source> {
    private final SourceService sourceService;

    public SourceDataLoaderWithContext(SourceService sourceService){
        this.sourceService = sourceService;
    }
    @Override
    public CompletionStage<Map<Long, Source>> load(Set<Long> set, BatchLoaderEnvironment batchLoaderEnvironment) {
        log.info("Source will be fetched for following article IDs: {}", set);
        return CompletableFuture.supplyAsync(
                ()-> sourceService.getSource(new ArrayList<>(set))
        );
    }
}
