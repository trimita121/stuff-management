package com.example.stuff_management_reactivejava.stuffClean.application.port.in.response;

import com.example.stuff_management_reactivejava.stuffClean.Domain.Stuff;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class StuffResponseDto {
    private List<Stuff> stuffList;
    private String message;
}
