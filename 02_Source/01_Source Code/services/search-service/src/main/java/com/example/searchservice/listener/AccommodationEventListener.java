package com.example.searchservice.listener;

import com.example.searchservice.model.Accommodation;
import com.example.searchservice.repository.elastic.AccommodationElasticRepository;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class AccommodationEventListener {
    
    private final AccommodationElasticRepository elasticRepository;

    @Autowired
    public AccommodationEventListener(AccommodationElasticRepository elasticRepository) {
        this.elasticRepository = elasticRepository;
    }

    @PostPersist
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onPostPersist(Accommodation accommodation) {
        elasticRepository.save(accommodation);
    }

    @PostUpdate
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onPostUpdate(Accommodation accommodation) {
        elasticRepository.save(accommodation);
    }

    @PostRemove
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onPostRemove(Accommodation accommodation) {
        elasticRepository.deleteById(accommodation.getId());
    }
} 