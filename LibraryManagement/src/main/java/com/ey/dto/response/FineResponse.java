package com.ey.dto.response;


import java.math.BigDecimal;

public class FineResponse {
 private Long id;
 private Long memberId;
 private String memberName;
 private Long loanId;
 private BigDecimal amount;
 private boolean paid;

 public Long getId() { return id; }
 public Long getMemberId() { return memberId; }
 public String getMemberName() { return memberName; }
 public Long getLoanId() { return loanId; }
 public BigDecimal getAmount() { return amount; }
 public boolean isPaid() { return paid; }
 public void setId(Long id) { this.id = id; }
 public void setMemberId(Long memberId) { this.memberId = memberId; }
 public void setMemberName(String memberName) { this.memberName = memberName; }
 public void setLoanId(Long loanId) { this.loanId = loanId; }
 public void setAmount(BigDecimal amount) { this.amount = amount; }
 public void setPaid(boolean paid) { this.paid = paid; }
}

