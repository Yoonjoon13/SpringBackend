package com.example.springbackend.user;

import com.example.springbackend.user.userModel.User;
import com.example.springbackend.user.userModel.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
//@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserDto.SignupRes save(UserDto.SignupReq request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        if (userRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        User user = request.toEntity();
        User saved = userRepository.save(user);
        return UserDto.SignupRes.from(saved);
    }

    public UserDto.LoginRes login(UserDto.LoginReq request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!request.getPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return UserDto.LoginRes.from(user);
    }
}
