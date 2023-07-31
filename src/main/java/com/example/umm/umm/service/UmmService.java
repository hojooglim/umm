package com.example.umm.umm.service;

import com.example.umm.common.config.AwsS3UpLoader;
import com.example.umm.follow.entity.Follow;
import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.umm.dto.UmmResponseDto;
import com.example.umm.umm.entity.ReUmm;
import com.example.umm.umm.entity.Umm;
import com.example.umm.umm.repository.ReUmmRepository;
import com.example.umm.umm.repository.UmmRepository;
import com.example.umm.user.entity.User;
import com.example.umm.user.entity.UserRoleEnum;
import com.example.umm.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final UserRepository userRepository;

    public void create(UserDetailsImpl userDetails, MultipartFile image, String contents) throws IOException {
        if (image != null) {
            String imageUrl = awsS3UpLoader.upload(image, "image");
            Umm umm = new Umm(userDetails, contents, imageUrl);
            ummRepository.save(umm);
        } else {
            ummRepository.save((new Umm(userDetails, contents)));
        }

    }

    @Transactional
    public void update(Long ummId, UserDetailsImpl userDetails, MultipartFile image, String contents) throws IOException {
        Umm umm = ummRepository.findById(ummId).orElseThrow(
                () -> new NullPointerException("not found umm")
        );
        if (!umm.getUser().getId().equals(userDetails.getUser().getId())) {
            if (userDetails.getUser().getRole() == UserRoleEnum.ADMIN) {
                if (image != null) {
                    String imageUrl = awsS3UpLoader.upload(image, "image");
                    umm.update(contents, imageUrl);
                } else {
                    umm.update(contents);
                }
            }
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        if (image != null) {
            String imageUrl = awsS3UpLoader.upload(image, "image");
            umm.update(contents, imageUrl);
        } else {
            umm.update(contents);
        }
    }

    public void delete(Long ummId, UserDetailsImpl userDetails) {
        Umm umm = ummRepository.findById(ummId).orElseThrow(
                () -> new NullPointerException("not found umm")
        );
        if (!umm.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        ummRepository.delete(umm);
    }

    public UmmResponseDto getUmm(Long ummId) {
        Umm umm = ummRepository.findById(ummId).orElseThrow(
                () -> new NullPointerException("not found umm")
        );
        return new UmmResponseDto(umm);
    }

    public List<UmmResponseDto> getUmmList(UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                () -> new NullPointerException("not found user")
        );
        List<Follow> followList = user.getFollowList();
        List<Long> followUserId = new ArrayList<>();
        for (Follow follow : followList) {
            followUserId.add(follow.getFollowingUser().getId());
        }
        //내가 팔로우 한 사람들 의 아이디
        //아이디 한개씩 꺼네서.
        //그 아이디의 umm리스트를 가져옴.
        //1개 아이디에 여러개의 음 리스트


        List<UmmResponseDto> ummListDto = new ArrayList<>();

        for (Long id : followUserId) {
            List<Umm> ummlist = ummRepository.findAllByUserId(id);
            for (Umm umm : ummlist) {
                ummListDto.add(new UmmResponseDto(umm));
            }
        }

        List<Umm> myUmmList = ummRepository.findAllByUserId(userDetails.getUser().getId());


        for (Umm umm : myUmmList) {
            ummListDto.add(new UmmResponseDto(umm));
        }
        Collections.reverse(ummListDto);

        return ummListDto;

    }

    public List<UmmResponseDto> getAllUmmList() {
        List<Umm> ummList = ummRepository.findAll();

        List<UmmResponseDto> ummDtoList = new ArrayList<>();

        for (Umm umm : ummList) {
            ummDtoList.add(new UmmResponseDto(umm));
        }

        Collections.reverse(ummDtoList);

        return ummDtoList;
    }

    public void reUmm(Long ummId, UserDetailsImpl userDetails) {
        Umm umm = ummRepository.findById(ummId).orElseThrow(
                () -> new NullPointerException("not found umm")
        );

        Optional<ReUmm> reUmm = reUmmRepository.findByUmmAndUser(umm, userDetails.getUser());
        reUmm.ifPresentOrElse(
                checkreUmm -> { // 게시물과 유저를 통해 좋아요를 이미 누른게 확인이 되면 삭제
                    reUmmRepository.delete(reUmm.get());
                },
                () -> { // 좋아요를 아직 누르지 않았을 땐 추가
                    reUmmRepository.save(new ReUmm(umm, userDetails));
                }
        );

    }


    public List<UmmResponseDto> getUmmScroll(int size) {
        PageRequest pageRequest = PageRequest.of(0, size);
        Page<Umm> ummPage = ummRepository.findAll(pageRequest);
        List<Umm> ummList = ummPage.getContent();
        return ummList.stream().map(UmmResponseDto::new).toList();
    }
}
