package com.sparta.hanghaeblog.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class RestApiException {
    private String errorMessage;
    private HttpStatus httpStatus;

    @Builder
    public RestApiException(String errorMessage, HttpStatus httpStatus){
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
