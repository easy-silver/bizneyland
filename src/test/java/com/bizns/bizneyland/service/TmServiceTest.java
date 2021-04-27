package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.domain.client.ClientRepository;
import com.bizns.bizneyland.domain.company.Company;
import com.bizns.bizneyland.domain.company.CompanyRepository;
import com.bizns.bizneyland.domain.loan.Loan;
import com.bizns.bizneyland.domain.loan.LoanRepository;
import com.bizns.bizneyland.domain.member.Member;
import com.bizns.bizneyland.domain.member.MemberRepository;
import com.bizns.bizneyland.domain.tm.TmRepository;
import com.bizns.bizneyland.web.dto.LoanRequestDto;
import com.bizns.bizneyland.web.dto.TmCreateRequestDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class TmServiceTest {

    @Autowired TmService service;
    @Autowired TmRepository repository;
    @Autowired ClientRepository clientRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired CompanyRepository companyRepository;
    @Autowired LoanRepository loanRepository;

    @Before
    public void before() {
        Company company = companyRepository.save(Company.builder()
                .businessNo("12-345-789")
                .name("Member Company")
                .build());

        memberRepository.save(Member.builder()
                .userSeq(1L)
                .name("Call Tester")
                .company(company)
                .build());

        clientRepository.save(Client.builder()
                .companyName("Client Company")
                .contact("02-123-1234")
                .build());
    }

    @Test
    public void TM_등록() {
        service.save(TmCreateRequestDto.builder()
                .clientSeq(1L)
                .userSeq(1L)
                .build());
    }

    @Test
    public void 대출현황_포함_등록() {
        //given
        Long tmSeq = service.save(TmCreateRequestDto.builder()
                .clientSeq(1L)
                .userSeq(1L)
                .loan(LoanRequestDto.builder()
                        .creditors(new String[]{"국민은행"})
                        .amounts(new int[]{1000})
                        //FIXME: 필수값 아닌 것들 어떻게 저장할지 고민하기
                        //.purposes(new String[]{""})
                        //.memos(new String[]{""})
                        .build())
                .build());

        //when
        List<Loan> loanList = loanRepository.findByTm(repository.findById(tmSeq).get());

        //then
        assertThat(loanList.size()).isEqualTo(1);
    }

}