package com.ey.mapper;

import com.ey.domain.entity.Loan;
import com.ey.dto.response.LoanResponse;

public class LoanMapper {
 public static LoanResponse toResponse(Loan l) {
     LoanResponse lr = new LoanResponse();
     lr.setId(l.getId());
     lr.setMemberId(l.getMember().getId());
     lr.setMemberName(l.getMember().getFullName());
     lr.setBookId(l.getBook().getId());
     lr.setBookTitle(l.getBook().getTitle());
     lr.setIssueDate(l.getIssueDate());
     lr.setDueDate(l.getDueDate());
     lr.setReturnDate(l.getReturnDate());
     lr.setStatus(l.getStatus().name());
     return lr;
 }
}

