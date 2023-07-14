package com.website.ventevoiture.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {

    private Boolean success;
    private String message;
    private Object result;
    private int status;
    private List<String> errors;
}
