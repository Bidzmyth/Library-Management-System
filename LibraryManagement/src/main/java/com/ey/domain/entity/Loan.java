package com.ey.domain.entity;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

import com.ey.domain.enums.LoanStatus;

@Entity
@Table(name = "loans")
public class Loan extends BaseEntity {
 @ManyToOne(optional = false, fetch = FetchType.LAZY)
 private Member member;

 @ManyToOne(optional = false, fetch = FetchType.LAZY)
 private Book book;

 @Column(nullable = false)
 private LocalDate issueDate;

 @Column(nullable = false)
 private LocalDate dueDate;

 private LocalDate returnDate;

 @Enumerated(EnumType.STRING)
 @Column(nullable = false, length = 20)
 private LoanStatus status = LoanStatus.ISSUED;

 public Member getMember() { return member; }
 public Book getBook() { return book; }
 public LocalDate getIssueDate() { return issueDate; }
 public LocalDate getDueDate() { return dueDate; }
 public LocalDate getReturnDate() { return returnDate; }
 public LoanStatus getStatus() { return status; }
 public void setMember(Member member) { this.member = member; }
 public void setBook(Book book) { this.book = book; }
 public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
 public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
 public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
 public void setStatus(LoanStatus status) { this.status = status; }

 @Override public boolean equals(Object o) {
     if (this == o) return true;
     if (!(o instanceof Loan l)) return false;
     return Objects.equals(getId(), l.getId());
 }
 @Override public int hashCode() { return Objects.hash(getId()); }
}

