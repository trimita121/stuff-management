package com.example.stuff_management_reactivejava.stuffClean.Domain.commands;

import com.example.stuff_management_reactivejava.stuffClean.Domain.commands.dto.StuffResponseDto;
import com.example.stuff_management_reactivejava.stuffClean.Domain.Stuff;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component
public interface IStuffCommands {
    Mono<Stuff> buildStuffDomain(StuffResponseDto stuffResponseDto);
}