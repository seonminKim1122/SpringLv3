package com.sparta.springlv2.controller;

import com.sparta.springlv2.dto.CommentRequestDto;
import com.sparta.springlv2.dto.CommentResponseDto;
import com.sparta.springlv2.dto.GeneralResponseDto;
import com.sparta.springlv2.dto.StatusResponseDto;
import com.sparta.springlv2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/memo/{memoId}/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create")
    public GeneralResponseDto create(@PathVariable Long memoId, @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        return commentService.create(memoId, requestDto, request);
    }

    @PutMapping("/{commentId}")
    public GeneralResponseDto update(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        return commentService.update(commentId, requestDto, request);
    }

    @DeleteMapping("/{commentId}")
    public StatusResponseDto delete(@PathVariable Long commentId, HttpServletRequest request) {
        return commentService.delete(commentId, request);
    }
}
