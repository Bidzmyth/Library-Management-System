package com.ey.mapper;

import com.ey.domain.entity.Author;
import com.ey.dto.request.AuthorCreateRequest;
import com.ey.dto.request.AuthorUpdateRequest;
import com.ey.dto.response.AuthorResponse;

public class AuthorMapper {
 public static Author toEntity(AuthorCreateRequest r) {
     Author a = new Author();
     a.setFullName(r.getFullName());
     a.setBio(r.getBio());
     return a;
 }
 public static void update(Author a, AuthorUpdateRequest r) {
     if (r.getFullName() != null) a.setFullName(r.getFullName());
     if (r.getBio() != null) a.setBio(r.getBio());
 }
 public static AuthorResponse toResponse(Author a) {
     AuthorResponse ar = new AuthorResponse();
     ar.setId(a.getId());
     ar.setFullName(a.getFullName());
     ar.setBio(a.getBio());
     return ar;
 }
}

