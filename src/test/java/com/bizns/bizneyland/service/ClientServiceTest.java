package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.domain.client.ClientRepository;
import com.bizns.bizneyland.web.dto.ClientCreateRequestDto;
import com.bizns.bizneyland.web.dto.ClientResponseDto;
import com.bizns.bizneyland.web.dto.ClientUpdateRequestDto;
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
    @Autowired ClientRepository repository;

    private Client createClient() {
        return repository.save(Client.builder()
                .companyName("테스트 클라이언트")
                .contact("010-1234-1234")
                .build());
    }

    @Test
    public void 고객_등록() {
        Client client = createClient();

        assertThat(client.getCompanyName()).isEqualTo("테스트 클라이언트");
        assertThat(client.getContact()).isEqualTo("01012341234");
    }


    @Test
    public void 고객번호로_조회() {
        //given
        Long clientSeq = createClient().getClientSeq();

        //when
        ClientResponseDto findClient = service.findById(clientSeq);

        //then
        assertThat(findClient.getContact()).isEqualTo("010-1234-1234");
    }

    @Test
    public void 고객_수정() {
        //given
        Client client = createClient();
        Long seq = service.findById(client.getClientSeq()).getClientSeq();

        //when
        String modifiedAddress = "변경된 주소";
        ClientUpdateRequestDto dto = new ClientUpdateRequestDto();
        dto.setAddress(modifiedAddress);
        service.update(seq, dto);

        //then
        assertThat(service.findById(seq).getAddress()).isEqualTo(modifiedAddress);
    }

    @Test
    public void 고객_등록_매출_포함() {
        service.save(ClientCreateRequestDto.builder()
                .companyName("테스트 클라이언트")
                .contact("010-1234-1234")
                .salesYears(new String[] {"2012"})
                .build());
    }
}