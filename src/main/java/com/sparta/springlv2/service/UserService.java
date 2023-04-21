package com.sparta.springlv2.service;

import com.sparta.springlv2.dto.StatusResponseDto;
import com.sparta.springlv2.dto.UserRequestDto;
import com.sparta.springlv2.entity.User;
import com.sparta.springlv2.repository.UserRepository;
import com.sparta.springlv2.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    public StatusResponseDto signup(UserRequestDto userRequestDto) {
        Optional<User> found = userRepository.findById(userRequestDto.getUsername());

        if (found.isPresent()) {
            return new StatusResponseDto("이미 회원가입한 사용자입니다.", HttpStatus.ALREADY_REPORTED);
        }

        User user = new User(userRequestDto);
        userRepository.save(user);
        return new StatusResponseDto("회원가입 성공!!", HttpStatus.OK);
    }

    public StatusResponseDto login(UserRequestDto userRequestDto, HttpServletResponse response) {
        try {
            User user = findUserByName(userRequestDto.getUsername());
            if (user.getPassword().equals(userRequestDto.getPassword())) {
                response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));
                return new StatusResponseDto("로그인 성공!!", HttpStatus.OK);
            }
            return new StatusResponseDto("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        } catch(NullPointerException e) {
            return new StatusResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public User findUserByName(String username) {
        return userRepository.findById(username).orElseThrow(
                () -> new NullPointerException("등록되지 않은 사용자입니다.")
        );
    }
}
