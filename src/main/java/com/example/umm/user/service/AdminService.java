package com.example.umm.user.service;

import com.example.umm.common.config.AwsS3UpLoader;
import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.umm.dto.UmmResponseDto;
import com.example.umm.umm.entity.Umm;
import com.example.umm.umm.repository.UmmRepository;
import com.example.umm.user.dto.ProfileResponseDto;
import com.example.umm.user.entity.User;
import com.example.umm.user.entity.UserRoleEnum;
import com.example.umm.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final UmmRepository ummRepository;
    private final AwsS3UpLoader awsS3UpLoader;

    public List<ProfileResponseDto> getUsersInfo(UserDetailsImpl userDetails) {

        if(userDetails.getUser().getRole() == UserRoleEnum.ADMIN){
            List<ProfileResponseDto> profileResponseDtoList = userRepository.findAll().stream().map(ProfileResponseDto::new).toList();
            return profileResponseDtoList;
        } else {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    public ProfileResponseDto getuserInfo(UserDetailsImpl userDetails, Long userId) {
        if(userDetails.getUser().getRole() == UserRoleEnum.ADMIN){
            User user = userRepository.findById(userId).orElseThrow(
                    ()-> new NullPointerException("회원이 없습니다.")
            );
            return new ProfileResponseDto(user);
        } else {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    @Transactional
    public void updateUserInfo(UserDetailsImpl userDetails, Long userId, String nickname, String myComment, MultipartFile image) throws IOException {
        if(userDetails.getUser().getRole() == UserRoleEnum.ADMIN){
            User user = userRepository.findById(userId).orElseThrow(
                    ()-> new NullPointerException("회원이 없습니다.")
            );
            if (image != null) {
                String imageUrl = awsS3UpLoader.upload(image, "image");
                user.updateProfile(nickname, myComment,imageUrl);
            } else{
                user.updateProfile(nickname, myComment);
            }
        } else {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    public void deleteUserInfo(UserDetailsImpl userDetails, Long userId) {
        if(userDetails.getUser().getRole() == UserRoleEnum.ADMIN){
            User user = userRepository.findById(userId).orElseThrow(
                    ()-> new NullPointerException("회원이 없습니다.")
            );
            userRepository.delete(user);
        } else {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    @Transactional
    public void chageUserAuth(UserDetailsImpl userDetails, Long userId) {
        if(userDetails.getUser().getRole() == UserRoleEnum.ADMIN){
            User user = userRepository.findById(userId).orElseThrow(
                    ()-> new NullPointerException("회원이 없습니다.")
            );
            user.updateRole(UserRoleEnum.GUEST);
        } else {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    public List<UmmResponseDto> getUmms(UserDetailsImpl userDetails) {
        if(userDetails.getUser().getRole() == UserRoleEnum.ADMIN){
            List<UmmResponseDto> ummResponseDtoList = ummRepository.findAll().stream().map(UmmResponseDto::new).toList();
            return ummResponseDtoList;
        } else {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    public UmmResponseDto getUmm(UserDetailsImpl userDetails, Long ummId) {
        if(userDetails.getUser().getRole() == UserRoleEnum.ADMIN){
            Umm umm = ummRepository.findById(ummId).orElseThrow(
                    ()-> new NullPointerException("회원이 없습니다.")
            );
            return new UmmResponseDto(umm);
        } else {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    @Transactional
    public void updateUmm(UserDetailsImpl userDetails, Long ummId, MultipartFile image, String contents) throws IOException {
        if(userDetails.getUser().getRole() == UserRoleEnum.ADMIN){
            Umm umm = ummRepository.findById(ummId).orElseThrow(
                    ()->new NullPointerException("not found umm")
            );
            if (image != null) {
                String imageUrl = awsS3UpLoader.upload(image, "image");
                umm.update(contents,imageUrl);
            } else{
                umm.update(contents);
            }
        } else {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    public void deleteUmm(UserDetailsImpl userDetails, Long ummId) {
        if(userDetails.getUser().getRole() == UserRoleEnum.ADMIN){
            Umm umm = ummRepository.findById(ummId).orElseThrow(
                    ()-> new NullPointerException("회원이 없습니다.")
            );
            ummRepository.delete(umm);
        } else {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }


}
