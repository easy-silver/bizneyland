package com.bizns.springboot.domain.company;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyRepositoryTest {

    @Autowired
    CompanyRepository repository;

    @After
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void 회사저장_불러오기() {
        //given
        String name = "애플";

        repository.save(Company.builder()
                .name(name)
                .build());

        //when
        List<Company> companies = repository.findAll();

        //then
        Company company = companies.get(0);
        Assertions.assertThat(company.getName()).isEqualTo(name);
    }

}