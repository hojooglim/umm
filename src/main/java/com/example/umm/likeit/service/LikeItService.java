package com.example.umm.likeit.service;


import com.example.umm.comment.entity.Comment;
import com.example.umm.comment.repository.CommentRepository;
import com.example.umm.likeit.entity.LikeIt;
import com.example.umm.likeit.repository.LikeItRepository;
import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.umm.entity.Umm;
import com.example.umm.umm.repository.UmmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeItService {
    private final LikeItRepository likeItRepository;
    private final UmmRepository ummRepository;
    private final CommentRepository commentRepository;

    public void ummLike(Long blogId, UserDetailsImpl userDetails) {
        Umm umm = ummRepository.findById(blogId).orElseThrow(
                ()-> new NullPointerException("글이 존재하지 않습니다.")
        );

        Optional<LikeIt> ummLike = likeItRepository.findByUmmAndUser(umm, userDetails.getUser());

        ummLike.ifPresentOrElse(
                likeIt -> { // 게시물과 유저를 통해 좋아요를 이미 누른게 확인이 되면 삭제
                    likeItRepository.delete(ummLike.get());
                },
                () -> { // 좋아요를 아직 누르지 않았을 땐 추가
                    likeItRepository.save(new LikeIt(umm, userDetails.getUser()));
                }
        );

    }


    public void commentLike(Long commentId, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new NullPointerException("댓글이 존재하지 않습니다.")
        );

        Optional<LikeIt> commentLike = likeItRepository.findByCommentAndUser(comment, userDetails.getUser());

        commentLike.ifPresentOrElse(
                likeIt -> {
                    likeItRepository.delete(commentLike.get());
                },
                ()->{
                    likeItRepository.save(new LikeIt(comment,userDetails.getUser()));
                }
        );
    }
}
