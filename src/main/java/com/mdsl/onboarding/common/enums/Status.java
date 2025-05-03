package com.mdsl.onboarding.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    DISABLED(0),
    ENABLED(1);

    private final int value;

    public static Status fromValue(int value) {
        return switch (value) {
            case 0 -> DISABLED;
            case 1 -> ENABLED;
            default -> null;
        };
    }
}
