package com.example.stuff_management_reactivejava.activityLogClean.domain.commands.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRequestDto {
    private String traceId;
    private String httpStatus;
    private String httpMethod;
    private LocalDateTime createdOn;
    private String createdBy;
    private String requestUrl;
}
