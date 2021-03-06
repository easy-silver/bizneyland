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
import com.bizns.bizneyland.web.dto.TmResponseDto;
import com.bizns.bizneyland.web.dto.TmUpdateRequestDto;
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
    }

    private Client createClient() {
        return clientRepository.save(Client.builder()
                .name("Client Company")
                .contact("02-123-1234")
                .build());
    }

    @Test
    public void TM_??????() {
        Long clientSeq = createClient().getClientSeq();
        service.save(TmCreateRequestDto.builder()
                .clientSeq(clientSeq)
                .userSeq(1L)
                .build());
    }

    @Test
    public void ????????????_??????_??????() {
        //given
        Long clientSeq = createClient().getClientSeq();

        Long tmSeq = service.save(TmCreateRequestDto.builder()
                .clientSeq(clientSeq)
                .userSeq(1L)
                .loan(LoanRequestDto.builder()
                        .creditors(new String[]{"????????????", "??????"})
                        .amounts(new String[]{"1000", "2000"})
                        .purposes(new String[] {"?????????"})
                        .memos(new String[] {"????????????"})
                        .build())
                .build());

        //when
        List<Loan> loanList = loanRepository.findByTm(repository.findById(tmSeq).get());

        //then
        assertThat(loanList.size()).isEqualTo(2);
    }

    @Test
    public void ????????????_??????() {
        //given
        Long clientSeq = createClient().getClientSeq();
        Long tmSeq = service.save(TmCreateRequestDto.builder()
                .clientSeq(clientSeq)
                .userSeq(1L)
                .memo("?????? ??? ??????")
                .arrearsYn('Y')
                .build());

        //when
        String modifiedMemo = "?????? ??? ??????";

        Long updateTmSeq = service.updateTmInfo(TmUpdateRequestDto.builder()
                .tmSeq(tmSeq)
                .memo(modifiedMemo)
                .arrearsYn('N')
                .build());

        TmResponseDto updateTm = service.findById(updateTmSeq);

        //then
        assertThat(updateTm.getMemo()).isEqualTo(modifiedMemo);
        assertThat(updateTm.getArrearsYn()).isEqualTo('N');
    }

    @Test(expected = IllegalArgumentException.class)
    public void ??????_??????() {
        //given
        Long clientSeq = createClient().getClientSeq();
        Long tmSeq = service.save(TmCreateRequestDto.builder()
                .clientSeq(clientSeq)
                .userSeq(1L)
                .build());

        //when
        service.delete(tmSeq);

        //then
        service.findById(tmSeq);
        assertThat(service.findAllDesc().size()).isEqualTo(0);
    }

}