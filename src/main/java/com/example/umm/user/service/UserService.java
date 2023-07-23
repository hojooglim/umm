package com.example.umm.user.service;

import com.example.umm.common.config.AwsS3UpLoader;
import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.umm.entity.Umm;
import com.example.umm.user.dto.*;
import com.example.umm.user.entity.Password;
import com.example.umm.user.entity.User;
import com.example.umm.user.entity.UserRoleEnum;
import com.example.umm.user.repository.PasswordRepository;
import com.example.umm.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService  {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AwsS3UpLoader awsS3UpLoader;
    private final PasswordRepository passwordRepository;

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
        return new ProfileResponseDto(findUser(userDetails));
    }
    public ProfileResponseDto findUserIdProfile(Long userId) {
        User user=userRepository.findById(userId).orElseThrow(
                ()->new NullPointerException("해당 사용자가 존재하지 않습니다.")
        );
        return new ProfileResponseDto(user);
    }
    @Transactional
    public void updateProfile(UserDetailsImpl userDetails, String nickname, String myComment, MultipartFile image) throws IOException {
        User findUser = findUser(userDetails);
        if (image != null) {
            String imageUrl = awsS3UpLoader.upload(image, "image");
            findUser.updateProfile(nickname, myComment,imageUrl);
        } else{
            findUser.updateProfile(nickname, myComment);
        }
    }

    @Transactional
    public void checkPassword(UserDetailsImpl userDetails, CheckPasswordRequestDto requestDto) {
        if (!passwordEncoder.matches(requestDto.getPassword(), userDetails.getUser().getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    public void editPassword(UserDetailsImpl userDetails, EditPasswordRequestDto requestDto) {
        User user= userRepository.findById(userDetails.getUser().getId()).orElseThrow(()-> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        String password = passwordEncoder.encode(requestDto.getNew_password());
        user.updatePassword(password);

    }

    public User findUser(UserDetailsImpl userDetails){
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(()->
                new NullPointerException("해당 사용자가 존재하지 않습니다."));
        return user;
    }


    @Transactional
    public void PasswordChecker(EditPasswordRequestDto requestDto, UserDetailsImpl userDetails){
        //password 확인 패스워드 저장 따로 추가하로 for문 돌려서 일일히 확인
        //3개중에 맞는게 없어야 변경가능 하도록 조건 주고
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                ()->new NullPointerException("not fount user")
        );
        List<Password> userPasswordList = passwordRepository.findAllByUser(user);

        //움 최근 3번이니까 for문 거꾸로 돌려서 3번쨰까지만
        //size-4하면 오류날꺼같은데
//        for (int i = userPasswordList.size()-1; i > userPasswordList.size()-4  ; i--) {
//
//            if(passwordEncoder.matches(requestDto.getNew_password(), userPasswordList.get(i).toString())){
//                throw new IllegalArgumentException("last 3 password same");
//            }
//        }
        //콜렉션 써서 돌리고
        Collections.reverse(userPasswordList); // 54321
        for (int i = 0; i <3 ; i++) {
            if(passwordEncoder.matches(requestDto.getNew_password(), userPasswordList.get(i).toString())){
               throw new IllegalArgumentException("last 3 password same");
           }
        }


        //변경
        String encodePassword = passwordEncoder.encode(requestDto.getNew_password());
        passwordRepository.save(new Password(encodePassword, user));
        user.updatePassword(encodePassword);

    }

}
