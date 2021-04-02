package com.bizns.bizneyland.domain.owner;

import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.domain.client.ClientRepository;
import org.junit.After;
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
public class OwnerRepositoryTest {

    @Autowired
    OwnerRepository repository;

    @Autowired
    ClientRepository clientRepository;

    private Client createClient() {
        return clientRepository.save(Client.builder()
                .companyName("Test Company")
                .build());
    }

    @After
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void 대표자_등록() {
        //given
        Owner owner = Owner.builder()
                .client(createClient())
                .name("최양인")
                .mobile("010-1234-1234")
                .build();

        //when
        Owner savedOwner = repository.save(owner);

        //then
        assertThat(owner).isEqualTo(savedOwner);
    }
}