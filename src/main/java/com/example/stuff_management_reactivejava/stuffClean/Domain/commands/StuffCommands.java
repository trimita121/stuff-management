package com.example.stuff_management_reactivejava.stuffClean.Domain.commands;

import com.example.stuff_management_reactivejava.stuffClean.Domain.commands.dto.StuffResponseDto;
import com.example.stuff_management_reactivejava.stuffClean.Domain.Stuff;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class StuffCommands implements IStuffCommands {
    @Override
    public Mono<Stuff> buildStuffDomain(StuffResponseDto stuffResponseDto) {
        log.info("Request Received to build Stuff by StuffResponse dto : {}",stuffResponseDto);
        return Mono.just( Stuff
                .builder()
                .id(stuffResponseDto.getId())
                .stuffId(stuffResponseDto.getStuffId())
                .nickName(stuffResponseDto.getNickName())
                .fullName(stuffResponseDto.getFullName())
                .designation(stuffResponseDto.getDesignation())
                .build()
        );
    }
}
