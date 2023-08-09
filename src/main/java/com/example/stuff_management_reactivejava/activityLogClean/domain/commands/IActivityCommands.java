package com.example.stuff_management_reactivejava.activityLogClean.domain.commands;

import com.example.stuff_management_reactivejava.activityLogClean.domain.Activity;
import com.example.stuff_management_reactivejava.activityLogClean.domain.commands.dto.ActivityDomainRequestDto;
import reactor.core.publisher.Mono;
public interface IActivityCommands {
    Mono<Activity> buildActivityDomain(ActivityDomainRequestDto activityDomainRequestDto);
}
