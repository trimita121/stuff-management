package com.example.stuff_management_reactivejava.stuffClean.application.port.in.response;

import com.example.stuff_management_reactivejava.stuffClean.application.port.in.request.StuffRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class StuffResponseDto {
    private StuffRequestDto stuffRequestDto;
    String message;
}
