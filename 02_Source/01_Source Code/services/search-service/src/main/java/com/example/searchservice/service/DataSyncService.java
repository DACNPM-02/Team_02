package com.example.searchservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.searchservice.model.Accommodation;
import com.example.searchservice.repository.elastic.AccommodationElasticRepository;
import com.example.searchservice.repository.jpa.AccommodationRepository;

@Service
public class DataSyncService {

    private final AccommodationRepository mysqlRepository;
    private final AccommodationElasticRepository elasticRepository;

    @Autowired
    public DataSyncService(AccommodationRepository mysqlRepository, 
                          AccommodationElasticRepository elasticRepository) {
        this.mysqlRepository = mysqlRepository;
        this.elasticRepository = elasticRepository;
    }

    @Transactional
    public void syncAllData() {
        // Delete all existing data in Elasticsearch
        elasticRepository.deleteAll();
        
        // Get all data from MySQL
        Iterable<Accommodation> accommodations = mysqlRepository.findAll();
        
        // Save all data to Elasticsearch
        elasticRepository.saveAll(accommodations);
    }

    @Transactional
    public void syncSingleAccommodation(int id) {
        mysqlRepository.findById(id).ifPresent(accommodation -> {
            elasticRepository.save(accommodation);
        });
    }
} 