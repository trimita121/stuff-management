package com.example.stuff_management_reactivejava.activityLogClean.domain;

import lombok.*;

import java.time.LocalDateTime;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    private String id;
    private String stuffId;
    private String traceId;
    private String status;
    private String httpMethod;
    private LocalDateTime createdOn;
    private String editedBy;
    private String requestUrl;
}
