package com.example.stuff_management_reactivejava.activityLogClean.adapter.out;

import com.example.stuff_management_reactivejava.activityLogClean.adapter.out.repository.ActivityEntity;
import com.example.stuff_management_reactivejava.activityLogClean.adapter.out.repository.ActivityRepositoryClean;
import com.example.stuff_management_reactivejava.activityLogClean.application.port.out.ActivityPersistencePort;
import com.example.stuff_management_reactivejava.activityLogClean.domain.Activity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component
public class ActivityLogAdapter implements ActivityPersistencePort {
    private final ActivityRepositoryClean repository;
    private final ActivityEntity activityEntity;
    private final ModelMapper modelMapper = new ModelMapper();

    public ActivityLogAdapter(ActivityRepositoryClean repository, ActivityEntity activityEntity) {
        this.repository = repository;
        this.activityEntity = activityEntity;
    }


    @Override
    public Mono<Activity> saveActivity(Activity activity) {
        ActivityEntity activityEntity1 = modelMapper.map(activity, ActivityEntity.class);
        return repository.save(activityEntity1)
                .map(activityEntity2 -> modelMapper.map(activityEntity2, Activity.class));

    }

    @Override
    public Mono<Activity> findActivityByTraceId(String traceId) {
        return repository.findActivityEntityByTraceId(traceId)
                .map(activityEntity1 -> modelMapper.map(activityEntity1, Activity.class));
    }
}
