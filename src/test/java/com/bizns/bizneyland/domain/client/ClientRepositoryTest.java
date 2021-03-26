package com.bizns.bizneyland.domain.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientRepositoryTest {

    @Autowired
    ClientRepository repository;

    @Before
    public void create() {
        repository.save(Client.builder()
                .companyName("Timo Company")
                .establishDate(LocalDate.of(2020, 8, 27))
                .build());
    }

    @After
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void 고객업체_등록() {
        Client company = Client.builder()
                .companyName("TargetCompany")
                .establishDate(LocalDate.of(2020, 1, 1))
                .build();

        Client savedClient = repository.save(company);

        assertThat(savedClient.getClientSeq()).isEqualTo(company.getClientSeq());
    }

    @Test
    public void 고객업체_전체조회() {
        //when
        List<Client> clients = repository.findAllDesc();

        //then
        assertThat(clients.size()).isEqualTo(1);
        assertThat(clients.get(0).getCompanyName()).isEqualTo("Timo Company");
    }

    @Test
    public void 고객업체_ID조회() {
        Client client = repository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException());

        assertThat(client.getCompanyName()).isEqualTo("Timo Company");
    }

}