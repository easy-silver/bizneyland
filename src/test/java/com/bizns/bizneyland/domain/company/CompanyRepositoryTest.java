package com.bizns.bizneyland.domain.company;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyRepositoryTest {

    @Autowired CompanyRepository repository;

    @Test
    public void 회사저장_불러오기() {
        //given
        String name = "애플";

        repository.save(Company.builder()
                .businessNo("123-45-67890")
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
                .businessNo("123-45-67890")
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

    @Test(expected = IllegalArgumentException.class)
    public void 회사_삭제() {
        //given
        Company company = repository.save(Company.builder()
                .name("테스트")
                .businessNo("123-456-789")
                .build());
        //when
        repository.deleteById(company.getId());
        repository.findById(company.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회사가 존재하지 않습니다."));
    }

}