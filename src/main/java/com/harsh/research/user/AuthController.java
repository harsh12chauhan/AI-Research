package com.harsh.research.user;

import com.harsh.research.common.ApiResponse;
import com.harsh.research.user.dto.AuthResponse;
import com.harsh.research.user.dto.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) throws  Exception{

        AuthResponse authResponse = authService.register(request);

        return ResponseEntity.ok(
                ApiResponse.Ok(
                        authResponse
                )
        );
    }

}
