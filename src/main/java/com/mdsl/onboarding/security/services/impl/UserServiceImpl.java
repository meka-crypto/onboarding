package com.mdsl.onboarding.security.services.impl;

import com.mdsl.onboarding.security.models.User;
import com.mdsl.onboarding.security.repositories.UserRepository;
import com.mdsl.onboarding.security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public Class getModelClass() {
        return User.class;
    }

    @Override
    public UserRepository getRepository() {
        return userRepository;
    }

    @Override
    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
