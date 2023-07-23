package com.example.umm.comment.service;

import com.example.umm.comment.dto.CommentRequestDto;
import com.example.umm.comment.entity.Comment;
import com.example.umm.comment.entity.ReComment;
import com.example.umm.comment.repository.CommentRepository;
import com.example.umm.comment.repository.ReCommentRepository;
import com.example.umm.security.filter.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReCommentService {
    private final ReCommentRepository reCommentRepository;
    private final CommentRepository commentRepository;

    public void createReComment(UserDetailsImpl userDetails, CommentRequestDto requestDto, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new NullPointerException("not found comment")
        );
        reCommentRepository.save(new ReComment(requestDto, userDetails, comment));
    }

    @Transactional
    public void updateReComment(UserDetailsImpl userDetails, Long id, CommentRequestDto requestDto) {
        ReComment reComment = reCommentRepository.findById(id).orElseThrow(() ->
                new NullPointerException("not found comment")
        );
        if (userDetails.getUser().getId().equals(reComment.getUser().getId())) {
            reComment.update(requestDto);
        }
    }

    public void deleteReComment(UserDetailsImpl userDetails, Long id) {
        ReComment reComment = reCommentRepository.findById(id).orElseThrow(() ->
                new NullPointerException("not found comment")
        );
        if (userDetails.getUser().getId().equals(reComment.getUser().getId())) {
            reCommentRepository.delete(reComment);
        }
    }
}
