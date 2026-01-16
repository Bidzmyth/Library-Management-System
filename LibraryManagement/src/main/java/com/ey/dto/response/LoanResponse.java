package com.ey.dto.response;


import java.time.LocalDate;

public class LoanResponse {
 private Long id;
 private Long memberId;
 private String memberName;
 private Long bookId;
 private String bookTitle;
 private LocalDate issueDate;
 private LocalDate dueDate;
 private LocalDate returnDate;
 private String status;

 public Long getId() { return id; }
 public Long getMemberId() { return memberId; }
 public String getMemberName() { return memberName; }
 public Long getBookId() { return bookId; }
 public String getBookTitle() { return bookTitle; }
 public LocalDate getIssueDate() { return issueDate; }
 public LocalDate getDueDate() { return dueDate; }
 public LocalDate getReturnDate() { return returnDate; }
 public String getStatus() { return status; }
 public void setId(Long id) { this.id = id; }
 public void setMemberId(Long memberId) { this.memberId = memberId; }
 public void setMemberName(String memberName) { this.memberName = memberName; }
 public void setBookId(Long bookId) { this.bookId = bookId; }
 public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
 public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
 public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
 public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
 public void setStatus(String status) { this.status = status; }
}


