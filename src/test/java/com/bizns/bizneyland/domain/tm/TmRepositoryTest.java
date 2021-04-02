package com.bizns.bizneyland.domain.tm;

import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.domain.client.ClientRepository;
import com.bizns.bizneyland.domain.company.Company;
import com.bizns.bizneyland.domain.company.CompanyRepository;
import com.bizns.bizneyland.domain.member.Member;
import com.bizns.bizneyland.domain.member.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class TmRepositoryTest {

    @Autowired TmRepository repository;
    @Autowired ClientRepository clientRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired CompanyRepository companyRepository;

    private Client registerClient() {
        return clientRepository.save(Client.builder()
                .companyName("Client Company")
                .build());
    }

    private Member registerCaller() {
        return memberRepository.save(Member.builder()
                .name("Call Tester")
                .company(registerCompany())
                .build());
    }

    private Company registerCompany() {
        return companyRepository.save(Company.builder()
                .businessNo("12-345-789")
                .name("Member Company")
                .build());
    }

    @Test
    public void TM정보가_등록된다() {
        //when
        Tm tm = repository.save(Tm.builder()
                .caller(registerCaller())
                .client(registerClient())
                .callDate(LocalDateTime.now())
                .build());
        //then
        assertThat(tm.getClient().getCompanyName()).isEqualTo("Client Company");
        assertThat(tm.getCaller().getName()).isEqualTo("Call Tester");

    }
}