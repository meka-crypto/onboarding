package com.mdsl.onboarding.common.exceptions;

import com.mdsl.onboarding.common.enums.ErrorResponseCode;
import lombok.Getter;

@Getter
public class CustomServiceException extends Exception {
    
    private final ErrorResponseCode errorCode;

    public CustomServiceException(String message, ErrorResponseCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}