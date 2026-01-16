package com.ey.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ey.domain.entity.Book;
import com.ey.domain.entity.Loan;
import com.ey.domain.entity.Member;
import com.ey.domain.enums.LoanStatus;
import com.ey.domain.enums.MemberStatus;
import com.ey.dto.request.LoanIssueRequest;
import com.ey.dto.request.LoanRenewRequest;
import com.ey.exception.BadRequestException;
import com.ey.exception.NotFoundException;
import com.ey.repository.BookRepository;
import com.ey.repository.LoanRepository;
import com.ey.repository.MemberRepository;

@Service
public class LoanService {
 private static final Logger log = LoggerFactory.getLogger(LoanService.class);
 private final LoanRepository loanRepo;
 private final MemberRepository memberRepo;
 private final BookRepository bookRepo;

 @Value("${library.loan.days:14}")
 private int defaultLoanDays;

 public LoanService(LoanRepository loanRepo, MemberRepository memberRepo, BookRepository bookRepo) {
     this.loanRepo = loanRepo; this.memberRepo = memberRepo; this.bookRepo = bookRepo;
 }

 public Loan issue(LoanIssueRequest r) {
     Member m = memberRepo.findById(r.getMemberId())
             .orElseThrow(() -> new NotFoundException("Member not found: " + r.getMemberId()));
     if (m.getStatus() == MemberStatus.SUSPENDED) throw new BadRequestException("Member is suspended");
     Book b = bookRepo.findById(r.getBookId())
             .orElseThrow(() -> new NotFoundException("Book not found: " + r.getBookId()));
     if (b.getAvailableCopies() <= 0) throw new BadRequestException("Book not available");
     b.setAvailableCopies(b.getAvailableCopies() - 1);
     bookRepo.save(b);

     Loan l = new Loan();
     l.setMember(m);
     l.setBook(b);
     l.setIssueDate(LocalDate.now());
     l.setDueDate(LocalDate.now().plusDays(defaultLoanDays));
     l.setStatus(LoanStatus.ISSUED);
     log.info("Issued book {} to member {}", b.getId(), m.getId());
     return loanRepo.save(l);
 }

 public Loan get(Long id) { return loanRepo.findById(id).orElseThrow(() -> new NotFoundException("Loan not found: " + id)); }

 public List<Loan> list() { return loanRepo.findAll(); }

 public List<Loan> byMember(Long memberId) {
     Member m = memberRepo.findById(memberId).orElseThrow(() -> new NotFoundException("Member not found: " + memberId));
     return loanRepo.findByMember(m);
 }

 public List<Loan> overdue() {
     return loanRepo.findByDueDateBeforeAndStatus(LocalDate.now(), LoanStatus.ISSUED);
 }

 public Loan renew(Long id, LoanRenewRequest r) {
     Loan l = get(id);
     if (l.getStatus() != LoanStatus.ISSUED) throw new BadRequestException("Only ISSUED loans can be renewed");
     l.setDueDate(l.getDueDate().plusDays(r.getAdditionalDays()));
     log.info("Renewed loan id={} by {} days", id, r.getAdditionalDays());
     return loanRepo.save(l);
 }

 public Loan returnBook(Long id) {
     Loan l = get(id);
     if (l.getStatus() != LoanStatus.ISSUED) throw new BadRequestException("Loan already returned/closed");
     l.setReturnDate(LocalDate.now());
     if (l.getReturnDate().isAfter(l.getDueDate())) {
         l.setStatus(LoanStatus.OVERDUE);
     } else {
         l.setStatus(LoanStatus.RETURNED);
     }
     // increase availability
     Book b = l.getBook();
     b.setAvailableCopies(b.getAvailableCopies() + 1);
     bookRepo.save(b);
     log.info("Returned loan id={}", id);
     return loanRepo.save(l);
 }

 public void delete(Long id) {
     log.warn("Deleting loan id={}", id);
     loanRepo.delete(get(id));
 }
}

