package com.example.stuff_management_reactivejava.ActivityLog.Service;

import com.example.stuff_management_reactivejava.ActivityLog.Service.dto.ActivityResponseDTO;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;


public interface ActivityServiceInterface {

    Mono<ActivityResponseDTO> saveActivity(String traceId, String stuffId, HttpMethod httpMethod, String status);

    Mono<ActivityResponseDTO> updateActivity(String traceId, String stuffId, HttpMethod httpMethod, String status);


}
