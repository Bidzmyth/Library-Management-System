package com.ey.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ey.domain.entity.Member;
import com.ey.domain.enums.MemberStatus;
import com.ey.dto.request.MemberCreateRequest;
import com.ey.dto.request.MemberUpdateRequest;
import com.ey.exception.BadRequestException;
import com.ey.exception.NotFoundException;
import com.ey.mapper.MemberMapper;
import com.ey.repository.MemberRepository;

@Service
public class MemberService {
 private static final Logger log = LoggerFactory.getLogger(MemberService.class);
 private final MemberRepository repo;

 public MemberService(MemberRepository repo) { this.repo = repo; }

 public Member create(MemberCreateRequest r) {
     repo.findByEmailIgnoreCase(r.getEmail()).ifPresent(m -> {
         throw new BadRequestException("Member already exists with email: " + r.getEmail());
     });
     Member m = MemberMapper.toEntity(r);
     log.info("Creating member: {}", r.getEmail());
     return repo.save(m);
 }

 public Member get(Long id) {
     return repo.findById(id).orElseThrow(() -> new NotFoundException("Member not found: " + id));
 }

 public List<Member> list() { return repo.findAll(); }

 public List<Member> search(String q) { return repo.findByFullNameContainingIgnoreCase(q); }

 public Member update(Long id, MemberUpdateRequest r) {
     Member m = get(id);
     if (r.getEmail() != null && !r.getEmail().equalsIgnoreCase(m.getEmail()) &&
             repo.findByEmailIgnoreCase(r.getEmail()).isPresent()) {
         throw new BadRequestException("Email already in use: " + r.getEmail());
     }
     MemberMapper.update(m, r);
     log.info("Updating member id={}", id);
     return repo.save(m);
 }

 public Member suspend(Long id) {
     Member m = get(id);
     m.setStatus(MemberStatus.SUSPENDED);
     log.warn("Suspending member id={}", id);
     return repo.save(m);
 }

 public Member unsuspend(Long id) {
     Member m = get(id);
     m.setStatus(MemberStatus.ACTIVE);
     log.info("Unsuspending member id={}", id);
     return repo.save(m);
 }

 public void delete(Long id) {
     log.warn("Deleting member id={}", id);
     repo.delete(get(id));
 }
}

