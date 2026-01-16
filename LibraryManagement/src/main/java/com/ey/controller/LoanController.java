package com.ey.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ey.domain.entity.Loan;
import com.ey.dto.request.LoanIssueRequest;
import com.ey.dto.request.LoanRenewRequest;
import com.ey.dto.response.LoanResponse;
import com.ey.mapper.LoanMapper;
import com.ey.service.FineService;
import com.ey.service.LoanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
 private static final Logger log = LoggerFactory.getLogger(LoanController.class);
 private final LoanService service;
 private final FineService fineService;

 public LoanController(LoanService service, FineService fineService) {
     this.service = service; this.fineService = fineService;
 }

 @PostMapping("/issue")
 public LoanResponse issue(@Valid @RequestBody LoanIssueRequest r) {
     Loan l = service.issue(r);
     return LoanMapper.toResponse(l);
 }

 @PostMapping("/{id}/return")
 public LoanResponse returnBook(@PathVariable Long id) {
     Loan l = service.returnBook(id);
     // Generate fine if overdue
     fineService.generateIfOverdue(l);
     return LoanMapper.toResponse(l);
 }

 @PostMapping("/{id}/renew")
 public LoanResponse renew(@PathVariable Long id, @Valid @RequestBody LoanRenewRequest r) {
     return LoanMapper.toResponse(service.renew(id, r));
 }

 @GetMapping
 public List<LoanResponse> list() { return service.list().stream().map(LoanMapper::toResponse).toList(); }

 @GetMapping("/{id}")
 public LoanResponse get(@PathVariable Long id) { return LoanMapper.toResponse(service.get(id)); }

 @GetMapping("/overdue")
 public List<LoanResponse> overdue() { return service.overdue().stream().map(LoanMapper::toResponse).toList(); }

 @GetMapping("/member/{memberId}")
 public List<LoanResponse> byMember(@PathVariable Long memberId) {
     return service.byMember(memberId).stream().map(LoanMapper::toResponse).toList();
 }

 @DeleteMapping("/{id}")
 public void delete(@PathVariable Long id) { service.delete(id); }
}

