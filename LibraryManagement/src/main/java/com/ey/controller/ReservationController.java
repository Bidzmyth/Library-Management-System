package com.ey.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ey.dto.request.ReservationCreateRequest;
import com.ey.dto.response.ReservationResponse;
import com.ey.mapper.ReservationMapper;
import com.ey.service.ReservationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
 private final ReservationService service;

 public ReservationController(ReservationService service) { this.service = service; }

 @PostMapping
 public ReservationResponse create(@Valid @RequestBody ReservationCreateRequest r) {
     return ReservationMapper.toResponse(service.create(r));
 }

 @PostMapping("/{id}/cancel")
 public ReservationResponse cancel(@PathVariable Long id) {
     return ReservationMapper.toResponse(service.cancel(id));
 }

 @GetMapping
 public List<ReservationResponse> list() { return service.list().stream().map(ReservationMapper::toResponse).toList(); }

 @GetMapping("/{id}")
 public ReservationResponse get(@PathVariable Long id) { return ReservationMapper.toResponse(service.get(id)); }

 @GetMapping("/member/{memberId}")
 public List<ReservationResponse> byMember(@PathVariable Long memberId) {
     return service.byMember(memberId).stream().map(ReservationMapper::toResponse).toList();
 }

 @GetMapping("/book/{bookId}")
 public List<ReservationResponse> byBook(@PathVariable Long bookId) {
     return service.byBook(bookId).stream().map(ReservationMapper::toResponse).toList();
 }
}

