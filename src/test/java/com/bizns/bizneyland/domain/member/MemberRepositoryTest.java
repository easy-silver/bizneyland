package com.bizns.bizneyland.domain.member;

import com.bizns.bizneyland.domain.company.Company;
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
                .name("test company")
                .build();

        em.persist(company);

        return company;
    }

    private Member createMember(Long userSeq, String name, Company company) {
        return repository.save(Member.builder()
                .userSeq(userSeq)
                .name(name)
                .company(company)
                .build());
    }

    @Test
    public void 회원저장_불러오기() {
        //given
        String name = "테스터";
        Company company = createCompany();
        createMember(999L, name, company);

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

        createMember(11L, "tester1", company);
        createMember(12L, "tester2", company);

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
        createMember(999L, "이지은", createCompany());

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
        Member member = createMember(999L, "테스터", company);

        //when
        Member findMember = repository.findOneWithCompany(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));

        //then
        assertThat(findMember.getCompany()).isEqualTo(company);
        assertThat(findMember.getCompany().getName()).isEqualTo("test company");
    }

    @Test(expected = IllegalArgumentException.class)
    public void 회원_삭제() {
        //given
        Member member = createMember(999L, "테스터", createCompany());
        Long id = member.getId();

        //when
        repository.deleteById(id);

        //then
        Member findMember = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));
    }

    @Test
    public void 회원_수정() {
        //given
        Member member = createMember(999L, "테스터", createCompany());
        Member findMember = repository.findById(member.getId()).get();

        //when
        String modifiedName = "new Tester";
        findMember.update(modifiedName, findMember.getBirth(), findMember.getGender(),
                findMember.getMobile(), findMember.getGrade(), findMember.getWorkingArea(),
                findMember.getEmail(), findMember.getFax(), findMember.getProfileFileSeq());

        //then
        Member modifiedMember = repository.findById(member.getId()).get();
        assertThat(modifiedMember.getNickname()).isEqualTo(modifiedName);
    }
}