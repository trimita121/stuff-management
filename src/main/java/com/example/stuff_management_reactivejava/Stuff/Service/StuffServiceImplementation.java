package com.example.stuff_management_reactivejava.Stuff.Service;

import com.example.stuff_management_reactivejava.ActivityLog.Service.ActivityServiceInterface;
import com.example.stuff_management_reactivejava.ActivityLog.Service.dto.ActivityResponseDTO;
import com.example.stuff_management_reactivejava.Stuff.Dto.AllStuffResponseDto;
import com.example.stuff_management_reactivejava.Stuff.Dto.StuffDto;
import com.example.stuff_management_reactivejava.Stuff.Dto.StuffResponseDto;
import com.example.stuff_management_reactivejava.Stuff.Entity.StuffEntity;
import com.example.stuff_management_reactivejava.Stuff.Repository.StuffRepository;
import com.example.stuff_management_reactivejava.model.ExceptionHandlerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class StuffServiceImplementation implements StuffServiceUseCase {
    private final StuffRepository stuffRepository;
    private final ActivityServiceInterface activityServiceInterface;
    private final ModelMapper modelMapper = new ModelMapper();


    @Override
    public Mono<AllStuffResponseDto> findAllStuff(String traceId) {
        /*
        * save activity log -> activity response dto
        * parse data from stuff table -> update activity
        * build response dto & return
        *   i. Flux<StuffResponseDTO>
            ii. List<Mono<StuffResponseDto>>
        *
        * */
        return activityServiceInterface
                .saveActivity(traceId, null, HttpMethod.GET, "Request Received")
                .doOnRequest(value -> log.info("Request Received to save activity with details : {}, {}, {}, {}", traceId, null, HttpMethod.GET, "Request Received"))
                .doOnNext(activityResponseDTO -> log.info("Activity Response DTO saved in DB : {}", activityResponseDTO))
                .doOnSuccess(activityResponseDTO -> log.info("Activity saved to DB Successfully. "))
                .doOnError(throwable -> log.info("Error Happened while saving activity to DB : {}", throwable.getMessage()))
                .onErrorMap(throwable -> new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, "Custom Exception message. "))
                .zipWith(stuffRepository.findAll().collectList())
                .doOnError(throwable -> activityServiceInterface.updateActivity(traceId, null, HttpMethod.GET, "Failed").subscribeOn(Schedulers.immediate()).subscribe())
                .flatMap(Tuple -> {
                    ActivityResponseDTO activityResponseDTO = Tuple.getT1();
                    return activityServiceInterface
                            .updateActivity(activityResponseDTO.getTraceId(),activityResponseDTO.getStuffId(),HttpMethod.GET,"Ok")
                            .then(Mono.just(Tuple.getT2()));
                })
                .flatMapMany(Flux::fromIterable)
                .map(stuffEntity -> modelMapper.map(stuffEntity, StuffDto.class))
                .collectList()
                .map(stuffDtos -> AllStuffResponseDto
                        .builder()
                        .stuffDtoList(stuffDtos)
                        .message("Successfully retrieve all stuff entity")
                        .build());


    }

    @Override
    public Mono<StuffResponseDto> findStuffByStuffId(String stuffId, String traceId) {
        log.info("Service : request received for getting stuff : stuffId : {}, traceId : {}", stuffId, traceId);
        /*
         * add an activity in activity log
         * find stuffEntity from database
         * if found call updateActivity
         * return StuffResponseDto
         * */

        return activityServiceInterface
                .saveActivity(traceId, stuffId, HttpMethod.GET, "Request Received")
                .zipWith(stuffRepository.findByStuffId(stuffId)
                        .doOnRequest(value -> log.info("Request to get stuff by StuffId : {}", stuffId))
                        .doOnSuccess(stuffEntity -> activityServiceInterface.updateActivity(traceId, stuffEntity.getStuffId(), HttpMethod.GET, "OK").subscribeOn(Schedulers.immediate()).subscribe())
                        .doOnError(throwable -> activityServiceInterface.updateActivity(traceId, stuffId, HttpMethod.GET, "Failed").subscribeOn(Schedulers.immediate()).subscribe())
                        .doOnError(throwable -> log.info("Stuff does not found by StuffId :{}", stuffId))
                        .doOnNext(stuffEntity -> log.info("Received Stuff entity from repository :{} ", stuffEntity)))

                .flatMap(objects -> {
                    StuffResponseDto stuffResponseDto = StuffResponseDto
                            .builder()
                            .stuffDto(StuffDto
                                    .builder()
                                    .id(objects.getT2().getId())
                                    .stuffId(objects.getT2().getStuffId())
                                    .stuffNickName(objects.getT2().getNickName())
                                    .stuffFullName(objects.getT2().getFullName())
                                    .stuffDesignation(objects.getT2().getDesignation())
                                    .build())
                            .message("Found the stuff info by stuffId : " + stuffId)
                            .build();
                    return Mono.just(stuffResponseDto);
                });

    }

    @Override
    public Mono<StuffResponseDto> addStuff(StuffDto stuffDto, String traceId) {
        StuffEntity stuffEntity = StuffEntity.builder()
                .stuffId(stuffDto.getStuffId())
                .nickName(stuffDto.getStuffNickName())
                .fullName(stuffDto.getStuffFullName())
                .designation(stuffDto.getStuffDesignation())
                .createdOn(LocalDateTime.now())
                .createdBy("System")
                .build();

        return activityServiceInterface
                .saveActivity(traceId, stuffDto.getStuffId(), HttpMethod.POST, "Request Received")
                .zipWith(stuffRepository.save(stuffEntity)
                        .doOnSuccess(stuffEntity1 -> activityServiceInterface.updateActivity(traceId, stuffEntity1.getStuffId(), HttpMethod.POST, "Ok").subscribeOn(Schedulers.immediate()).subscribe())
                        .doOnError(throwable -> activityServiceInterface.updateActivity(traceId, stuffEntity.getStuffId(), HttpMethod.POST, "Failed").subscribeOn(Schedulers.immediate()).subscribe())
                        .doOnError(throwable -> new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, "Data not found From activityResponseDto"))
                        .doOnNext(stuffEntity1 -> log.info("Saved the stuff : {}", stuffEntity1)))

                .map(objects -> StuffResponseDto
                        .builder()
                        .stuffDto(StuffDto
                                .builder()
                                .id(objects.getT2().getId())
                                .stuffId(objects.getT2().getStuffId())
                                .stuffNickName(objects.getT2().getNickName())
                                .stuffFullName(objects.getT2().getFullName())
                                .stuffDesignation(objects.getT2().getDesignation())
                                .build())

                        .message("Added the stuff info by : " + objects.getT2().getStuffId())
                        .build());

    }

    @Override
    public Mono<StuffResponseDto> updateStuffEntity(StuffDto updateStuffDto, String traceId) {

        return activityServiceInterface.saveActivity(traceId, updateStuffDto.getStuffId(), HttpMethod.PUT, "Request received")
                .zipWith(stuffRepository.findByStuffId(updateStuffDto.getStuffId())
                        .onErrorMap(throwable -> new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, "Data do not found in Repository"))
                        .map(stuffEntity ->
                                StuffEntity
                                        .builder()
                                        .id(stuffEntity.getId())
                                        .stuffId(updateStuffDto.getStuffId())
                                        .nickName(updateStuffDto.getStuffNickName())
                                        .fullName(updateStuffDto.getStuffFullName())
                                        .designation(updateStuffDto.getStuffDesignation())
                                        .createdOn(LocalDateTime.now())
                                        .createdBy("Trimita")
                                        .build()

                        )
                        .doOnSuccess(stuffEntity -> activityServiceInterface.updateActivity(traceId, stuffEntity.getStuffId(), HttpMethod.PUT, "Ok").subscribeOn(Schedulers.immediate()).subscribe())
                        .doOnError(throwable -> activityServiceInterface.updateActivity(traceId, updateStuffDto.getStuffId(), HttpMethod.PUT, "Failed").subscribeOn(Schedulers.immediate()).subscribe())
                        .doOnError(throwable -> new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, "Data not found From activityResponseDto")))

                .flatMap(objects -> stuffRepository.save(objects.getT2()))
                .doOnSuccess(stuff -> log.info(" updated Stuff is : {}", stuff))
                .map(stuffEntity -> StuffResponseDto
                        .builder()
                        .stuffDto(StuffDto
                                .builder()
                                .id(stuffEntity.getId())
                                .stuffId(stuffEntity.getStuffId())
                                .stuffNickName(stuffEntity.getNickName())
                                .stuffFullName(stuffEntity.getFullName())
                                .stuffDesignation(stuffEntity.getDesignation())
                                .build())
                        .message("Update the Stuff Info by stuffId :" + stuffEntity.getStuffId())
                        .build());

    }

    @Override
    public Mono<StuffResponseDto> deleteStuffEntity(String stuffId, String traceId) {
        return activityServiceInterface.saveActivity(traceId, stuffId, HttpMethod.DELETE, "Request received")
                .zipWith(stuffRepository.findByStuffId(stuffId)
                        .onErrorMap(throwable -> new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, "Data do not found in Repository"))
                        .flatMap(entity -> {
                            return stuffRepository
                                    .delete(entity)
                                    .then(Mono.just(StuffDto
                                            .builder()
                                            .stuffId(entity.getStuffId())
                                            .id(entity.getId())
                                            .stuffNickName(entity.getNickName())
                                            .stuffFullName(entity.getFullName())
                                            .stuffDesignation(entity.getDesignation())
                                            .build()));
                        })
                        .onErrorMap(throwable -> new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, "Deleting Stuff Failed! "))
                        .doOnSuccess(stuffDto -> activityServiceInterface.updateActivity(traceId, stuffDto.getStuffId(), HttpMethod.DELETE, "Ok").subscribeOn(Schedulers.immediate()).subscribe())
                        .doOnError(throwable -> activityServiceInterface.updateActivity(traceId, stuffId, HttpMethod.DELETE, "Failed").subscribeOn(Schedulers.immediate()).subscribe())
                        .doOnError(throwable -> log.error("Something Error happened while deleting Stuff. {}", throwable.getMessage()))
                        .doOnSuccess(stuff -> log.info("Deleted Stuff is : {}", stuff))
                )
                .map(objects -> StuffResponseDto
                        .builder()
                        .stuffDto(objects.getT2())
                        .message("Successfully Deleted the Stuff by id : " + objects.getT2().getStuffId())
                        .build());


    }


}

