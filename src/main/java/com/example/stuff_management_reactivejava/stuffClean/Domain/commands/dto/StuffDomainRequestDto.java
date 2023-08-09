package com.example.stuff_management_reactivejava.stuffClean.Domain.commands.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StuffDomainRequestDto {
    private String id;
    private String stuffId;
    private String nickName;
    private String fullName;
    private String designation;
}
