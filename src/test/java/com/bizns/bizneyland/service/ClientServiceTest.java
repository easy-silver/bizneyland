package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.web.dto.ClientRequestDto;
import com.bizns.bizneyland.web.dto.ClientResponseDto;
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
public class ClientServiceTest {

    @Autowired ClientService service;

    @Test
    public void 고객_등록() {
        Client client = service.save(ClientRequestDto.builder()
                .companyName("테스트 클라이언트")
                .contact("010-1234-1234")
                //.contact("01012341234")
                .build());

        assertThat(client.getCompanyName()).isEqualTo("테스트 클라이언트");
        assertThat(client.getContact()).isEqualTo("01012341234");
    }

    @Test
    public void 고객번호로_조회() {
        //given
        Long clientSeq = service.save(ClientRequestDto.builder()
                .companyName("테스트 클라이언트")
                .contact("010-1234-1234")
                .build()).getClientSeq();

        //when
        ClientResponseDto findClient = service.findById(clientSeq);

        //then
        assertThat(findClient.getContact()).isEqualTo("010-1234-1234");
    }
}