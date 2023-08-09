package com.example.stuff_management_reactivejava.activityLogClean.application.service;

import com.example.stuff_management_reactivejava.activityLogClean.application.port.in.ActivityUseCase;
import com.example.stuff_management_reactivejava.activityLogClean.application.port.in.dto.request.ActivityRequestDto;
import com.example.stuff_management_reactivejava.activityLogClean.application.port.in.dto.response.ActivityResponseDto;
import com.example.stuff_management_reactivejava.activityLogClean.application.port.out.ActivityPersistencePort;
import com.example.stuff_management_reactivejava.activityLogClean.domain.commands.IActivityCommands;
import com.example.stuff_management_reactivejava.activityLogClean.domain.commands.dto.ActivityDomainRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService implements ActivityUseCase {
    private final ModelMapper modelMapper = new ModelMapper();
    private final IActivityCommands iActivityCommands;
    private final ActivityPersistencePort port;

    @Override
    public Mono<ActivityResponseDto> saveActivity(ActivityRequestDto activityRequestDto) {
        /*
         * Build domain
         * Call port to save Domain to Db
         * */
        ActivityDomainRequestDto domainRequestDto = modelMapper.map(activityRequestDto, ActivityDomainRequestDto.class);

        return iActivityCommands.buildActivityDomain(domainRequestDto)
                .flatMap(port::saveActivity)
                .doOnSuccess(activity -> log.info("Successfully saved activity is : {}",activity))
                .map(activity -> modelMapper.map(activity, ActivityResponseDto.class));
    }

    @Override
    public Mono<ActivityResponseDto> updateActivity(String traceId, String status) {
        /*
         * find activity by traceId
         * update activity
         * Call port to save Domain to Db
         */
        return port.findActivityByTraceId(traceId)
                .map(activity -> {
                    activity.setStatus(status);
                    return activity;
                })
                .flatMap(port::saveActivity)
                .doOnSuccess(activity -> log.info("Successfully updated activity is : {}",activity))
                .map(activity -> modelMapper.map(activity, ActivityResponseDto.class));
    }
}
