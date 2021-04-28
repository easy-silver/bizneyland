package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.company.Company;
import com.bizns.bizneyland.domain.company.CompanyRepository;
import com.bizns.bizneyland.domain.user.Role;
import com.bizns.bizneyland.domain.user.User;
import com.bizns.bizneyland.domain.user.UserRepository;
import com.bizns.bizneyland.web.dto.MemberCreateRequestDto;
import com.bizns.bizneyland.web.dto.MemberResponseDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {

    @Autowired MemberService service;
    @Autowired CompanyRepository companyRepository;
    @Autowired UserRepository userRepository;

    @Autowired EntityManager em;

    @Before
    public void before() {
        userRepository.save(User.builder()
                .name("test user")
                .email("test@gmail.com")
                .role(Role.GUEST)
                .build());
    }

    private Company createCompany() {
        return companyRepository.save(Company.builder()
                .businessNo("123-45-67890")
                .name("테스트 컴퍼니")
                .build());
    }

    @Test
    public void 회원_등록() {
        //given
        Long companySeq = createCompany().getId();
        String name = "이티모";
        String nickname = "티모";

        //when
        Long memberSeq = service.save(MemberCreateRequestDto.builder()
                .companySeq(companySeq)
                .userSeq(1L)
                .name(name)
                .nickname(nickname)
                .build());

        em.flush();
        em.clear();

        MemberResponseDto findMember = service.findById(memberSeq);

        //then
        assertThat(findMember.getName()).isEqualTo(name);
        assertThat(findMember.getNickname()).isEqualTo(nickname);

        // USER.MEMBER_SEQ 업데이트 여부 확인
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자"));
        assertThat(user.getMember().getId()).isEqualTo(findMember.getId());
    }

}