package com.sparta.springlv2.repository;

import com.sparta.springlv2.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}
