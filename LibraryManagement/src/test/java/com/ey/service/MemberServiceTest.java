package com.ey.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ey.domain.entity.Member;
import com.ey.domain.enums.MemberStatus;
import com.ey.dto.request.MemberCreateRequest;
import com.ey.exception.BadRequestException;
import com.ey.repository.MemberRepository;

class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void init() { MockitoAnnotations.openMocks(this); }

    @Test
    void createMember_success() {
        MemberCreateRequest req = new MemberCreateRequest();
        req.setFullName("Alice");
        req.setEmail("alice@example.com");

        when(memberRepository.findByEmailIgnoreCase("alice@example.com"))
                .thenReturn(Optional.empty());

        when(memberRepository.save(any())).thenAnswer(i -> {
            Member m = i.getArgument(0);
            m.setId(1L);
            return m;
        });

        Member m = memberService.create(req);

        assertEquals(1L, m.getId());
        assertEquals(MemberStatus.ACTIVE, m.getStatus());
    }

    @Test
    void createMember_duplicateEmail_throwsException() {
        MemberCreateRequest req = new MemberCreateRequest();
        req.setEmail("x@example.com");

        when(memberRepository.findByEmailIgnoreCase("x@example.com"))
                .thenReturn(Optional.of(new Member()));

        assertThrows(BadRequestException.class, () -> memberService.create(req));
    }

    @Test
    void suspendMember_success() {
        Member m = new Member();
        m.setId(5L);
        m.setStatus(MemberStatus.ACTIVE);

        when(memberRepository.findById(5L))
                .thenReturn(Optional.of(m));

        when(memberRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Member result = memberService.suspend(5L);

        assertEquals(MemberStatus.SUSPENDED, result.getStatus());
    }
}

