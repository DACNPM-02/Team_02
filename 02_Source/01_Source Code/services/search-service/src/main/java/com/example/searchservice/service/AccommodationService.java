package com.example.searchservice.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.searchservice.model.Accommodation;
import com.example.searchservice.repository.elastic.AccommodationElasticRepository;
import com.example.searchservice.repository.jpa.AccommodationRepository;

@Service
public class AccommodationService {
    private final AccommodationRepository repository;
    private final AccommodationElasticRepository elasticRepository;
    private final GeocodingService geocodingService;

    @Autowired
    public AccommodationService(AccommodationRepository repo, 
                              AccommodationElasticRepository elasticRepo,
                              GeocodingService geocodingService) {
        this.repository = repo;
        this.elasticRepository = elasticRepo;
        this.geocodingService = geocodingService;
    }

    public List<Accommodation> getAllAccommodations() {
        return repository.findAll();
    }

    public Accommodation getAccommodationById(int id) {
        Optional<Accommodation> accommodation = repository.findById(id);
        return accommodation.orElse(null);
    }

    public List<Accommodation> searchAccomodations(String address, double radius, int limit) {
        double[] location = geocodingService.getLatLngFromAddress(address);
        double lat = location[0];
        double lng = location[1];
        
        if (lat == 0 && lng == 0) {
            throw new RuntimeException("khong lay duoc api ket");
        }
        
        List<Accommodation> accommodations = repository.findNearbyAccommodations(lat, lng, radius, limit);
        return accommodations != null ? accommodations : Collections.emptyList();
    }

    public List<Accommodation> searchByText(String query) {
        return elasticRepository.searchByText(query);
    }

    @Transactional
    public Accommodation createAccommodation(Accommodation accommodation) {
        return repository.save(accommodation);
    }

    @Transactional
    public Accommodation updateAccommodation(Accommodation accommodation) {
        return repository.save(accommodation);
    }

    @Transactional
    public void deleteAccommodation(int id) {
        repository.deleteById(id);
    }
} 