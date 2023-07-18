package com.example.umm.user.service;

import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.user.dto.ProfileResponseDto;
import com.example.umm.user.dto.SignupRequestDto;
import com.example.umm.user.entity.User;
import com.example.umm.user.entity.UserRoleEnum;
import com.example.umm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService  {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto requestDto) {
        //email 중복 확인
        Optional<User> checkEmail = userRepository.findByEmail(requestDto.getEmail());
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }
        String password = passwordEncoder.encode(requestDto.getPassword());
        UserRoleEnum role = UserRoleEnum.USER;
        userRepository.save(new User(requestDto.getEmail(),password,requestDto.getNickname(),role));
    }

    public ProfileResponseDto findUserProfile(UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(()->
                new NullPointerException("해당 사용자가 존재하지 않습니다."));

        return new ProfileResponseDto(user);
    }
}
