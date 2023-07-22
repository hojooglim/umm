package com.example.umm.umm.service;

import com.example.umm.common.config.AwsS3UpLoader;
import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.umm.dto.UmmRequestDto;
import com.example.umm.umm.dto.UmmResponseDto;
import com.example.umm.umm.entity.ReUmm;
import com.example.umm.umm.entity.Umm;
import com.example.umm.umm.repository.ReUmmRepository;
import com.example.umm.umm.repository.UmmRepository;
import com.example.umm.user.entity.UserRoleEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UmmService {
    private final UmmRepository ummRepository;
    private final ReUmmRepository reUmmRepository;
    private final AwsS3UpLoader awsS3UpLoader;

    public void create(UserDetailsImpl userDetails, MultipartFile image, String contents) throws IOException {
        if (image != null) {
            String imageUrl = awsS3UpLoader.upload(image, "image");
            Umm umm = new Umm(userDetails,contents, imageUrl);
            ummRepository.save(umm);
        } else{
            ummRepository.save((new Umm(userDetails,contents)));
        }

    }

    @Transactional
    public void update(Long ummId, UserDetailsImpl userDetails, MultipartFile image, String contents) throws IOException {
        Umm umm = ummRepository.findById(ummId).orElseThrow(
                ()->new NullPointerException("not found umm")
        );
        if(!umm.getUser().getId().equals(userDetails.getUser().getId())  ){
            if(userDetails.getUser().getRole() == UserRoleEnum.ADMIN){
                if (image != null) {
                    String imageUrl = awsS3UpLoader.upload(image, "image");
                    umm.update(contents,imageUrl);
                } else{
                    umm.update(contents);
                }
            }
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        if (image != null) {
            String imageUrl = awsS3UpLoader.upload(image, "image");
            umm.update(contents,imageUrl);
        } else{
            umm.update(contents);
        }
    }

    public void delete(Long ummId, UserDetailsImpl userDetails) {
        Umm umm = ummRepository.findById(ummId).orElseThrow(
                ()->new NullPointerException("not found umm")
        );
        if(!umm.getUser().getId().equals(userDetails.getUser().getId())){
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        ummRepository.delete(umm);
    }

    public UmmResponseDto getUmm(Long ummId) {
        Umm umm = ummRepository.findById(ummId).orElseThrow(
                ()->new NullPointerException("not found umm")
        );
        return new UmmResponseDto(umm);
    }

    public List<UmmResponseDto> getUmmList() {
        List<Umm> ummList = ummRepository.findAllByOrderByCreatedAtDesc();
        List<UmmResponseDto> ummListDto = new ArrayList<>();
        for (Umm umm : ummList) {
            ummListDto.add(new UmmResponseDto(umm));
        }
        Collections.reverse(ummList);

        return ummListDto;
    }

    public void reUmm(Long ummId, UserDetailsImpl userDetails) {
        Umm umm =ummRepository.findById(ummId).orElseThrow(
                ()-> new NullPointerException("not found umm")
        );

        Optional<ReUmm> reUmm = reUmmRepository.findByUmmAndUser(umm,userDetails.getUser());
        reUmm.ifPresentOrElse(
                checkreUmm -> { // 게시물과 유저를 통해 좋아요를 이미 누른게 확인이 되면 삭제
                    reUmmRepository.delete(reUmm.get());
                },
                () -> { // 좋아요를 아직 누르지 않았을 땐 추가
                    reUmmRepository.save(new ReUmm(umm,userDetails));
                }
        );

    }
}
