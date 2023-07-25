package com.example.stuff_management_reactivejava.Stuff.Router;

import com.example.stuff_management_reactivejava.Stuff.Handler.StuffHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class StuffRouter {
     @Bean
    public RouterFunction<ServerResponse> MonoStuffRouterFunction(StuffHandler stuffHandler){
          return route()
                  .GET("/stuffs",stuffHandler::getAllStuffs)
                  .GET("/stuff/{stuffId}",stuffHandler::getStuffById)
                  .POST("/addStuff",stuffHandler::addStuff)
                  .PUT("/updateStuff/{stuffId}",stuffHandler::updateStuff)
                  .DELETE("/deleteStuff/{stuffId}",stuffHandler::deleteStuff)
                  .build();
    }
}
