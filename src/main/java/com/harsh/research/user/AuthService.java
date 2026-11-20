package com.harsh.research.user;

import com.harsh.research.security.JwtService;
import com.harsh.research.user.dto.AuthResponse;
import com.harsh.research.user.dto.LoginRequest;
import com.harsh.research.user.dto.RegisterRequest;
import com.harsh.research.user.dto.UserResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    @Transactional
   public AuthResponse register(RegisterRequest request) throws Exception{

       String email = request.email().trim().toLowerCase();

       if(userRepository.existsByEmail(email)){
           throw new Exception("User with email already exists");
       }

       if(CommonPasswords.LIST.contains(request.password())){
           throw new Exception("that password is too common - try another one");
       }

       User user = User.register(email, passwordEncoder.encode(request.password()),request.fullName());

       user = userRepository.save(user);

       return toAuthResponse(user);
   }

   public AuthResponse login(LoginRequest request) throws Exception{

       String email = request.email().trim().toLowerCase();

       User user = userRepository.findByEmail(email).orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

       if(!passwordEncoder.matches(request.password(),user.getPasswordHash())){
           throw new BadCredentialsException("Invalid username or password");
       }

       if(user.getStatus() == UserStatus.SUSPEND){
           throw new Exception("this account has been suspended");
       }

       return toAuthResponse(user);
   }

   private AuthResponse toAuthResponse(User user){

        String jwt_token = jwtService.generateAccessToken(
                user.getId(), user.getEmail(),user.getRole().name()
        );
       return new AuthResponse(
               jwt_token,
               UserResponse.from(user)
       );
   }
}
