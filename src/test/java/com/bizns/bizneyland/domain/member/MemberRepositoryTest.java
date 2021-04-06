package com.bizns.bizneyland.domain.member;

import com.bizns.bizneyland.domain.company.Company;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository repository;

    @PersistenceContext
    EntityManager em;

    public Company createCompany() {
        Company company = Company.builder()
                .businessNo("123-45-67890")
                .name("메디치")
                .build();

        em.persist(company);

        return company;
    }

    @Test
    public void 회원저장_불러오기() {
        //given
        String name = "테스터";

        Company company = createCompany();

        repository.save(Member.builder()
                .userSeq(1L)
                .company(company)
                .name(name)
                .build());

        //when
        List<Member> members = repository.findAll();

        //then
        Member member = members.get(0);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getCompany().getId()).isEqualTo(company.getId());
    }

    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2021, 3, 22, 0,0,0);
        repository.save(Member.builder()
                .userSeq(1L)
                .company(createCompany())
                .name("이지은")
                .nickname("티모")
                .build());
        //when
        List<Member> members = repository.findAll();

        //then
        Member member = members.get(0);
        System.out.println(">>>>>>>>> createDate=" + member.getCreatedDate()
                + ", modifiedDate=" + member.getModifiedDate());

        assertThat(member.getCreatedDate()).isAfter(now);
        assertThat(member.getModifiedDate()).isAfter(now);
    }
}