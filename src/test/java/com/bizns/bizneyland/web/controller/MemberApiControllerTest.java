package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.domain.company.Company;
import com.bizns.bizneyland.domain.company.CompanyRepository;
import com.bizns.bizneyland.domain.member.Member;
import com.bizns.bizneyland.domain.member.MemberRepository;
import com.bizns.bizneyland.web.dto.MemberCreateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CompanyRepository companyRepository;
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

    //@Test
    @WithMockUser(roles = "USER")
    public void Member_????????????() throws Exception {
        //given
        String name = "?????????";
        String nickname = "??????";

        Long companyId = createCompany().getId();

        MemberCreateRequestDto requestDto = MemberCreateRequestDto.builder()
                .userSeq(1L)
                .companySeq(companyId)
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
        List<Member> all = memberRepository.findAll();
        assertThat(all.get(0).getName()).isEqualTo(name);
        assertThat(all.get(0).getNickname()).isEqualTo(nickname);
        assertThat(all.get(0).getCompany().getId()).isEqualTo(companyId);
    }

    private Company createCompany() {
        return companyRepository.save(Company.builder()
                .name("??????")
                .businessNo("123-45-67890")
                .build());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Member_????????????() throws Exception {
        //given
        Member savedMember = memberRepository.save(Member.builder()
                .userSeq(1L)
                .company(createCompany())
                .name("?????????")
                .nickname("??????")
                .build());

        Long updateId = savedMember.getId();
        String expectedNickname = "??????";

        MemberCreateRequestDto requestDto = MemberCreateRequestDto.builder()
                .nickname(expectedNickname)
                .build();

        String url = "http://localhost:" + port + "/member/" + updateId;

        //when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Member> all = memberRepository.findAll();
        assertThat(all.get(0).getNickname()).isEqualTo(expectedNickname);
        assertThat(all.get(0).getCompany()).isNotNull();
    }

}