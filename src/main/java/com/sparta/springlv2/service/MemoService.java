package com.sparta.springlv2.service;

import com.sparta.springlv2.dto.GeneralResponseDto;
import com.sparta.springlv2.dto.MemoRequestDto;
import com.sparta.springlv2.dto.MemoResponseDto;
import com.sparta.springlv2.dto.StatusResponseDto;
import com.sparta.springlv2.entity.Comment;
import com.sparta.springlv2.entity.Memo;
import com.sparta.springlv2.entity.User;
import com.sparta.springlv2.repository.MemoRepository;
import com.sparta.springlv2.repository.UserRepository;
import com.sparta.springlv2.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemoService {

    private final JwtUtil jwtUtil;
    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    @Transactional
    public GeneralResponseDto createMemo(MemoRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims = jwtUtil.getUserInfoFromToken(token);

        try {
            User user = findUserByName(claims.getSubject());
            Memo memo = new Memo(requestDto);
            memo.setUser(user);
            memoRepository.save(memo);
            return new MemoResponseDto(memo);
        } catch (NullPointerException e) {
            return new StatusResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    public List<MemoResponseDto> getAllMemo() {
        List<Memo> memoList = memoRepository.findAll();
        return memoList.stream().sorted((memo1, memo2) -> memo2.getModifiedAt().compareTo(memo1.getModifiedAt())).map(MemoResponseDto::new).toList();
    }

    public GeneralResponseDto getMemo(Long id) {
        try {
            Memo memo = findMemoById(id);
            return new MemoResponseDto(memo);
        } catch (NullPointerException e){
            return new StatusResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public GeneralResponseDto updateMemo(Long id, MemoRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims = jwtUtil.getUserInfoFromToken(token);
        try {
            Memo memo = findMemoById(id);
            if (memo.getUser().getUsername().equals(claims.getSubject())) {
                memo.update(requestDto);
                return new MemoResponseDto(memo);
            }

            return new StatusResponseDto("직접 작성한 게시글만 수정할 수 있습니다.", HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e) {
            return new StatusResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional
    public StatusResponseDto deleteMemo(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims = jwtUtil.getUserInfoFromToken(token);

        try {
            Memo memo = findMemoById(id);
            if (memo.getUser().getUsername().equals(claims.getSubject())) {
                memoRepository.delete(memo);
                return new StatusResponseDto("삭제 성공!!", HttpStatus.OK);
            }

            return new StatusResponseDto("직접 작성한 게시글만 삭제할 수 있습니다.", HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e) {
            return new StatusResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    public Memo findMemoById(Long id) {
        return memoRepository.findById(id).orElseThrow(
                () -> new NullPointerException("입력하신 id의 게시글이 없습니다.")
        );
    }

    public User findUserByName(String username) {
        return userRepository.findById(username).orElseThrow(
                () -> new NullPointerException("등록되지 않은 사용자입니다.")
        );
    }
}
