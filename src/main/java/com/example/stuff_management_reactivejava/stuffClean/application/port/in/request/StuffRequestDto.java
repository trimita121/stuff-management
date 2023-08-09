package com.example.stuff_management_reactivejava.stuffClean.application.port.in.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StuffRequestDto {
    private String stuffId;
    private String stuffNickName;
    private String stuffFullName;
    private String stuffDesignation;
}
