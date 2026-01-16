package com.ey.domain.entity;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

import com.ey.domain.enums.ReservationStatus;

@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity {
 @ManyToOne(optional = false, fetch = FetchType.LAZY)
 private Member member;

 @ManyToOne(optional = false, fetch = FetchType.LAZY)
 private Book book;

 @Column(nullable = false)
 private LocalDate reservationDate;

 @Enumerated(EnumType.STRING)
 @Column(nullable = false, length = 20)
 private ReservationStatus status = ReservationStatus.ACTIVE;

 public Member getMember() { return member; }
 public Book getBook() { return book; }
 public LocalDate getReservationDate() { return reservationDate; }
 public ReservationStatus getStatus() { return status; }
 public void setMember(Member member) { this.member = member; }
 public void setBook(Book book) { this.book = book; }
 public void setReservationDate(LocalDate reservationDate) { this.reservationDate = reservationDate; }
 public void setStatus(ReservationStatus status) { this.status = status; }

 @Override public boolean equals(Object o) {
     if (this == o) return true;
     if (!(o instanceof Reservation r)) return false;
     return Objects.equals(getId(), r.getId());
 }
 @Override public int hashCode() { return Objects.hash(getId()); }
}

