package com.example.stuff_management_reactivejava.activityLogClean.adapter.out.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@Component
public interface ActivityRepositoryClean extends R2dbcRepository<ActivityEntity,String> {
    Mono<ActivityEntity> findActivityEntityByTraceId(String traceId);

}
