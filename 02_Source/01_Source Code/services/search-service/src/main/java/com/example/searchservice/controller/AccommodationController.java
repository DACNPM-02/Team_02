package com.example.searchservice.controller;

import com.example.searchservice.model.Accommodation;
import com.example.searchservice.service.AccommodationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccommodationController {
    private final AccommodationService accommodationService;

    @Autowired
    public AccommodationController(AccommodationService service) {
        this.accommodationService = service;
    }

    @GetMapping("/accommodations")
    public List<Accommodation> getAllAccommodations() {
        return accommodationService.getAllAccommodations();
    }

    @GetMapping("/accommodations/{id}")
    public Accommodation getAccommodationById(@PathVariable int id) {
        return accommodationService.getAccommodationById(id);
    }

    @GetMapping("/accommodations/search")
    public List<Accommodation> searchAccommodations(
            @RequestParam String address,
            @RequestParam(defaultValue = "10") double radius,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return accommodationService.searchAccomodations(address, radius, limit);
    }

    @GetMapping("/accommodations/search/text")
    public List<Accommodation> searchByText(@RequestParam String query) {
        return accommodationService.searchByText(query);
    }

    @PostMapping("/accommodations")
    public Accommodation createAccommodation(@RequestBody Accommodation accommodation) {
        return accommodationService.createAccommodation(accommodation);
    }

    @PutMapping("/accommodations/{id}")
    public Accommodation updateAccommodation(@PathVariable int id, @RequestBody Accommodation accommodation) {
        accommodation.setId(id);
        return accommodationService.updateAccommodation(accommodation);
    }

    @DeleteMapping("/accommodations/{id}")
    public void deleteAccommodation(@PathVariable int id) {
        accommodationService.deleteAccommodation(id);
    }
} 