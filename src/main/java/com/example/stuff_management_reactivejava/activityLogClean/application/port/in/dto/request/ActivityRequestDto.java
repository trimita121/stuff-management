package com.example.stuff_management_reactivejava.activityLogClean.application.port.in.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRequestDto {
    private String traceId;
    private String httpStatus;
    private String httpMethod;
    private String createdBy;
    private String requestUrl;
}
