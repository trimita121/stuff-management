package com.example.stuff_management_reactivejava.stuffClean.Domain.commands;

import com.example.stuff_management_reactivejava.stuffClean.Domain.commands.dto.StuffDomainRequestDto;
import com.example.stuff_management_reactivejava.stuffClean.Domain.Stuff;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class StuffCommands implements IStuffCommands {
    @Override
    public Mono<Stuff> buildStuffDomain(StuffDomainRequestDto stuffDomainRequestDto) {
        log.info("Request Received to build Stuff Domain by StuffDomainRequestDto : {}", stuffDomainRequestDto);
        return Mono.just( Stuff
                .builder()
                .id(stuffDomainRequestDto.getId())
                .stuffId(stuffDomainRequestDto.getStuffId())
                .nickName(stuffDomainRequestDto.getNickName())
                .fullName(stuffDomainRequestDto.getFullName())
                .designation(stuffDomainRequestDto.getDesignation())
                .build()
        );
    }
}
