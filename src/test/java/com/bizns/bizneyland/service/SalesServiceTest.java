package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.domain.client.ClientRepository;
import com.bizns.bizneyland.domain.sales.Sales;
import com.bizns.bizneyland.domain.sales.SalesRepository;
import com.bizns.bizneyland.web.dto.SalesRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class SalesServiceTest {

    @Autowired SalesService service;
    @Autowired SalesRepository repository;
    @Autowired ClientRepository clientRepository;

    private Client createClient() {
        return clientRepository.save(Client.builder()
                .companyName("테스트")
                .contact("032-123-1234")
                .build());
    }


    @Test
    public void 매출정보_등록() {
        //given
        Client client = createClient();

        //when
        service.register(SalesRequestDto.builder()
                .clientSeq(client.getClientSeq())
                .salesYear("2020")
                .salesAmount("1000")
                .build());

        //then
        List<Sales> salesList = repository.findByClient(client);
        assertThat(salesList.size()).isEqualTo(1);
        assertThat(salesList.get(0).getSalesYear()).isEqualTo("2020");
    }
}