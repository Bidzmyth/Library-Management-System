package com.ey.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ey.domain.entity.Fine;
import com.ey.domain.entity.Member;

public interface FineRepository extends JpaRepository<Fine, Long> {
 List<Fine> findByMember(Member member);
    List<Fine> findByPaid(boolean paid);
}
