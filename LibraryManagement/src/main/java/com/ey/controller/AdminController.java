package com.ey.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
 private static final Logger log = LoggerFactory.getLogger(AdminController.class);

 @GetMapping("/health")
 public Map<String, Object> health() {
     log.debug("Health check");
     return Map.of("status", "UP", "time", OffsetDateTime.now().toString());
 }

 @GetMapping("/time")
 public Map<String, Object> time() {
     return Map.of("now", OffsetDateTime.now().toString());
 }
}

