package com.bizns.bizneyland.domain.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientRepositoryTest {

    @Autowired
    ClientRepository repository;

    public Client create() {
        return Client.builder()
                .companyName("Timo Company")
                .contact("032-815-4553")
                .establishDate(LocalDate.parse("2020-04-14", DateTimeFormatter.ISO_DATE))
                .build();
    }

    @Test
    public void 고객업체_등록() {
        Client client = create();

        Client savedClient = repository.save(client);

        assertThat(savedClient.getClientSeq()).isEqualTo(client.getClientSeq());
    }

    @Test
    public void 고객업체_전체조회() {
        //given
        Client client = repository.save(create());

        //when
        List<Client> clients = repository.findAllDesc();

        //then
        assertThat(clients.size()).isEqualTo(1);
        assertThat(clients.get(0).getCompanyName()).isEqualTo(client.getCompanyName());
    }

    @Test
    public void 고객업체_ID조회() {
        //given
        Client client = repository.save(create());

        Client findClient = repository.findById(client.getClientSeq())
                .orElseThrow(() -> new IllegalArgumentException("해당 업체가 없습니다. id=" + client.getClientSeq()));

        assertThat(findClient.getCompanyName()).isEqualTo(client.getCompanyName());
    }

    @Test
    public void 회사명으로_고객조회() {
        //given
        Client client = repository.save(create());

        //when
        Client findClient = repository.findByCompanyName(client.getCompanyName());

        //then
        assertThat(findClient).isEqualTo(client);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 고객_삭제() {
        //given
        Client client = repository.save(create());

        //when
        repository.deleteById(client.getClientSeq());

        repository.findById(client.getClientSeq())
                .orElseThrow(() -> new IllegalArgumentException("해당 고객이 존재하지 않습니다."));

        //then
    }

}