package com.ey.service;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.ey.domain.entity.Book;
import com.ey.domain.entity.Loan;
import com.ey.domain.entity.Member;
import com.ey.domain.enums.LoanStatus;
import com.ey.domain.enums.MemberStatus;
import com.ey.dto.request.LoanIssueRequest;
import com.ey.dto.request.LoanRenewRequest;
import com.ey.exception.BadRequestException;
import com.ey.repository.BookRepository;
import com.ey.repository.LoanRepository;
import com.ey.repository.MemberRepository;

class LoanServiceTest {

    @Mock LoanRepository loanRepository;
    @Mock MemberRepository memberRepository;
    @Mock BookRepository bookRepository;

    @InjectMocks LoanService loanService;

    Member member;
    Book book;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(loanService, "defaultLoanDays", 14);

        member = new Member();
        member.setId(1L);
        member.setStatus(MemberStatus.ACTIVE);

        book = new Book();
        book.setId(2L);
        book.setAvailableCopies(3);
    }

    @Test
    void issueLoan_success() {
        LoanIssueRequest req = new LoanIssueRequest();
        req.setMemberId(1L);
        req.setBookId(2L);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(bookRepository.findById(2L)).thenReturn(Optional.of(book));
        when(loanRepository.save(any())).thenAnswer(i -> {
            Loan l = i.getArgument(0);
            l.setId(10L);
            return l;
        });

        Loan loan = loanService.issue(req);

        assertEquals(LoanStatus.ISSUED, loan.getStatus());
        assertEquals(2, loan.getBook().getAvailableCopies());
        assertEquals(LocalDate.now().plusDays(14), loan.getDueDate());
    }

    @Test
    void issueLoan_whenMemberSuspended_throwsException() {
        member.setStatus(MemberStatus.SUSPENDED);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(bookRepository.findById(2L)).thenReturn(Optional.of(book));

        assertThrows(BadRequestException.class,
                () -> loanService.issue(new LoanIssueRequest() {{
                    setMemberId(1L);
                    setBookId(2L);
                }}));
    }

    @Test
    void renewLoan_success() {
        Loan loan = new Loan();
        loan.setId(10L);
        loan.setStatus(LoanStatus.ISSUED);
        loan.setDueDate(LocalDate.now().plusDays(3));

        when(loanRepository.findById(10L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Loan renewed = loanService.renew(10L, new LoanRenewRequest() {{ setAdditionalDays(5); }});

        assertEquals(LocalDate.now().plusDays(8), renewed.getDueDate());
    }
}

