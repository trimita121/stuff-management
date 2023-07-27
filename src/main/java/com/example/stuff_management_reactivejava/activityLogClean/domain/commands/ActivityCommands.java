package com.example.stuff_management_reactivejava.activityLogClean.domain.commands;

import com.example.stuff_management_reactivejava.activityLogClean.domain.Activity;
import com.example.stuff_management_reactivejava.activityLogClean.domain.commands.dto.ActivityDomainRequestDto;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
@Slf4j
public class ActivityCommands implements IActivityCommands{
    @Override
    public Mono<Activity> buildActivityDomain(ActivityDomainRequestDto activityDomainRequestDto) {
        log.info("Request received to build Activity domain by ActivityRequestDto : {}", activityDomainRequestDto);

        return Mono.just(Activity
                .builder()
                .traceId(activityDomainRequestDto.getTraceId())
                .httpStatus(activityDomainRequestDto.getHttpStatus())
                .httpMethod(activityDomainRequestDto.getHttpMethod())
                .createdOn(activityDomainRequestDto.getCreatedOn())
                .createdBy(activityDomainRequestDto.getCreatedBy())
                .requestUrl(activityDomainRequestDto.getRequestUrl())
                .build());
    }
}
