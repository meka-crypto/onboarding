package com.mdsl.onboarding.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    DISABLED(0),
    ENABLED(1);

    private final int value;
}
