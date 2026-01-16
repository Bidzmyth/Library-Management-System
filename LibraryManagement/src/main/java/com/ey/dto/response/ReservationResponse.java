package com.ey.dto.response;


import java.time.LocalDate;

public class ReservationResponse {
 private Long id;
 private Long memberId;
 private String memberName;
 private Long bookId;
 private String bookTitle;
 private LocalDate reservationDate;
 private String status;

 public Long getId() { return id; }
 public Long getMemberId() { return memberId; }
 public String getMemberName() { return memberName; }
 public Long getBookId() { return bookId; }
 public String getBookTitle() { return bookTitle; }
 public LocalDate getReservationDate() { return reservationDate; }
 public String getStatus() { return status; }
 public void setId(Long id) { this.id = id; }
 public void setMemberId(Long memberId) { this.memberId = memberId; }
 public void setMemberName(String memberName) { this.memberName = memberName; }
 public void setBookId(Long bookId) { this.bookId = bookId; }
 public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
 public void setReservationDate(LocalDate reservationDate) { this.reservationDate = reservationDate; }
 public void setStatus(String status) { this.status = status; }
}

