package com.ey.mapper;

import com.ey.domain.entity.Fine;
import com.ey.dto.response.FineResponse;

public class FineMapper {
 public static FineResponse toResponse(Fine f) {
     FineResponse fr = new FineResponse();
     fr.setId(f.getId());
     fr.setMemberId(f.getMember().getId());
     fr.setMemberName(f.getMember().getFullName());
     fr.setLoanId(f.getLoan() != null ? f.getLoan().getId() : null);
     fr.setAmount(f.getAmount());
     fr.setPaid(f.isPaid());
     return fr;
 }
}

