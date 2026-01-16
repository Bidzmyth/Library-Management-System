package com.ey.dto.request;


import jakarta.validation.constraints.NotNull;

public class FinePayRequest {
 @NotNull
 private Long fineId;
 public Long getFineId() { return fineId; }
 public void setFineId(Long fineId) { this.fineId = fineId; }
}

