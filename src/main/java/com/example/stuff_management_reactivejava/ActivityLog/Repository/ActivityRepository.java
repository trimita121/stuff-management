package com.example.stuff_management_reactivejava.ActivityLog.Repository;
import com.example.stuff_management_reactivejava.ActivityLog.Entity.ActivityEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ActivityRepository extends R2dbcRepository<ActivityEntity,String> {
    Mono<ActivityEntity> findByTraceId(String traceId);
}
