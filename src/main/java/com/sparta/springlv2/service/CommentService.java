package com.sparta.springlv2.service;

import com.sparta.springlv2.dto.*;
import com.sparta.springlv2.entity.Comment;
import com.sparta.springlv2.entity.Memo;
import com.sparta.springlv2.entity.User;
import com.sparta.springlv2.repository.CommentRepository;
import com.sparta.springlv2.repository.MemoRepository;
import com.sparta.springlv2.repository.UserRepository;
import com.sparta.springlv2.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final MemoRepository memoRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    public GeneralResponseDto create(Long memo_id, CommentRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims = jwtUtil.getUserInfoFromToken(token);

        if (claims == null) {
            return new StatusResponseDto("토큰이 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        try {
            Memo memo = memoRepository.findById(memo_id).orElseThrow(
                    () -> new NullPointerException("존재하지 않는 게시글입니다.")
            );
            User user = userRepository.findById(claims.getSubject()).orElseThrow(
                    () -> new NullPointerException("등록되지 않은 회원입니다.")
            );

            Comment comment = new Comment(requestDto);
            comment.setMemo(memo);
            comment.setUser(user);

            commentRepository.save(comment);
            return new CommentResponseDto(comment);
        } catch (NullPointerException e) {
            return new StatusResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
