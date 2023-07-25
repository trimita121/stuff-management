package com.example.stuff_management_reactivejava.ActivityLog.Service;

import com.example.stuff_management_reactivejava.ActivityLog.Entity.ActivityEntity;
import com.example.stuff_management_reactivejava.ActivityLog.Repository.ActivityRepository;
import com.example.stuff_management_reactivejava.ActivityLog.Service.dto.ActivityResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityServiceImplementation implements ActivityServiceInterface  {

    private final ActivityRepository activityRepository;

    @Override
    public Mono<ActivityResponseDTO> saveActivity(String traceId, String stuffId, HttpMethod httpMethod, String status) {
        /*
         * incoming parameters for being saved to activity
         * create Activity entity
         * call repository to save activity entity
         * return ResponseDTO
         * */
        ActivityEntity activityEntity = ActivityEntity
                .builder()
                .traceId(traceId)
                .stuffId(stuffId)
                .httpMethod(httpMethod.name())
                .status(status)
                .build();
        return activityRepository
                .save(activityEntity)
                .map(this::buildActivityResponseDto);
    }

    @Override
    public Mono<ActivityResponseDTO> updateActivity(String traceId, String stuffId, HttpMethod httpMethod, String status) {
        return activityRepository
                .findByTraceId(traceId)
                .map(activityEntity -> {
                    activityEntity.setTraceId(traceId);
                    activityEntity.setStuffId(stuffId);
                    activityEntity.setHttpMethod(httpMethod.toString());
                    activityEntity.setStatus(status);
                    return activityEntity;
                })
                .flatMap(activityRepository::save)
                .map(this::buildActivityResponseDto);
    }

    private ActivityResponseDTO buildActivityResponseDto(ActivityEntity activityEntity) {
        return ActivityResponseDTO
                .builder()
                .traceId(activityEntity.getTraceId())
                .stuffId(activityEntity.getStuffId())
                .httpMethod(activityEntity.getHttpMethod())
                .status(activityEntity.getStatus())
                .build();
    }
}
