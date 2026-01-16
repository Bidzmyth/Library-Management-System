package com.ey.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ey.domain.entity.Book;
import com.ey.domain.entity.Member;
import com.ey.service.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
 private final ReportService service;

 public ReportController(ReportService service) { this.service = service; }

 @GetMapping("/stats")
 public Map<String, Object> stats() { return service.stats(); }

 @GetMapping("/top-books")
 public List<Book> topBooks() { return service.topBooks(); }

 @GetMapping("/active-members")
 public List<Member> activeMembers() { return service.activeMembers(); }
}

