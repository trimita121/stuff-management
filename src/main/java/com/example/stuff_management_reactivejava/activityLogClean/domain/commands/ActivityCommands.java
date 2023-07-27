package com.example.stuff_management_reactivejava.activityLogClean.domain.commands;

import com.example.stuff_management_reactivejava.activityLogClean.domain.Activity;
import com.example.stuff_management_reactivejava.activityLogClean.domain.commands.dto.ActivityRequestDto;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
@Slf4j
public class ActivityCommands implements IActivityCommands{

    @Override
    public Mono<Activity> buildActivityDomain(ActivityRequestDto activityRequestDto) {
        log.info("Request received to build Activity domain by ActivityRequestDto : {}", activityRequestDto);
       return Mono.just(Activity
                .builder()
                .traceId(activityRequestDto.getTraceId())
                .httpStatus(activityRequestDto.getHttpStatus())
                .httpMethod(activityRequestDto.getHttpMethod())
                .createdOn(activityRequestDto.getCreatedOn())
                .createdBy(activityRequestDto.getCreatedBy())
                .requestUrl(activityRequestDto.getRequestUrl())
                .build());
    }
}
