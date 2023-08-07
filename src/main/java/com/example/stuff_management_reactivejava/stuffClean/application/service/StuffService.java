package com.example.stuff_management_reactivejava.stuffClean.application.service;

import com.example.stuff_management_reactivejava.activityLogClean.application.port.in.ActivityUseCase;
import com.example.stuff_management_reactivejava.stuffClean.application.port.in.StuffUseCase;
import com.example.stuff_management_reactivejava.stuffClean.application.port.in.request.StuffRequestDto;
import com.example.stuff_management_reactivejava.stuffClean.application.port.in.response.AllStuffResponseDto;
import reactor.core.publisher.Mono;

public class StuffService implements StuffUseCase {
    private final ActivityUseCase activityUseCase;

    public StuffService(ActivityUseCase activityUseCase) {
        this.activityUseCase = activityUseCase;
    }

    @Override
    public Mono<AllStuffResponseDto> findAllStuff(String traceId) {
        /*
        * save activity log
        * parse data from activity table - update activity
        * build stuffResponseDto
        */
        return null;
    }

    @Override
    public Mono<StuffRequestDto> findStuffByStuffId(String stuffId, String traceId) {
        return null;
    }

    @Override
    public Mono<StuffRequestDto> addStuff(StuffRequestDto stuffRequestDto, String traceId) {
        return null;
    }

    @Override
    public Mono<StuffRequestDto> updateStuffEntity(StuffRequestDto stuffRequestDto, String traceId) {
        return null;
    }

    @Override
    public Mono<StuffRequestDto> deleteStuffEntity(String stuffId, String traceId) {
        return null;
    }
}
