package com.ey.domain.entity;



import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "fines")
public class Fine extends BaseEntity {
 @ManyToOne(optional = false, fetch = FetchType.LAZY)
 private Member member;

 @OneToOne(fetch = FetchType.LAZY)
 private Loan loan;

 @Column(nullable = false, precision = 10, scale = 2)
 private BigDecimal amount;

 @Column(nullable = false)
 private boolean paid = false;

 public Member getMember() { return member; }
 public Loan getLoan() { return loan; }
 public BigDecimal getAmount() { return amount; }
 public boolean isPaid() { return paid; }
 public void setMember(Member member) { this.member = member; }
 public void setLoan(Loan loan) { this.loan = loan; }
 public void setAmount(BigDecimal amount) { this.amount = amount; }
 public void setPaid(boolean paid) { this.paid = paid; }

 @Override public boolean equals(Object o) {
     if (this == o) return true;
     if (!(o instanceof Fine f)) return false;
     return Objects.equals(getId(), f.getId());
 }
 @Override public int hashCode() { return Objects.hash(getId()); }
}

