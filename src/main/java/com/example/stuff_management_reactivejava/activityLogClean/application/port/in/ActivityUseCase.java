package com.example.stuff_management_reactivejava.activityLogClean.application.port.in;

import com.example.stuff_management_reactivejava.activityLogClean.application.port.in.dto.request.ActivityRequestDto;
import com.example.stuff_management_reactivejava.activityLogClean.application.port.in.dto.response.ActivityResponseDto;
import reactor.core.publisher.Mono;

public interface ActivityUseCase {
    Mono<ActivityResponseDto> saveActivity(ActivityRequestDto activityRequestDto);

}
