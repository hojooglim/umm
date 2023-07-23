package com.example.umm.comment.controller;

import com.example.umm.comment.dto.CommentRequestDto;
import com.example.umm.comment.service.ReCommentService;
import com.example.umm.security.filter.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReCommentController {
    private final ReCommentService reCommentService;

    @PostMapping("/reComments/{comment-id}")
    public void createReComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto, @PathVariable Long comment_id) {
        reCommentService.createReComment(userDetails, requestDto, comment_id);
    }

    @PutMapping("/reComments/{comment_id}")
    public void updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long comment_id, @RequestBody CommentRequestDto requestDto) {
        reCommentService.updateReComment(userDetails, comment_id, requestDto);
    }

    @DeleteMapping("/reComments/{comment_id}")
    public void deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long comment_id) {
        reCommentService.deleteReComment(userDetails, comment_id);
    }
}
