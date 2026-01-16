package com.ey.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ey.domain.entity.Book;
import com.ey.domain.entity.Member;
import com.ey.domain.entity.Reservation;
import com.ey.domain.enums.ReservationStatus;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
 List<Reservation> findByMember(Member member);
 List<Reservation> findByBook(Book book);
 List<Reservation> findByStatus(ReservationStatus status);
}
