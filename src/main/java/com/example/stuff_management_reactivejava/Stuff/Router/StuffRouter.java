package com.example.stuff_management_reactivejava.Stuff.Router;

import com.example.stuff_management_reactivejava.Stuff.Handler.StuffHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class StuffRouter {
     @Bean
    public RouterFunction<ServerResponse> MonoStuffRouterFunction(StuffHandler stuffHandler){
          return route()
                  .GET("/findStuffs",stuffHandler::getAllStuffs)
                  .GET("/findStuff/{stuffId}",stuffHandler::getStuffById)
                  .POST("/addstuff",stuffHandler::addStuff)
                  .PUT("/updatestuff/{stuffId}",stuffHandler::updateStuff)
                  .DELETE("/deletestuff/{stuffId}",stuffHandler::deleteStuff)
                  .build();
    }
}
