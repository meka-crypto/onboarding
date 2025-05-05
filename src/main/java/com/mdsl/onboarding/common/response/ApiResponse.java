package com.mdsl.onboarding.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

// Custom Structured ResponseEntity
@Getter
public class ApiResponse<T> extends ResponseEntity<ApiResponse.Body<T>> {

    public ApiResponse(HttpStatus status, String message, T data, boolean valid, String stackTrace, Integer errorCode) {
        super(new Body<>(valid, message, data, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS).toString(), errorCode, stackTrace), status);
    }

    public static <T> ApiResponse<T> success(T data, boolean valid) {
        return new ApiResponse<>(HttpStatus.OK, "success", data, valid, "", null);
    }

    public static <T> ApiResponse<T> success(boolean valid) {
        return new ApiResponse<>(HttpStatus.OK, "success", null, valid, "", null);
    }

    public static <T> ApiResponse<T> created(T data, String message, boolean valid) {
        return new ApiResponse<>(HttpStatus.CREATED, message, data, valid, "", null);
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message, boolean valid, String stackTrace) {
        return new ApiResponse<>(status, message, null, valid, stackTrace, status.value());
    }

    @Getter
    public static class Body<T> {
        private final boolean valid;
        private final String message;
        private final T data;
        private final String timestamp;
        @JsonInclude(JsonInclude.Include.NON_NULL) // include if not null
        private final Integer errorCode;
        @JsonInclude(JsonInclude.Include.NON_EMPTY) // include if not empty
        private final String stackTrace;

        public Body(boolean valid, String message, T data, String timestamp, Integer errorCode, String stackTrace) {
            this.valid = valid;
            this.message = message;
            this.data = data;
            this.timestamp = timestamp;
            this.errorCode = errorCode;
            this.stackTrace = stackTrace;
        }
    }
}