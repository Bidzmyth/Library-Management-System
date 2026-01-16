package com.ey.dto.request;


import jakarta.validation.constraints.NotNull;

public class LoanIssueRequest {
 @NotNull
 private Long memberId;
 @NotNull
 private Long bookId;

 public Long getMemberId() { return memberId; }
 public Long getBookId() { return bookId; }
 public void setMemberId(Long memberId) { this.memberId = memberId; }
 public void setBookId(Long bookId) { this.bookId = bookId; }
}

