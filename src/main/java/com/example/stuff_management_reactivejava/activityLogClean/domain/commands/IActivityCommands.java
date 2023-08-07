package com.example.stuff_management_reactivejava.activityLogClean.domain.commands;

import com.example.stuff_management_reactivejava.activityLogClean.domain.Activity;
import com.example.stuff_management_reactivejava.activityLogClean.domain.commands.dto.ActivityDomainRequestDto;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component
public interface IActivityCommands {
    Mono<Activity> buildActivityDomain(ActivityDomainRequestDto activityDomainRequestDto);
}
