package com.example.stuff_management_reactivejava.stuffClean.application.port.in.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllStuffResponseDto {
    List<StuffResponseDto> stuffResponseDtos;
    String message;

}
