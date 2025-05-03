package com.mdsl.onboarding.security.controllers;

import com.mdsl.onboarding.common.enums.Status;
import com.mdsl.onboarding.common.exceptions.CustomServiceException;
import com.mdsl.onboarding.common.response.ApiResponse;
import com.mdsl.onboarding.security.dtos.UserRequestDTO;
import com.mdsl.onboarding.security.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "User Apis")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder encoder;

    @PostMapping()
    public ApiResponse<String> registerUser(@RequestBody UserRequestDTO user) throws CustomServiceException {
        if (userService.existsByUsername(user.getUsername())) {
            return ApiResponse.success("Error: Username is already taken!", true);
        }
        // Create new user
        user.setStatus(Status.ENABLED);
        user.setUsername(user.getUsername());
        user.setPassword(encoder.encode(user.getPassword()));
        userService.saveNew(user);
        return ApiResponse.created("User registered successfully!", "success", true);
    }
}
