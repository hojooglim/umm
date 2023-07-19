package com.example.umm.comment.service;


import com.example.umm.comment.dto.CommentRequestDto;
import com.example.umm.comment.dto.CommentResponseDto;
import com.example.umm.comment.entity.Comment;
import com.example.umm.comment.repository.CommentRepository;
import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.umm.entity.Umm;
import com.example.umm.umm.repository.UmmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UmmRepository ummRepository;

    public void createComment(UserDetailsImpl userDetails, CommentRequestDto requestDto, Long blog_id) {
        Umm umm = ummRepository.findById(blog_id).orElseThrow(() ->
                new RuntimeException("해당 피드를 찾을 수 없습니다.")
        );
        commentRepository.save(new Comment(requestDto, userDetails, umm));
    }

    @Transactional
    public void updateComment(UserDetailsImpl userDetails, Long id, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new RuntimeException("없는 댓글입니다.")
        );
        if (userDetails.getUser().getId().equals(comment.getUser().getId())) {
            comment.update(requestDto);
        }
    }

    public void deleteComment(UserDetailsImpl userDetails, Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new RuntimeException("없는 댓글입니다.")
        );
        if (userDetails.getUser().getId().equals(comment.getUser().getId())) {
            commentRepository.delete(comment);
        }
    }
}
