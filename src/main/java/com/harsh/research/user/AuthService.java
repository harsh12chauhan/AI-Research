package com.harsh.research.user;

import com.harsh.research.user.dto.AuthResponse;
import com.harsh.research.user.dto.RegisterRequest;
import com.harsh.research.user.dto.UserResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
   public AuthResponse register(RegisterRequest request) throws Exception{

       String email = request.email().trim().toLowerCase();

       if(userRepository.existsByEmail(email)){
           throw new Exception("User with email already exists");
       }

       if(CommonPasswords.LIST.contains(request.password())){
           throw new Exception("that password is too common - try another one");
       }

       User user = User.register(email, request.password(),request.fullName());

       user = userRepository.save(user);

       return toAuthResponse(user);
   }

   private AuthResponse toAuthResponse(User user){
       return new AuthResponse(
               "dummy token",
               UserResponse.from(user)
       );
   }
}
