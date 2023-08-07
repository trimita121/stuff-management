package com.example.stuff_management_reactivejava.stuffClean.application.port.in;

import com.example.stuff_management_reactivejava.stuffClean.application.port.in.request.StuffRequestDto;
import com.example.stuff_management_reactivejava.stuffClean.application.port.in.response.AllStuffResponseDto;
import reactor.core.publisher.Mono;

public interface StuffUseCase {
    Mono<AllStuffResponseDto> findAllStuff(String traceId);
    Mono<StuffRequestDto> findStuffByStuffId(String stuffId, String traceId);
    Mono<StuffRequestDto> addStuff(StuffRequestDto stuffRequestDto,String traceId);
    Mono<StuffRequestDto> updateStuffEntity(StuffRequestDto stuffRequestDto,String traceId);
    Mono<StuffRequestDto> deleteStuffEntity(String stuffId, String traceId);

}
