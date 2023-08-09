package com.example.stuff_management_reactivejava.stuffClean.application.port.out;

import com.example.stuff_management_reactivejava.stuffClean.Domain.Stuff;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StuffPersistencePort {
    Mono<Stuff> saveStuff(Stuff stuff);
    Mono<Stuff> findStuffByStuffId(String stuffId);
    Flux<Stuff> findAllStuff();
    Mono<Stuff> deleteStuff(String stuffId);
}
