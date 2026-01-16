package com.ey.mapper;

import com.ey.domain.entity.Member;
import com.ey.dto.request.MemberCreateRequest;
import com.ey.dto.request.MemberUpdateRequest;
import com.ey.dto.response.MemberResponse;

public class MemberMapper {
 public static Member toEntity(MemberCreateRequest r) {
     Member m = new Member();
     m.setFullName(r.getFullName());
     m.setEmail(r.getEmail());
     return m;
 }
 public static void update(Member m, MemberUpdateRequest r) {
     if (r.getFullName() != null) m.setFullName(r.getFullName());
     if (r.getEmail() != null) m.setEmail(r.getEmail());
 }
 public static MemberResponse toResponse(Member m) {
     MemberResponse mr = new MemberResponse();
     mr.setId(m.getId());
     mr.setFullName(m.getFullName());
     mr.setEmail(m.getEmail());
     mr.setStatus(m.getStatus().name());
     return mr;
 }
}


