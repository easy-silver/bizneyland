package com.bizns.bizneyland.domain.member;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository repository;

    @After
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void 회원저장_불러오기() {
        //given
        String name = "테스터";

        repository.save(Member.builder()
                .name(name)
                .build());

        //when
        List<Member> members = repository.findAll();

        //then
        Member member = members.get(0);
        assertThat(member.getName()).isEqualTo(name);
    }

    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2021, 3, 22, 0,0,0);
        repository.save(Member.builder()
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