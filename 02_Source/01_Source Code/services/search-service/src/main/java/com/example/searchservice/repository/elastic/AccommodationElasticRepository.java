package com.example.searchservice.repository.elastic;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.example.searchservice.model.Accommodation;

@Repository
public interface AccommodationElasticRepository extends ElasticsearchRepository<Accommodation, Integer> {
    
    @Query("{\"bool\": {\"must\": [{\"match\": {\"title\": \"?0\"}}]}}")
    List<Accommodation> findByTitle(String title);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"address\": \"?0\"}}]}}")
    List<Accommodation> findByAddress(String address);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"description\": \"?0\"}}]}}")
    List<Accommodation> findByDescription(String description);

    @Query("{\"bool\": {\"should\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"address\": \"?0\"}}, {\"match\": {\"description\": \"?0\"}}]}}")
    List<Accommodation> searchByText(String query);
} 