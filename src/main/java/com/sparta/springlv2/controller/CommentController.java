package com.sparta.springlv2.controller;

import com.sparta.springlv2.dto.CommentRequestDto;
import com.sparta.springlv2.dto.CommentResponseDto;
import com.sparta.springlv2.dto.GeneralResponseDto;
import com.sparta.springlv2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/memo/{memo_id}/comment/create")
    public GeneralResponseDto create(@PathVariable Long memo_id, @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        return commentService.create(memo_id, requestDto, request);
    }
}
