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
import java.util.List;

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
                .name("Client Company")
                .contact("02-123-1234")
                .build());
    }

    private Member registerCaller() {
        return memberRepository.save(Member.builder()
                .userSeq(1L)
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
    public void 고객번호로_TM목록_조회() {
        //given
        Tm tm = repository.save(Tm.builder()
                .caller(registerCaller())
                .client(registerClient())
                .callDate(LocalDateTime.now())
                .build());

        Long clientSeq = tm.getClient().getClientSeq();

        //when
        List<Tm> tms = repository.findByClient(clientSeq);

        //then
        assertThat(tms.size()).isEqualTo(1);
        assertThat(tms.get(0).getClient().getName()).isEqualTo("Client Company");
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
        assertThat(tm.getClient().getName()).isEqualTo("Client Company");
        assertThat(tm.getCaller().getName()).isEqualTo("Call Tester");

    }
}