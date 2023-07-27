package com.example.stuff_management_reactivejava.activityLogClean.domain;

import lombok.*;

import java.time.LocalDateTime;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    private String traceId;
    private String httpStatus;
    private String httpMethod;
    private LocalDateTime createdOn;
    private String createdBy;
    private String requestUrl;
}
