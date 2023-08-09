package com.example.stuff_management_reactivejava.stuffClean.application.port.in;

import com.example.stuff_management_reactivejava.stuffClean.application.port.in.request.StuffRequestDto;
import com.example.stuff_management_reactivejava.stuffClean.application.port.in.response.StuffResponseDto;
import reactor.core.publisher.Mono;

public interface StuffUseCase {
    Mono<StuffResponseDto> findAllStuff(String traceId);
    Mono<StuffResponseDto> findStuffByStuffId(String stuffId, String traceId);
    Mono<StuffResponseDto> addStuff(StuffRequestDto stuffRequestDto,String traceId);
    Mono<StuffResponseDto> updateStuffEntity(StuffRequestDto stuffRequestDto,String traceId);
    Mono<StuffResponseDto> deleteStuffEntity(String stuffId, String traceId);

}
