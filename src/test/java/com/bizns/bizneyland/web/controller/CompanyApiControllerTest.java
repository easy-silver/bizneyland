package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.domain.company.Company;
import com.bizns.bizneyland.domain.company.CompanyRepository;
import com.bizns.bizneyland.web.dto.CompanyRequestDto;
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
public class CompanyApiControllerTest {

    @LocalServerPort private int port;
    @Autowired private CompanyRepository repository;
    @Autowired private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Company_등록된다() throws Exception {
        //given
        String name = "애플";

        CompanyRequestDto requestDto = CompanyRequestDto.builder()
                .businessNo("123-45-67890")
                .name(name)
                .build();

        String url = "http://localhost:" + port + "/company";

        //when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Company> all = repository.findAll();
        assertThat(all.get(0).getName()).isEqualTo(name);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Company_수정된다() throws Exception {
        //given
        Company savedCompany = repository.save(Company.builder()
                .businessNo("123-45-67890")
                .name("애플")
                .address("캘리포니아")
                .build());

        Long updateId = savedCompany.getId();
        String expectedAddress = "CaliCalifornia";

        CompanyRequestDto requestDto = CompanyRequestDto.builder()
                .address(expectedAddress)
                .build();

        String url = "http://localhost:" + port + "/company/" + updateId;

        //when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Company> all = repository.findAll();
        assertThat(all.get(0).getAddress()).isEqualTo(expectedAddress);
    }
}