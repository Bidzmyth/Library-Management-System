package com.ey.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ey.domain.entity.Fine;
import com.ey.domain.entity.Loan;
import com.ey.domain.entity.Member;
import com.ey.domain.enums.LoanStatus;
import com.ey.dto.request.FinePayRequest;
import com.ey.exception.BadRequestException;
import com.ey.exception.NotFoundException;
import com.ey.repository.FineRepository;
import com.ey.repository.LoanRepository;
import com.ey.repository.MemberRepository;

@Service
public class FineService {
 private static final Logger log = LoggerFactory.getLogger(FineService.class);
 private final FineRepository fineRepo;
 private final MemberRepository memberRepo;
 private final LoanRepository loanRepo;

 @Value("${library.fine.per-day:5.0}")
 private double finePerDay;

 public FineService(FineRepository fineRepo, MemberRepository memberRepo, LoanRepository loanRepo) {
     this.fineRepo = fineRepo; this.memberRepo = memberRepo; this.loanRepo = loanRepo;
 }

 public Fine generateIfOverdue(Loan loan) {
     if (loan.getStatus() == LoanStatus.OVERDUE) {
         LocalDate refDate = loan.getReturnDate() != null ? loan.getReturnDate() : LocalDate.now();
         long days = Math.max(0, ChronoUnit.DAYS.between(loan.getDueDate(), refDate));
         BigDecimal amt = BigDecimal.valueOf(days * finePerDay).setScale(2, BigDecimal.ROUND_HALF_UP);
         Fine f = new Fine();
         f.setLoan(loan);
         f.setMember(loan.getMember());
         f.setAmount(amt);
         f.setPaid(false);
         log.info("Generated fine {} for loan {}", amt, loan.getId());
         return fineRepo.save(f);
     }
     return null;
 }

 public Fine get(Long id) { return fineRepo.findById(id).orElseThrow(() -> new NotFoundException("Fine not found: " + id)); }

 public List<Fine> list() { return fineRepo.findAll(); }

 public List<Fine> byMember(Long memberId) {
     Member m = memberRepo.findById(memberId).orElseThrow(() -> new NotFoundException("Member not found: " + memberId));
     return fineRepo.findByMember(m);
 }

 public Fine pay(FinePayRequest r) {
     Fine f = get(r.getFineId());
     if (f.isPaid()) throw new BadRequestException("Fine already paid");
     f.setPaid(true);
     log.info("Paid fine id={}", f.getId());
     return fineRepo.save(f);
 }

 public Fine waiveForMember(Long memberId) {
     List<Fine> fines = byMember(memberId);
     if (fines.isEmpty()) throw new BadRequestException("No fines to waive for member: " + memberId);
     Fine last = null;
     for (Fine f : fines) {
         if (!f.isPaid()) {
             f.setPaid(true);
             last = fineRepo.save(f);
         }
     }
     if (last == null) throw new BadRequestException("All fines already paid for member: " + memberId);
     log.warn("Waived unpaid fines for member={}", memberId);
     return last;
 }
}

