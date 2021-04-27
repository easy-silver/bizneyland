package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.domain.client.ClientRepository;
import com.bizns.bizneyland.domain.company.Company;
import com.bizns.bizneyland.domain.company.CompanyRepository;
import com.bizns.bizneyland.domain.member.Member;
import com.bizns.bizneyland.domain.member.MemberRepository;
import com.bizns.bizneyland.domain.tm.Tm;
import com.bizns.bizneyland.web.dto.TmRequestDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class TmServiceTest {

    @Autowired TmService service;
    @Autowired ClientRepository clientRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired CompanyRepository companyRepository;

    @Before
    public void saveClient() {
        clientRepository.save(Client.builder()
                .companyName("테스트 클라이언트")
                .contact("010-1234-1234")
                .build());
    }

    @Before
    public void saveMember() {
        memberRepository.save(Member.builder()
                .userSeq(1L)
                .company(companyRepository.save(Company.builder()
                        .businessNo("12-345-789")
                        .name("Member Company")
                        .build()))
                .name("Call Tester")
                .build());
    }

    @Test
    public void TM_등록() {
        service.save(TmRequestDto.builder()
                .clientSeq(1L)
                .userSeq(1L)
                .build());

    }

}