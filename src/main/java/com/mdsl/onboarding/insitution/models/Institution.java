package com.mdsl.onboarding.insitution.models;

import com.mdsl.onboarding.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "institution", schema = "public")
@SQLDelete(sql = "UPDATE public.institution SET status = 0 WHERE id = ?")
public class Institution extends BaseEntity {

    @NotNull(message = "code cannot be null")
    @Digits(integer = 5, fraction = 0)
    @Column(name = "code", unique = true, nullable = false)
    private Long code;

    @NotNull(message = "name cannot be null")
    @Length(message = "value should be of length 50", min = 1, max = 50)
    @Column(name = "name", nullable = false)
    private String name;
}