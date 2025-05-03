package com.mdsl.onboarding.security.config;

import com.mdsl.onboarding.common.enums.Status;
import com.mdsl.onboarding.common.exceptions.CustomServiceException;
import com.mdsl.onboarding.security.dtos.UserRequestDTO;
import com.mdsl.onboarding.security.models.User;
import com.mdsl.onboarding.security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class UserConfig {

    private final PasswordEncoder encoder;
    private final UserService userService;

    @Value("${user.admin.username}")
    private String adminUserUsername;

    @Value("${user.admin.password}")
    private String adminUserPassword;

    @Bean
    public User addAdmin() throws CustomServiceException {
        Optional<User> admin = userService.findByUsername(adminUserUsername);
        if (admin.isEmpty()) {
            UserRequestDTO userRequestDTO = new UserRequestDTO(adminUserUsername, encoder.encode(adminUserPassword), Status.ENABLED);
            return userService.saveNew(userRequestDTO);
        }
        return admin.get();
    }
}
