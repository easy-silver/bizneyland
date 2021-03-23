package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.domain.member.Member;
import com.bizns.bizneyland.domain.member.MemberRepository;
import com.bizns.bizneyland.web.dto.MemberSaveRequestDto;
import com.bizns.bizneyland.web.dto.MemberUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberRepository repository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Member_등록된다() throws Exception {
        //given
        String name = "테스터";
        String nickname = "티모";

        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .companyId(1L)
                .name(name)
                .nickname(nickname)
                .build();

        String url = "http://localhost:" + port + "/member";

        //when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Member> all = repository.findAll();
        assertThat(all.get(0).getName()).isEqualTo(name);
        assertThat(all.get(0).getNickname()).isEqualTo(nickname);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Member_수정된다() throws Exception {
        //given
        Member savedMember = repository.save(Member.builder()
                .companyId(1L)
                .name("이지은")
                .nickname("티모")
                .build());

        Long updateId = savedMember.getId();
        String expectedNickname = "모티";

        MemberUpdateRequestDto requestDto = MemberUpdateRequestDto.builder()
                .nickname(expectedNickname)
                .build();

        String url = "http://localhost:" + port + "/member/" + updateId;

        //when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Member> all = repository.findAll();
        assertThat(all.get(0).getNickname()).isEqualTo(expectedNickname);
    }

}