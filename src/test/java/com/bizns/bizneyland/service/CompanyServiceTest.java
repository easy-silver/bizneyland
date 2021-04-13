package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.company.Company;
import com.bizns.bizneyland.domain.company.CompanyRepository;
import com.bizns.bizneyland.web.dto.CompanyResponseDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyServiceTest {

    @Autowired CompanyService service;
    @Autowired CompanyRepository repository;

    @Test
    public void 아이디로_조회() {
        //given
        Company company = repository.save(Company.builder()
                .businessNo("123-456-789")
                .name("테스트 회사")
                .build());

        //when
        CompanyResponseDto responseDto = service.findById(company.getId());

        //then
        assertThat(responseDto.getName()).isEqualTo(company.getName());
    }

}