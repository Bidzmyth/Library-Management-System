package com.ey.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ey.domain.entity.Book;
import com.ey.domain.entity.Member;
import com.ey.domain.entity.Reservation;
import com.ey.domain.enums.ReservationStatus;
import com.ey.dto.request.ReservationCreateRequest;
import com.ey.exception.BadRequestException;
import com.ey.exception.NotFoundException;
import com.ey.repository.BookRepository;
import com.ey.repository.MemberRepository;
import com.ey.repository.ReservationRepository;

@Service
public class ReservationService {
 private static final Logger log = LoggerFactory.getLogger(ReservationService.class);
 private final ReservationRepository reservationRepo;
 private final MemberRepository memberRepo;
 private final BookRepository bookRepo;

 public ReservationService(ReservationRepository reservationRepo, MemberRepository memberRepo, BookRepository bookRepo) {
     this.reservationRepo = reservationRepo; this.memberRepo = memberRepo; this.bookRepo = bookRepo;
 }

 public Reservation create(ReservationCreateRequest r) {
     Member m = memberRepo.findById(r.getMemberId())
             .orElseThrow(() -> new NotFoundException("Member not found: " + r.getMemberId()));
     Book b = bookRepo.findById(r.getBookId())
             .orElseThrow(() -> new NotFoundException("Book not found: " + r.getBookId()));
     if (b.getAvailableCopies() > 0) throw new BadRequestException("Book available; no need to reserve");
     Reservation res = new Reservation();
     res.setMember(m);
     res.setBook(b);
     res.setReservationDate(LocalDate.now());
     res.setStatus(ReservationStatus.ACTIVE);
     log.info("Reservation created for book {} by member {}", b.getId(), m.getId());
     return reservationRepo.save(res);
 }

 public Reservation get(Long id) {
     return reservationRepo.findById(id).orElseThrow(() -> new NotFoundException("Reservation not found: " + id));
 }

 public List<Reservation> list() { return reservationRepo.findAll(); }

 public List<Reservation> byMember(Long memberId) {
     Member m = memberRepo.findById(memberId).orElseThrow(() -> new NotFoundException("Member not found: " + memberId));
     return reservationRepo.findByMember(m);
 }

 public List<Reservation> byBook(Long bookId) {
     Book b = bookRepo.findById(bookId).orElseThrow(() -> new NotFoundException("Book not found: " + bookId));
     return reservationRepo.findByBook(b);
 }

 public Reservation cancel(Long id) {
     Reservation r = get(id);
     r.setStatus(ReservationStatus.CANCELLED);
     log.warn("Cancelled reservation id={}", id);
     return reservationRepo.save(r);
 }
}

