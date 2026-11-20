package com.harsh.research.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getById(UUID id) throws Exception{
        return userRepository.findById(id).orElseThrow(()->new Exception("User id not found"));
    }

}
