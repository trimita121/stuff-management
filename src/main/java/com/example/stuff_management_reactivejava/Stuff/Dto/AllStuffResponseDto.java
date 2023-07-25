package com.example.stuff_management_reactivejava.Stuff.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class AllStuffResponseDto {
    List<StuffDto> stuffDtoList;
    String message;
  /*private String id;
    private String stuffId;
    private String stuffNickName;
    private String stuffFullName;
    private String stuffDesignation;*/
}
