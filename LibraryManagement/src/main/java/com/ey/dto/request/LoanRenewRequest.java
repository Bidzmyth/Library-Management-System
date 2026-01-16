package com.ey.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

public class LoanRenewRequest {

 @Min(value = 1, message = "Renewal must be at least 1 day.")
 @Max(value = 30, message = "Cannot renew for more than 30 days.")
 private int additionalDays = 7; // default renewal duration

 public int getAdditionalDays() {
     return additionalDays;
 }

 public void setAdditionalDays(int additionalDays) {
     this.additionalDays = additionalDays;
 }
}

