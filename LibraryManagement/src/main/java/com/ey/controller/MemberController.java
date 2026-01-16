package com.ey.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ey.dto.request.MemberCreateRequest;
import com.ey.dto.request.MemberUpdateRequest;
import com.ey.dto.response.MemberResponse;
import com.ey.mapper.MemberMapper;
import com.ey.service.MemberService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/members")
public class MemberController {
 private final MemberService service;

 public MemberController(MemberService service) { this.service = service; }

 @PostMapping
 public MemberResponse create(@Valid @RequestBody MemberCreateRequest r) {
     return MemberMapper.toResponse(service.create(r));
 }

 @GetMapping
 public List<MemberResponse> list() { return service.list().stream().map(MemberMapper::toResponse).toList(); }

 @GetMapping("/{id}")
 public MemberResponse get(@PathVariable Long id) { return MemberMapper.toResponse(service.get(id)); }

 @PutMapping("/{id}")
 public MemberResponse update(@PathVariable Long id, @Valid @RequestBody MemberUpdateRequest r) {
     return MemberMapper.toResponse(service.update(id, r));
 }

 @DeleteMapping("/{id}")
 public void delete(@PathVariable Long id) { service.delete(id); }

 @GetMapping("/search")
 public List<MemberResponse> search(@RequestParam String q) {
     return service.search(q).stream().map(MemberMapper::toResponse).toList();
 }

 @PostMapping("/{id}/suspend")
 public MemberResponse suspend(@PathVariable Long id) { return MemberMapper.toResponse(service.suspend(id)); }

 @PostMapping("/{id}/unsuspend")
 public MemberResponse unsuspend(@PathVariable Long id) { return MemberMapper.toResponse(service.unsuspend(id)); }

 @GetMapping("/{id}/loans")
 public List<Object> loans(@PathVariable Long id) { return List.of(
         java.util.Map.of("hint", "Use /api/v1/loans/member/" + id)); }

 @GetMapping("/{id}/reservations")
 public List<Object> reservations(@PathVariable Long id) { return List.of(
         java.util.Map.of("hint", "Use /api/v1/reservations/member/" + id)); }
}

