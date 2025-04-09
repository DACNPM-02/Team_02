package com.example.searchservice.config;

import com.example.searchservice.model.Accommodation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchIndexInitializer {

    private final ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public ElasticsearchIndexInitializer(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createIndex() {
        if (!elasticsearchOperations.indexOps(Accommodation.class).exists()) {
            elasticsearchOperations.indexOps(Accommodation.class).create();
            elasticsearchOperations.indexOps(Accommodation.class).putMapping();
        }
    }
} 