package com.mdsl.onboarding.common.exceptions;

import com.mdsl.onboarding.common.enums.ErrorResponseCode;
import com.mdsl.onboarding.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

// Class to handle Exceptions on Controllers level
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomServiceException.class)
    public ApiResponse<Object> handleCustomServiceException(CustomServiceException ex) {
        log.error(ex.toString());
        if (ex.getErrorCode().equals(ErrorResponseCode.NOT_FOUND)) {
            return ApiResponse.error(HttpStatus.NOT_FOUND, ex.getMessage(), false, Arrays.toString(ex.getStackTrace()));
        }
        else if (ex.getErrorCode().equals(ErrorResponseCode.NOT_NULL)) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST, ex.getMessage(), false, Arrays.toString(ex.getStackTrace()));
        }
        else return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), false, Arrays.toString(ex.getStackTrace()));
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Object> handleGenericException(Exception ex) {
        log.error(ex.toString());
        return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), false, Arrays.toString(ex.getStackTrace()));
    }
}