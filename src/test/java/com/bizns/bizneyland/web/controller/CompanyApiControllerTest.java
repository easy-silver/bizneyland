package com.bizns.bizneyland.web.controller;

import com.bizns.bizneyland.domain.company.Company;
import com.bizns.bizneyland.domain.company.CompanyRepository;
import com.bizns.bizneyland.web.dto.CompanyRequestDto;
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

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompanyApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CompanyRepository repository;

    @After
    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    @Test
    public void Company_등록된다() throws Exception {
        //given
        String name = "애플";

        CompanyRequestDto requestDto = CompanyRequestDto.builder()
                .name(name)
                .build();

        String url = "http://localhost:" + port + "/company";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Company> all = repository.findAll();
        assertThat(all.get(0).getName()).isEqualTo(name);
    }

    @Test
    public void Company_수정된다() throws Exception {
        //given
        Company savedCompany = repository.save(Company.builder()
                .name("애플")
                .address("캘리포니아")
                .build());

        Long updateId = savedCompany.getId();
        String expectedAddress = "CaliCalifornia";

        CompanyRequestDto requestDto = CompanyRequestDto.builder()
                .address(expectedAddress)
                .build();

        String url = "http://localhost:" + port + "/company/" + updateId;
        HttpEntity<CompanyRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Company> all = repository.findAll();
        assertThat(all.get(0).getAddress()).isEqualTo(expectedAddress);
    }
}