package com.bizns.bizneyland.domain.owner;

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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class OwnerRepositoryTest {

    @Autowired OwnerRepository repository;
    @Autowired CompanyRepository companyRepository;
    @Autowired MemberRepository memberRepository;

    private Company createCompany() {
        return companyRepository.save(Company.builder()
                .name("테스트 회사")
                .businessNo("123-45-67890")
                .build());
    }

    private Member createMember(Company company) {
        return memberRepository.save(Member.builder()
                .userSeq(999L)
                .name("테스트 멤버")
                .company(company)
                .build());
    }

    @Test
    public void 대표자_저장() {
        //given
        Company company = createCompany();
        Owner owner = Owner.builder()
                        .company(company)
                        .member(createMember(company))
                        .name("테스트 대표")
                        .build();

        //when
        repository.save(owner);
        List<Owner> owners = repository.findAll();

        //then
        assertThat(owners.size()).isEqualTo(1);
        assertThat(owners.get(0).getName()).isEqualTo("테스트 대표");
    }
}