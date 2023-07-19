package com.example.umm.comment.controller;


import com.example.umm.comment.dto.CommentRequestDto;
import com.example.umm.comment.service.CommentService;
import com.example.umm.security.filter.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments/{umm_id}")
    public void createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto, @PathVariable Long umm_id) {
        commentService.createComment(userDetails, requestDto, umm_id);
    }

    @PutMapping("/comments/{comment_id}")
    public void updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long comment_id, @RequestBody CommentRequestDto requestDto) {
        commentService.updateComment(userDetails, comment_id, requestDto);
    }

    @DeleteMapping("/comments/{comment_id}") // 해당 코멘트 삭제
    public void deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long comment_id) {
        commentService.deleteComment(userDetails, comment_id);
    }
}
