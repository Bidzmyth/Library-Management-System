package com.ey.domain.entity;




import jakarta.persistence.*;
import java.util.Objects;

import com.ey.domain.enums.MemberStatus;

@Entity
@Table(name = "members")
public class Member extends BaseEntity {
 @Column(nullable = false, length = 120)
 private String fullName;

 @Column(nullable = false, unique = true, length = 160)
 private String email;

 @Enumerated(EnumType.STRING)
 @Column(nullable = false, length = 20)
 private MemberStatus status = MemberStatus.ACTIVE;

 public String getFullName() { return fullName; }
 public String getEmail() { return email; }
 public MemberStatus getStatus() { return status; }
 public void setFullName(String fullName) { this.fullName = fullName; }
 public void setEmail(String email) { this.email = email; }
 public void setStatus(MemberStatus status) { this.status = status; }

 @Override public boolean equals(Object o) {
     if (this == o) return true;
     if (!(o instanceof Member m)) return false;
     return Objects.equals(getId(), m.getId());
 }
 @Override public int hashCode() { return Objects.hash(getId()); }
}

