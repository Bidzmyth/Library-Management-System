package com.ey.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ey.domain.entity.Book;
import com.ey.domain.entity.Loan;
import com.ey.domain.entity.Member;
import com.ey.domain.enums.LoanStatus;

public interface LoanRepository extends JpaRepository<Loan, Long> {
 List<Loan> findByMember(Member member);
 List<Loan> findByBook(Book book);
 List<Loan> findByStatus(LoanStatus status);
 List<Loan> findByDueDateBeforeAndStatus(LocalDate date, LoanStatus status);
}

