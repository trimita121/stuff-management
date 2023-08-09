package com.example.stuff_management_reactivejava.activityLogClean.application.port.in.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityResponseDto {
    private String stuffId;
    private String traceId;
    private String httpStatus;
    private String httpMethod;
    private String requestUrl;
    private LocalDateTime createdOn;
    private String editedBy;
}
