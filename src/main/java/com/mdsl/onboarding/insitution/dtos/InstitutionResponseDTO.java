package com.mdsl.onboarding.insitution.dtos;

import com.mdsl.onboarding.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionResponseDTO {
    private Long id;
    private Long code;
    private String name;
    private Status status;
}
