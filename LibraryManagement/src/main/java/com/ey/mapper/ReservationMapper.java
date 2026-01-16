package com.ey.mapper;

import com.ey.domain.entity.Reservation;
import com.ey.dto.response.ReservationResponse;

public class ReservationMapper {
 public static ReservationResponse toResponse(Reservation r) {
     ReservationResponse rr = new ReservationResponse();
     rr.setId(r.getId());
     rr.setMemberId(r.getMember().getId());
     rr.setMemberName(r.getMember().getFullName());
     rr.setBookId(r.getBook().getId());
     rr.setBookTitle(r.getBook().getTitle());
     rr.setReservationDate(r.getReservationDate());
     rr.setStatus(r.getStatus().name());
     return rr;
 }
}

