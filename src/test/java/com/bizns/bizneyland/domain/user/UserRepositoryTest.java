package com.bizns.bizneyland.domain.user;

import com.bizns.bizneyland.domain.company.Company;
import com.bizns.bizneyland.domain.company.CompanyRepository;
import com.bizns.bizneyland.domain.member.Member;
import com.bizns.bizneyland.domain.member.MemberRepository;
import com.bizns.bizneyland.service.MemberService;
import com.bizns.bizneyland.web.dto.MemberCreateRequestDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired UserRepository repository;
    @Autowired MemberService memberService;
    @Autowired CompanyRepository companyRepository;
    @Autowired MemberRepository memberRepository;

    @Autowired EntityManager em;

    @Before
    public void before() {
        repository.save(User.builder()
                .name("111")
                .role(Role.USER)
                .email("timo@gmail.com")
                .build());
    }

    @Test
    public void 사용자_전체_조회() {
        //when
        List<User> users = repository.findAll();

        //then
        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    public void 회원정보_업데이트() {

        //given
        User user = repository.findAll().get(0);
        String name = "이티모";

        Member member = memberRepository.save(Member.builder()
                .userSeq(user.getUserSeq())
                .name(name)
                .company(createCompany())
                .build());

        //when
        user.setMember(member);

        em.flush();
        em.clear();

        User findUser = repository.findById(user.getUserSeq())
                .orElseThrow(() -> new IllegalArgumentException("없는 사용자"));

        System.out.println("Member.getName() = " + findUser.getMember().getName());
        System.out.println("Member.getId() = " + findUser.getMember().getId());
        System.out.println("Member.getCompany() = " + findUser.getMember().getCompany());

        //then
        assertThat(findUser.getMember().getName()).isEqualTo(name);
    }

    private Long createMember(User user, Company company) {
        return memberService.save(MemberCreateRequestDto.builder()
                .companySeq(company.getId())
                .userSeq(user.getUserSeq())
                .name("이티모")
                .build());
    }

    private Company createCompany() {
        return companyRepository.save(Company.builder()
                .businessNo("123-45-67890")
                .name("테스트 컴퍼니")
                .build());
    }

    @Test
    public void 회원정보_함께_조회() {
        //given
        User tester = repository.save(User.builder()
                .name("TESTER")
                .role(Role.USER)
                .email("tester@gmail.com")
                .build());

        Long memberSeq = createMember(tester, createCompany());


        //when
        User findUser = repository.findOneWithMember(tester.getUserSeq());

        System.out.println("Member = " + findUser.getMember());
        System.out.println("Member.id = " + findUser.getMember());

        //then
        assertThat(findUser.getMember().getId()).isEqualTo(memberSeq);
    }
}