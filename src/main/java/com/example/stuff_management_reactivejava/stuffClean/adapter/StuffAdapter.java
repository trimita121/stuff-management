package com.example.stuff_management_reactivejava.stuffClean.adapter;

import com.example.stuff_management_reactivejava.stuffClean.Domain.Stuff;
import com.example.stuff_management_reactivejava.stuffClean.adapter.out.entity.StuffEntityClean;
import com.example.stuff_management_reactivejava.stuffClean.adapter.out.repository.StuffRepositoryClean;
import com.example.stuff_management_reactivejava.stuffClean.application.port.out.StuffPersistencePort;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Component
public class StuffAdapter implements StuffPersistencePort{
    private final ModelMapper modelMapper = new ModelMapper();
    private final StuffRepositoryClean stuffRepositoryClean;
    private final StuffEntityClean stuffEntityClean;
    public StuffAdapter( StuffRepositoryClean stuffRepositoryClean, StuffEntityClean stuffEntityClean) {
        this.stuffRepositoryClean = stuffRepositoryClean;
        this.stuffEntityClean = stuffEntityClean;
    }

    @Override
    public Mono<Stuff> saveStuff(Stuff stuff) {
         StuffEntityClean stuffEntityClean1 = modelMapper.map(stuff,StuffEntityClean.class);
         return stuffRepositoryClean.save(stuffEntityClean1)
                 .map(savedStuffEntityClean -> modelMapper.map(savedStuffEntityClean,Stuff.class) );
    }

    @Override
    public Mono<Stuff> findStuffByStuffId(String stuffId) {
       return stuffRepositoryClean.findByStuffId(stuffId)
                .map(stuffEntityCleanFromDB -> modelMapper.map(stuffEntityCleanFromDB,Stuff.class));
    }

    @Override
    public Flux<Stuff> findAllStuff() {
     return stuffRepositoryClean.findAll()
                .map(allStuffsFromDB -> modelMapper.map(allStuffsFromDB,Stuff.class) );
    }

    @Override
    public Mono<Stuff> deleteStuff(String stuffId) {
      return stuffRepositoryClean.findByStuffId(stuffId)
                .flatMap(stuffEntityCleanFromDB -> {
                  return stuffRepositoryClean.delete(stuffEntityCleanFromDB)
                            .then(Mono.just(modelMapper.map(stuffEntityCleanFromDB,Stuff.class)));
                });
    }
}
