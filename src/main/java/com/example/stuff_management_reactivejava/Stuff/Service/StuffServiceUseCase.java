package com.example.stuff_management_reactivejava.Stuff.Service;

import com.example.stuff_management_reactivejava.Stuff.Dto.AllStuffResponseDto;
import com.example.stuff_management_reactivejava.Stuff.Dto.StuffDto;
import com.example.stuff_management_reactivejava.Stuff.Dto.StuffResponseDto;
import reactor.core.publisher.Mono;

public interface StuffServiceUseCase {
    Mono<AllStuffResponseDto> findAllStuff(String traceId);

    Mono<StuffResponseDto> findStuffByStuffId(String stuffId, String traceId);

    Mono<StuffResponseDto> addStuff(StuffDto stuffDto, String traceId);

    Mono<StuffResponseDto> updateStuffEntity(StuffDto stuffEntity, String traceId);

    Mono<StuffResponseDto> deleteStuffEntity(String stuffId, String traceId);

}
