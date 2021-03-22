package com.bizns.springboot.web.controller;

import com.bizns.springboot.domain.member.Member;
import com.bizns.springboot.domain.member.MemberRepository;
import com.bizns.springboot.web.dto.MemberSaveRequestDto;
import com.bizns.springboot.web.dto.MemberUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MemberRepository memberRepository;

    @After
    public void tearDown() throws Exception {
        memberRepository.deleteAll();
    }

    @Test
    public void Member_등록된다() throws Exception {
        //given
        String name = "테스터";
        String nickname = "티모";

        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .name(name)
                .nickname(nickname)
                .build();

        String url = "http://localhost:" + port + "/member";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Member> all = memberRepository.findAll();
        assertThat(all.get(0).getName()).isEqualTo(name);
        assertThat(all.get(0).getNickname()).isEqualTo(nickname);
    }

    @Test
    public void Member_수정된다() throws Exception {
        //given
        Member savedMember = memberRepository.save(Member.builder()
                .name("이지은")
                .nickname("티모")
                .build());

        Long updateId = savedMember.getId();
        String expectedNickname = "모티";

        MemberUpdateRequestDto requestDto = MemberUpdateRequestDto.builder()
                .nickname(expectedNickname)
                .build();

        String url = "http://localhost:" + port + "/member/" + updateId;

        HttpEntity<MemberUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Member> all = memberRepository.findAll();
        assertThat(all.get(0).getNickname()).isEqualTo(expectedNickname);
    }

}