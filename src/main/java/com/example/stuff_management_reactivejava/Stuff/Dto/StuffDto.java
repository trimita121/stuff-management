package com.example.stuff_management_reactivejava.Stuff.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class StuffDto {
    private String id;
    private String stuffId;
    private String stuffNickName;
    private String stuffFullName;
    private String stuffDesignation;

}
