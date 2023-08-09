package com.example.stuff_management_reactivejava.stuffClean.adapter.in;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Router {
    @Bean
    public RouterFunction<ServerResponse> MonoStuffRouterFunctionClean(Handler handler) {

        return route()
                .GET("/stuffs",handler::getAllStuffs)
                .GET("/stuff/{stuffId}",handler::getStuffByStuffId)
                .POST("/addStuff",handler::addStuff)
                .PUT("/updateStuff/{stuffId}",handler::updateStuff)
                .DELETE("/deleteStuff/{stuffId}",handler::deleteStuff)
                .build();


    }
}
