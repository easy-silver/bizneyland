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
    public void 회사번호로_멤버찾기() {
        //given
        Company company = createCompany();

        repository.save(Member.builder()
                .userSeq(11L)
                .company(company)
                .name("tester1")
                .build());

        repository.save(Member.builder()
                .userSeq(12L)
                .company(company)
                .name("tester2")
                .build());

        //when
        List<Member> findMembers = repository.findByCompanySeq(company.getId());

        //then
        assertThat(findMembers.size()).isEqualTo(2);
        assertThat(findMembers.get(0).getName()).isEqualTo("tester1");

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

    @Test
    public void 회사정보_함께_조회() {
        //given
        Company company = createCompany();
        Member member = repository.save(Member.builder()
                .userSeq(9L)
                .name("테스트")
                .company(company)
                .build());

        //when
        Member findMember = repository.findOneWithCompany(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));

        //then
        assertThat(findMember.getCompany()).isEqualTo(company);
        assertThat(findMember.getCompany().getName()).isEqualTo("메디치");
    }
}