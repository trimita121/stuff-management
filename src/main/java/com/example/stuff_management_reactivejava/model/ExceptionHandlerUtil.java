package com.example.stuff_management_reactivejava.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ExceptionHandlerUtil extends Exception{
    private HttpStatus code;
    private String message;
}
