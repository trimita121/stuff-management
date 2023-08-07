package com.example.stuff_management_reactivejava.stuffClean.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Stuff {
    private String id;
    private String stuffId;
    private String nickName;
    private String fullName;
    private String designation;
    private LocalDateTime createdOn;
    private String createdBy;
}
