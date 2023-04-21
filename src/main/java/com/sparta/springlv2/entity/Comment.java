package com.sparta.springlv2.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne // memo - comment 연관관계의 주인
    @JoinColumn(name = "memo_id")
    private Memo memo;

    public void setMemo(Memo memo) {
        this.memo = memo;
        memo.getComments().add(this);
    }
}
