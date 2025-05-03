package com.mdsl.onboarding.security.services;

import com.mdsl.onboarding.common.service.CUDService;
import com.mdsl.onboarding.common.service.FindService;
import com.mdsl.onboarding.security.dtos.UserRequestDTO;
import com.mdsl.onboarding.security.models.User;
import com.mdsl.onboarding.security.repositories.UserRepository;

import java.util.Optional;

public interface UserService extends FindService<User, UserRepository>, CUDService<User, UserRequestDTO, UserRepository> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
