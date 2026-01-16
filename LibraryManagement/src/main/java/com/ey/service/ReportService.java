package com.ey.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ey.domain.entity.Book;
import com.ey.domain.entity.Member;
import com.ey.domain.enums.MemberStatus;
import com.ey.repository.BookRepository;
import com.ey.repository.LoanRepository;
import com.ey.repository.MemberRepository;

@Service
public class ReportService {
 private final BookRepository bookRepo;
 private final MemberRepository memberRepo;
 private final LoanRepository loanRepo;

 public ReportService(BookRepository bookRepo, MemberRepository memberRepo, LoanRepository loanRepo) {
     this.bookRepo = bookRepo; this.memberRepo = memberRepo; this.loanRepo = loanRepo;
 }

 public Map<String, Object> stats() {
     Map<String, Object> m = new HashMap<>();
     m.put("books", bookRepo.count());
     m.put("members", memberRepo.count());
     m.put("loans", loanRepo.count());
     m.put("activeMembers", memberRepo.findByStatus(MemberStatus.ACTIVE).size());
     return m;
 }

 public List<Book> topBooks() {
     // For simplicity return available() first; you can implement real ranking using custom queries.
     return bookRepo.findByAvailableCopiesGreaterThan(0);
 }

 public List<Member> activeMembers() {
     return memberRepo.findByStatus(MemberStatus.ACTIVE);
 }
}

