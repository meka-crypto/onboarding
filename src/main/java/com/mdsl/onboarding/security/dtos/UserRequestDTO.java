package com.mdsl.onboarding.security.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mdsl.onboarding.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    private String username;
    private String password;
    @JsonIgnore
    private Status status;
}
