package com.example.searchservice.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.searchservice.model.Accommodation;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Integer> {
    @Query(value = """
            SELECT *, 
            (6371 * ACOS(
                COS(RADIANS(:latitude)) * COS(RADIANS(a.location_lat)) 
                * COS(RADIANS(a.location_lng) - RADIANS(:longitude)) 
                + SIN(RADIANS(:latitude)) * SIN(RADIANS(a.location_lat))
            )) AS distance
            FROM accommodation a
            HAVING distance < :radius
            ORDER BY distance ASC
            LIMIT :limit
            """, nativeQuery = true)
    List<Accommodation> findNearbyAccommodations(
        @Param("latitude") double latitude, 
        @Param("longitude") double longitude, 
        @Param("radius") double radius,
        @Param("limit") int limit
    );
} 