package com.bizns.springboot.domain.company;

import com.bizns.springboot.domain.member.Member;
import org.assertj.core.api.Assertions;
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

    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2021, 3, 22, 0,0,0);
        repository.save(Company.builder()
                .name("애플")
                .build());
        //when
        List<Company> companies = repository.findAll();

        //then
        Company company = companies.get(0);
        System.out.println(">>>>>>>>> createDate=" + company.getCreatedDate()
                + ", modifiedDate=" + company.getModifiedDate());

        assertThat(company.getCreatedDate()).isAfter(now);
        assertThat(company.getModifiedDate()).isAfter(now);
    }

}