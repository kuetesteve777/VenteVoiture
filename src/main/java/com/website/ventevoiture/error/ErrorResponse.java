package com.website.ventevoiture.error;

import java.util.List;
import org.springframework.http.HttpStatus;

//@AllArgsConstructor
public class ErrorResponse {

    private HttpStatus status;

    private String errorMessage;

    private List<String> errors;

    public ErrorResponse(HttpStatus status, String errorMessage, List<String> errors) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.errors=errors;
    }
}
