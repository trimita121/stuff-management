package com.example.stuff_management_reactivejava.Stuff.Handler;

import com.example.stuff_management_reactivejava.Stuff.Dto.StuffDto;
import com.example.stuff_management_reactivejava.Stuff.Service.StuffServiceUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Slf4j
public class StuffHandler {
    private final StuffServiceUseCase stuffServiceUseCase;

    public StuffHandler(StuffServiceUseCase stuffServiceUseCase) {
        this.stuffServiceUseCase = stuffServiceUseCase;
    }

    public Mono<ServerResponse> addStuff(ServerRequest serverRequest) {
        String traceId = serverRequest.queryParam("traceId").orElse(UUID.randomUUID().toString());
        return serverRequest
                .bodyToMono(StuffDto.class)
                .flatMap(stuffDto -> stuffServiceUseCase.addStuff(stuffDto, traceId))
                .flatMap(stuffResponseDto -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(stuffResponseDto)
                        .doOnRequest(value -> log.info("Request to add Stuff by traceId: {}", traceId))
                        .doOnSuccess(serverResponse -> log.info("Response after adding Stuff :{}", stuffResponseDto))
                        .doOnError(throwable -> log.info("Error occurred while adding stuff {}", throwable.getMessage())));
    }

    public Mono<ServerResponse> getAllStuffs(ServerRequest serverRequest) {
        String traceId = serverRequest.queryParam("traceId").orElse(UUID.randomUUID().toString());
        return stuffServiceUseCase.findAllStuff(traceId)
                .doOnRequest(value -> log.info("Request to get all Stuffs by traceId : {}", traceId))
                .doOnSuccess(allStuffResponseDtos -> log.info("SuccessFully get all stuffs"))
                .doOnError(throwable -> log.info("Error occurred while getting all stuffs {}", throwable.getMessage()))
                .flatMap(allStuffResponseDto -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(allStuffResponseDto));

    }

    public Mono<ServerResponse> getStuffById(ServerRequest serverRequest) {
        String traceId = serverRequest.queryParam("traceId").orElse(UUID.randomUUID().toString());
        String stuffId = serverRequest.pathVariable("stuffId");
        return stuffServiceUseCase.findStuffByStuffId(stuffId, traceId)
                .doOnRequest(value -> log.info("Request to get Stuff by StuffId : {}", stuffId))
                .doOnSuccess(stuffResponseDto -> log.info("Successfully get Stuff by StuffId : {}", stuffId))
                .doOnError(throwable -> log.info("Error Occurred while getting Stuff ", throwable.getMessage()))
                .flatMap(stuffResponseDto -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(stuffResponseDto));

    }

    public Mono<ServerResponse> updateStuff(ServerRequest serverRequest) {
        String traceId = serverRequest.queryParam("traceId").orElse(UUID.randomUUID().toString());
        String stuffId = serverRequest.pathVariable("stuffId");
        return serverRequest
                .bodyToMono(StuffDto.class)
                .flatMap(stuffDto -> stuffServiceUseCase.updateStuffEntity(stuffDto, traceId))
                .flatMap(stuffResponseDto -> ServerResponse
                        .ok()
                        .bodyValue(stuffResponseDto)
                        .doOnRequest(value -> log.info("Request to update the Stuff by stuffId : {}", stuffId))
                        .doOnSuccess(serverResponse -> log.info("Successfully Updated the stuff entity is : {}", stuffResponseDto))
                        .doOnError(throwable -> log.info("Error occurred while updating the Stuff", throwable.getMessage())));


    }

    public Mono<ServerResponse> deleteStuff(ServerRequest serverRequest) {
        String traceId = serverRequest.queryParam("traceId").orElse(UUID.randomUUID().toString());
        String stuffId = serverRequest.pathVariable("stuffId");
        return stuffServiceUseCase.deleteStuffEntity(stuffId, traceId)
                .flatMap(stuffResponseDto -> ServerResponse
                        .ok()
                        .bodyValue(stuffResponseDto)
                        .doOnRequest(value -> log.info("Request to delete the stuff by stuffId : {}", stuffId))
                        .doOnSuccess(serverResponse -> log.info("Successfully Deleted Stuff is : {}",stuffResponseDto))
                        .doOnError(throwable -> log.info("Error occurred while Deleting the Stuff", throwable.getMessage())));

    }

}