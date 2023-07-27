package com.example.stuff_management_reactivejava.activityLogClean.application.port.out;

import com.example.stuff_management_reactivejava.activityLogClean.domain.Activity;
import reactor.core.publisher.Mono;

public interface ActivityPersistencePort {
   Mono<Activity> saveActivity(Activity activity);
}
