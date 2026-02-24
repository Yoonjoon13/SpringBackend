package com.example.springbackend.user;

import com.example.springbackend.user.userModel.User;
import com.example.springbackend.user.userModel.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserDto.SignupRes save(UserDto.SignupReq request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        if (userRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Username already exists.");
        }

        User user = request.toEntity();
        User saved = userRepository.save(user);
        return UserDto.SignupRes.from(saved);
    }
}
