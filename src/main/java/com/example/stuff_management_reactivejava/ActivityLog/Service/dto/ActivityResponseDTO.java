package com.example.stuff_management_reactivejava.ActivityLog.Service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ActivityResponseDTO {
    private String stuffId;
    private String traceId;
    private String status;
    private String httpMethod;


}
