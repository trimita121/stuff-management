package com.example.stuff_management_reactivejava.Stuff.Repository;

import com.example.stuff_management_reactivejava.Stuff.Entity.StuffEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface StuffRepository extends R2dbcRepository<StuffEntity,String> {
    Mono<StuffEntity> findByStuffId(String stuffId);

}
