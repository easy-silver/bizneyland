package com.bizns.springboot.domain.member;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @After
    public void cleanup() {
        memberRepository.deleteAll();
    }

    @Test
    public void 회원저장_불러오기() {
        //given
        String name = "테스터";

        memberRepository.save(Member.builder()
                .name(name)
                .build());

        //when
        List<Member> members = memberRepository.findAll();

        //then
        Member member = members.get(0);
        assertThat(member.getName()).isEqualTo(name);
    }
}