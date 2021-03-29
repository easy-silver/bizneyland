package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.domain.client.ClientRepository;
import com.bizns.bizneyland.web.dto.ClientRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final ClientRepository repository;

    @Transactional
    public Client save(ClientRequestDto requestDto) {
        return repository.save(requestDto.toEntity());
    }

}
