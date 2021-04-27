package com.bizns.bizneyland.domain.loan;

import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.domain.client.ClientRepository;
import com.bizns.bizneyland.domain.company.Company;
import com.bizns.bizneyland.domain.company.CompanyRepository;
import com.bizns.bizneyland.domain.member.Member;
import com.bizns.bizneyland.domain.member.MemberRepository;
import com.bizns.bizneyland.domain.tm.Tm;
import com.bizns.bizneyland.domain.tm.TmRepository;
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
public class LoanRepositoryTest {

    @Autowired LoanRepository repository;
    @Autowired TmRepository tmRepository;
    @Autowired ClientRepository clientRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired CompanyRepository companyRepository;

    @Before
    public void before() {
        Company company = companyRepository.save(Company.builder()
                .businessNo("12-345-789")
                .name("Member Company")
                .build());

        Member member = memberRepository.save(Member.builder()
                .userSeq(1L)
                .name("Call Tester")
                .company(company)
                .build());

        Client client = clientRepository.save(Client.builder()
                .companyName("Client Company")
                .contact("02-123-1234")
                .build());

        tmRepository.save(Tm.builder()
                .client(client)
                .caller(member)
                .build());
    }

    @Test
    public void 대출정보_등록() {
        //given
        String creditor = "국민은행";
        int amount = 3000;

        Loan loan = repository.save(Loan.builder()
                .tm(tmRepository.findById(1L).get())
                .creditor(creditor)
                .amount(amount)
                .build());
        //when
        Loan findLoan = repository.findById(loan.getLoanSeq()).get();

        //then
        assertThat(findLoan.getCreditor()).isEqualTo(creditor);
        assertThat(findLoan.getAmount()).isEqualTo(amount);
    }

    @Test
    public void TM번호로_조회() {
        //given
        Tm tm = tmRepository.findById(1L).get();
        Loan loan = repository.save(Loan.builder()
                .tm(tm)
                .creditor("농협")
                .amount(300)
                .build());

        //when
        List<Loan> loanList = repository.findByTm(tm);

        //then
        assertThat(loanList.size()).isEqualTo(1);
        assertThat(loanList.get(0)).isEqualTo(loan);
    }
}