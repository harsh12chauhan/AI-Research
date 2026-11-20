package com.harsh.research.user;

import com.harsh.research.common.ApiResponse;
import com.harsh.research.user.dto.AuthResponse;
import com.harsh.research.user.dto.LoginRequest;
import com.harsh.research.user.dto.RegisterRequest;
import com.harsh.research.user.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) throws  Exception{

        AuthResponse authResponse = authService.register(request);

        return ResponseEntity.ok(
                ApiResponse.Ok(
                        authResponse
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) throws  Exception{

        AuthResponse authResponse = authService.login(request);

        return ResponseEntity.ok(
                ApiResponse.Ok(
                        authResponse
                )
        );
    }





}
