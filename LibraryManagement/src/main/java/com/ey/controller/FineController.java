package com.ey.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ey.dto.request.FinePayRequest;
import com.ey.dto.response.FineResponse;
import com.ey.mapper.FineMapper;
import com.ey.service.FineService;

@RestController
@RequestMapping("/api/fines")
public class FineController {
 private final FineService service;

 public FineController(FineService service) { this.service = service; }

 @GetMapping
 public List<FineResponse> list() { return service.list().stream().map(FineMapper::toResponse).toList(); }

 @GetMapping("/{id}")
 public FineResponse get(@PathVariable Long id) { return FineMapper.toResponse(service.get(id)); }

 @GetMapping("/member/{memberId}")
 public List<FineResponse> byMember(@PathVariable Long memberId) {
     return service.byMember(memberId).stream().map(FineMapper::toResponse).toList();
 }

 @PostMapping("/{id}/pay")
 public FineResponse pay(@PathVariable Long id) {
     FinePayRequest r = new FinePayRequest();
     r.setFineId(id);
     return FineMapper.toResponse(service.pay(r));
 }

 @PostMapping("/member/{memberId}/waive")
 public FineResponse waive(@PathVariable Long memberId) {
     return FineMapper.toResponse(service.waiveForMember(memberId));
 }
}

