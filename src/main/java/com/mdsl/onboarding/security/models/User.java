package com.mdsl.onboarding.security.models;

import com.mdsl.onboarding.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user", schema = "public")
//@SQLDelete(sql = "UPDATE public.user SET status = 0 WHERE id = ?") soft delete upon requirement
public class User extends BaseEntity {
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
}