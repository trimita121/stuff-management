package com.example.stuff_management_reactivejava.stuffClean.application.service;

import com.example.stuff_management_reactivejava.activityLogClean.application.port.in.ActivityUseCase;
import com.example.stuff_management_reactivejava.activityLogClean.application.port.in.dto.request.ActivityRequestDto;
import com.example.stuff_management_reactivejava.activityLogClean.application.port.in.dto.response.ActivityResponseDto;
import com.example.stuff_management_reactivejava.stuffClean.Domain.Stuff;
import com.example.stuff_management_reactivejava.stuffClean.application.port.in.StuffUseCase;
import com.example.stuff_management_reactivejava.stuffClean.application.port.in.request.StuffRequestDto;
import com.example.stuff_management_reactivejava.stuffClean.application.port.in.response.StuffResponseDto;
import com.example.stuff_management_reactivejava.stuffClean.application.port.out.StuffPersistencePort;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class StuffService implements StuffUseCase {
    private final ActivityUseCase activityUseCase;
    private final StuffPersistencePort stuffPersistencePort;
    private final ModelMapper modelMapper = new ModelMapper();


    public StuffService(ActivityUseCase activityUseCase, StuffPersistencePort stuffPersistencePort) {
        this.activityUseCase = activityUseCase;
        this.stuffPersistencePort = stuffPersistencePort;
    }

    @Override
    public Mono<StuffResponseDto> findAllStuff(String traceId) {
        /*
         * save activity log
         * parse data from activity table - update activity
         * build stuffResponseDto
         */
        ActivityRequestDto activityRequestDto = this.buildActivityRequestDto(traceId, null, "localhost:8086/stuffs", "GET", "Request Received", "System");
        return activityUseCase.saveActivity(activityRequestDto)
                .doOnRequest(value -> log.info("Request received to save activity info  by {}, {}, {},", traceId, "Get", "Request Received"))
                .doOnSuccess(activityResponseDto -> log.info("Successfully saved activity log to DB"))
                .zipWith(stuffPersistencePort.findAllStuff().collectList())
                .doOnError(throwable -> activityUseCase.updateActivity(traceId, "Failed").subscribeOn(Schedulers.immediate()).subscribe())
                .flatMap(tuple2 -> {
                    ActivityResponseDto activityResponseDto = tuple2.getT1();
                    return activityUseCase.updateActivity(activityResponseDto.getTraceId(), "OK")
                            .then(Mono.just(tuple2.getT2()));
                })
                .flatMapMany(Flux::fromIterable)
                .collectList()
                .map(stuffList -> StuffResponseDto
                        .builder()
                        .stuffList(stuffList)
                        .message("Successfully retrieved all stuff entity")
                        .build());

    }

    private ActivityRequestDto buildActivityRequestDto(String traceId, String stuffId, String requestURl, String httpMethod, String httpStatus, String createdBy) {
        return ActivityRequestDto
                .builder()
                .stuffId(stuffId)
                .traceId(traceId)
                .requestUrl(requestURl)
                .httpStatus(httpStatus)
                .httpMethod(httpMethod)
                .createdOn(LocalDateTime.now())
                .editedBy(createdBy)
                .build();
    }


    @Override
    public Mono<StuffResponseDto> findStuffByStuffId(String stuffId, String traceId) {
        /*
         * add an activity in activity log
         * find stuffEntity from database
         * if found call updateActivity
         * return StuffResponseDto
         * */
        ActivityRequestDto activityRequestDto = this.buildActivityRequestDto(traceId, stuffId, "localhost:8086/stuff/{stuffId}", "GET", "Request Received", "System");
        return activityUseCase.saveActivity(activityRequestDto)
                .doOnRequest(value -> log.info("Request received to save activity info  by traceId : {}", traceId))
                .doOnSuccess(activityResponseDto -> log.info("Successfully saved activity log to DB"))
                .zipWith(stuffPersistencePort.findStuffByStuffId(stuffId))
                .doOnError(throwable -> activityUseCase.updateActivity(traceId, "Failed").subscribeOn(Schedulers.immediate()).subscribe())
                .flatMap(tuple2 -> {
                    ActivityResponseDto activityResponseDto = tuple2.getT1();
                    Stuff savedstuff = tuple2.getT2();
                    return activityUseCase
                            .updateActivity(activityResponseDto.getTraceId(), "OK")
                            .thenReturn(StuffResponseDto
                                    .builder()
                                    .stuffList(List.of(savedstuff))
                                    .message("Found Stuff By StuffId : " + savedstuff.getStuffId())
                                    .build()
                            );
                });
    }

    @Override
    public Mono<StuffResponseDto> addStuff(StuffRequestDto stuffRequestDto, String traceId) {

        Stuff stuff = this.buildStuff(stuffRequestDto);
        ActivityRequestDto activityRequestDto = this.buildActivityRequestDto(traceId, stuff.getStuffId(), "localhost:8086/addStuff", "POST", "Request Received", "System");
        return activityUseCase.saveActivity(activityRequestDto)
                .zipWith(stuffPersistencePort.saveStuff(stuff))
                .doOnError(throwable -> activityUseCase.updateActivity(traceId, "Failed").subscribeOn(Schedulers.immediate()).subscribe())
                .flatMap(tuple2 -> {
                    ActivityResponseDto activityResponseDto = tuple2.getT1();
                    Stuff savedStuff = tuple2.getT2();
                    return activityUseCase
                            .updateActivity(activityResponseDto.getTraceId(), "OK")
                            .thenReturn(
                                    StuffResponseDto
                                            .builder()
                                            .stuffList(List.of(savedStuff))
                                            .message("Successfully saved Stuff By stuffId : " + stuffRequestDto.getStuffId())
                                            .build()
                            );
                });

    }

    private Stuff buildStuff(StuffRequestDto stuffRequestDto) {
        return Stuff
                .builder()
                .stuffId(stuffRequestDto.getStuffId())
                .nickName(stuffRequestDto.getStuffNickName())
                .fullName(stuffRequestDto.getStuffFullName())
                .designation(stuffRequestDto.getStuffDesignation())
                .createdOn(LocalDateTime.now())
                .createdBy("System")
                .build();
    }

    @Override
    public Mono<StuffResponseDto> updateStuffEntity(StuffRequestDto stuffRequestDto, String traceId) {
        /*
         * save activity
         * find stuff from DB
         * update the DBStuff and saved the updated stuff to repository
         * update activity and return StuffResponse
         * */
        Stuff stuff = this.buildStuff(stuffRequestDto);
        ActivityRequestDto activityRequestDto = this.buildActivityRequestDto(traceId, stuffRequestDto.getStuffId(), "localhost:8086/updateStuff/{stuffId}", "PUT", "Request Received", "Trimita");
        return activityUseCase.saveActivity(activityRequestDto)
                .doOnRequest(value -> log.info("Received request to save activity by Editor : {}", activityRequestDto.getEditedBy()))
                .doOnError(throwable -> log.info("CanNot saved activity" + throwable.getMessage()))
                .zipWith(stuffPersistencePort.findStuffByStuffId(stuff.getStuffId())
                        .map(stuffFromDB -> {
                            return this.buildUpdatedStuff(stuff, stuffFromDB);
                        }))
                .flatMap(tuple2 -> {
                    Stuff updatedStuff = tuple2.getT2();
                    ActivityResponseDto activityResponseDto = tuple2.getT1();
                    activityUseCase.updateActivity(traceId, "Ok").subscribeOn(Schedulers.immediate()).subscribe();
                    return stuffPersistencePort.saveStuff(updatedStuff)
                            .doOnError(throwable -> activityUseCase.updateActivity(traceId, "Failed").subscribeOn(Schedulers.immediate()).subscribe());

                })
                .map(updatedStuff -> StuffResponseDto
                        .builder()
                        .stuffList(List.of(updatedStuff))
                        .message("Successfully updated stuff by stuffId : " + updatedStuff.getStuffId())
                        .build());

    }

    private Stuff buildUpdatedStuff(Stuff stuffRequestDto, Stuff stuffFromD) {
        stuffFromD.setNickName(stuffRequestDto.getNickName());
        stuffFromD.setFullName(stuffRequestDto.getFullName());
        stuffFromD.setDesignation(stuffRequestDto.getDesignation());
        stuffFromD.setCreatedBy("Trimita");
        return modelMapper.map(stuffFromD, Stuff.class);
    }

    @Override
    public Mono<StuffResponseDto> deleteStuffEntity(String stuffId, String traceId) {
        /*
        * save activity
        * find stuff from DB
        * delete the StuffFromDB
        * update activity and return StuffResponseDto
        * */

        ActivityRequestDto activityRequestDto = buildActivityRequestDto(traceId,stuffId,"localhost:8086/deleteStuff/{stuffId}","Delete","Request received","Trimita");
      return activityUseCase.saveActivity(activityRequestDto)
                .doOnRequest(value -> log.info("Request Received to save activity"))
                .doOnSuccess( activityResponseDto -> log.info("Successfully saved the activity"))
                .doOnError(throwable -> log.info("Saving activity failed : {}",throwable.getMessage()))
                .zipWith(stuffPersistencePort.findStuffByStuffId(stuffId))
                .flatMap(tuple2 -> {
                    return stuffPersistencePort.deleteStuff(tuple2.getT2().getStuffId())
                            .doOnSuccess(stuff -> activityUseCase.updateActivity(traceId, "Ok").subscribeOn(Schedulers.immediate()).subscribe())
                            .doOnError(throwable -> activityUseCase.updateActivity(traceId, "failed").subscribeOn(Schedulers.immediate()).subscribe());
                })
                .map(stuffMono -> StuffResponseDto
                        .builder()
                        .stuffList(List.of(stuffMono))
                        .message("Successfully Deleted stuff by StuffId : "+stuffMono.getStuffId())
                        .build());
    }
}
