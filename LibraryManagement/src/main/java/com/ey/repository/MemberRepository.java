package com.ey.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ey.domain.entity.Member;
import com.ey.domain.enums.MemberStatus;

public interface MemberRepository extends JpaRepository<Member, Long> {
 Optional<Member> findByEmailIgnoreCase(String email);
 List<Member> findByFullNameContainingIgnoreCase(String q);
 List<Member> findByStatus(MemberStatus status);
}


