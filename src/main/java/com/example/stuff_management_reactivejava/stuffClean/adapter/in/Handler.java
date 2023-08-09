package com.example.stuff_management_reactivejava.stuffClean.adapter.in;

import com.example.stuff_management_reactivejava.stuffClean.adapter.StuffAdapter;
import com.example.stuff_management_reactivejava.stuffClean.application.port.in.StuffUseCase;
import com.example.stuff_management_reactivejava.stuffClean.application.port.in.request.StuffRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Slf4j
public class Handler {
    private final StuffUseCase stuffUseCase;
    private final ModelMapper modelMapper = new ModelMapper();

    public Handler(StuffAdapter stuffAdapter, StuffUseCase stuffUseCase) {
        this.stuffUseCase = stuffUseCase;
    }

    public Mono<ServerResponse> addStuff(ServerRequest serverRequest) {
        String traceId = serverRequest.queryParam("traceId").orElse(UUID.randomUUID().toString());
        return serverRequest
                .bodyToMono(StuffRequestDto.class)
                .flatMap(stuffRequestDto -> stuffUseCase.addStuff(stuffRequestDto, traceId))
                .flatMap(stuffResponseDto -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(stuffResponseDto)
                        .doOnRequest(value -> log.info("Request to add Stuff by traceId: {}", traceId))
                        .doOnSuccess(serverResponse -> log.info("Response after adding Stuff :{}", stuffResponseDto))
                        .doOnError(throwable -> log.info("Error occurred while adding stuff {}", throwable.getMessage())));

    }

    public Mono<ServerResponse> getStuffByStuffId(ServerRequest serverRequest) {
        String traceid = serverRequest.queryParam("traceId").orElse(UUID.randomUUID().toString());
        String stuffId = serverRequest.pathVariable("stuffId");
        return stuffUseCase.findStuffByStuffId(stuffId, traceid)
                .doOnRequest(value -> log.info("Request to get Stuff by StuffId : {}", stuffId))
                .doOnSuccess(stuffResponseDto -> log.info("Successfully get Stuff by StuffId : {}", stuffId))
                .doOnError(throwable -> log.info("Error Occurred while getting Stuff ", throwable.getMessage()))
                .flatMap(stuffResponseDto -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(stuffResponseDto));

    }

    public Mono<ServerResponse> getAllStuffs(ServerRequest serverRequest) {
        String traceid = serverRequest.queryParam("traceId").orElse(UUID.randomUUID().toString());
        return stuffUseCase.findAllStuff(traceid)
                .doOnRequest(value -> log.info("Request to get all Stuffs by traceId : {}", traceid))
                .doOnSuccess(stuffResponseDto -> log.info("Successfully get all Stuff "))
                .doOnError(throwable -> log.info("Error Occurred while getting all Stuff ", throwable.getMessage()))
                .flatMap(stuffResponseDto -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(stuffResponseDto));

    }

    public Mono<ServerResponse> updateStuff(ServerRequest serverRequest) {
        String traceid = serverRequest.queryParam("traceId").orElse(UUID.randomUUID().toString());
        String stuffId = serverRequest.pathVariable("stuffId");
        return serverRequest
                .bodyToMono(StuffRequestDto.class)
                .flatMap(stuffRequestDto -> stuffUseCase.updateStuffEntity(stuffRequestDto, traceid))
                .doOnRequest(value -> log.info("Request to update Stuff by traceId : {}", traceid))
                .doOnSuccess(stuffResponseDto -> log.info("Successfully updated Stuff is : {} ", stuffResponseDto))
                .doOnError(throwable -> log.info("Error Occurred while updating Stuff ", throwable.getMessage()))
                .flatMap(stuffResponseDto -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(stuffResponseDto));

    }

    public Mono<ServerResponse> deleteStuff(ServerRequest serverRequest) {
        String traceid = serverRequest.queryParam("traceId").orElse(UUID.randomUUID().toString());
        String stuffId = serverRequest.pathVariable("stuffId");
       return stuffUseCase.deleteStuffEntity(stuffId,traceid)
                .doOnRequest(value -> log.info("Request to Delete Stuff by stuffId : {}", stuffId))
                .doOnSuccess(stuffResponseDto -> log.info("Successfully deleted Stuff is : {} ", stuffResponseDto))
                .doOnError(throwable -> log.info("Error Occurred while Deleting Stuff ", throwable.getMessage()))
                .flatMap(stuffResponseDto -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(stuffResponseDto));



    }

}

