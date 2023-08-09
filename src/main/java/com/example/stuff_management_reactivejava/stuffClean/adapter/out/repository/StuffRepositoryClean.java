package com.example.stuff_management_reactivejava.stuffClean.adapter.out.repository;

import com.example.stuff_management_reactivejava.stuffClean.adapter.out.entity.StuffEntityClean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface StuffRepositoryClean extends ReactiveCrudRepository<StuffEntityClean,String> {
    Mono<StuffEntityClean> findByStuffId(String stuffId);

}
