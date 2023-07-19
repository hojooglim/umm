package com.example.umm.likeit.controller;



import com.example.umm.likeit.service.LikeItService;
import com.example.umm.security.filter.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeItController {
    private final LikeItService likeitService;

    @PostMapping("/blogLike/{blog_id}")
    public void ummLike(@PathVariable Long blog_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        likeitService.ummLike(blog_id,userDetails);

    }

    @PostMapping("/commentLike/{comment_id}")
    public void commentLike(@PathVariable Long comment_id,@AuthenticationPrincipal UserDetailsImpl userDetails){
        likeitService.commentLike(comment_id,userDetails);

    }

}
