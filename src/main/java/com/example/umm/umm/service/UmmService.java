package com.example.umm.umm.service;

import com.example.umm.likeit.entity.LikeIt;
import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.umm.dto.UmmRequestDto;
import com.example.umm.umm.dto.UmmResponseDto;
import com.example.umm.umm.entity.ReUmm;
import com.example.umm.umm.entity.Umm;
import com.example.umm.umm.repository.ReUmmRepository;
import com.example.umm.umm.repository.UmmRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UmmService {
    private final UmmRepository ummRepository;
    private final ReUmmRepository reUmmRepository;

    public void create(UserDetailsImpl userDetails, UmmRequestDto ummRequestDto) {
        ummRepository.save(new Umm(userDetails,ummRequestDto));
    }


    @Transactional
    public void update(Long ummId, UserDetailsImpl userDetails, UmmRequestDto ummRequestDto) {
        Umm umm = ummRepository.findById(ummId).orElseThrow(
                ()->new NullPointerException("not found umm")
        );
        if(!umm.getUser().getId().equals(userDetails.getUser().getId())){
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        umm.update(ummRequestDto);

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
       return ummRepository.findAllByOrderByModifiedAtDesc().stream().map(UmmResponseDto::new).toList();
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
