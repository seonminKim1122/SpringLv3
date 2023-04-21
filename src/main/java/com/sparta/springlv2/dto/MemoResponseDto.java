package com.sparta.springlv2.dto;

import com.sparta.springlv2.entity.Memo;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemoResponseDto implements GeneralResponseDto {
    private String title;
    private String name;
    private String content;
    private LocalDate modifiedAt;

    public MemoResponseDto(Memo memo) {
        this.title = memo.getTitle();
        this.name = memo.getUser().getUsername();
        this.content = memo.getContent();
        this.modifiedAt = memo.getModifiedAt().toLocalDate();
    }
}
