package com.example.stuff_management_reactivejava.Stuff.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class StuffResponseDto {

    private StuffDto stuffDto;
    private String message;
}
