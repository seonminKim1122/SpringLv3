package com.sparta.springlv3.service;

import com.sparta.springlv3.dto.StatusResponseDto;
import com.sparta.springlv3.dto.UserRequestDto;
import com.sparta.springlv3.entity.User;
import com.sparta.springlv3.repository.UserRepository;
import com.sparta.springlv3.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public StatusResponseDto signup(UserRequestDto userRequestDto) {
        Optional<User> found = userRepository.findById(userRequestDto.getUsername());

        if (found.isPresent()) {
            return new StatusResponseDto("중복된 username 입니다.", HttpStatus.BAD_REQUEST);
        }

        User user = new User(userRequestDto);
        userRepository.save(user);
        return new StatusResponseDto("회원가입 성공!!", HttpStatus.OK);
    }

    public StatusResponseDto login(UserRequestDto userRequestDto, HttpServletResponse response) {
        try {
            User user = userRepository.findById(userRequestDto.getUsername()).orElseThrow(
                    () -> new NullPointerException("회원을 찾을 수 없습니다.")
            );

            if (user.getPassword().equals(userRequestDto.getPassword())) {
                response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.isAdmin()));
                return new StatusResponseDto("로그인 성공!!", HttpStatus.OK);
            }
            return new StatusResponseDto("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        } catch(NullPointerException e) {
            return new StatusResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
