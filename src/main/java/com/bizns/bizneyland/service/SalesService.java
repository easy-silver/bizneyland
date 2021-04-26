package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.domain.client.ClientRepository;
import com.bizns.bizneyland.domain.sales.Sales;
import com.bizns.bizneyland.domain.sales.SalesRepository;
import com.bizns.bizneyland.web.dto.SalesRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SalesService {
    private final SalesRepository repository;
    private final ClientRepository clientRepository;

    public void register(SalesRequestDto requestDto) {
        Client client = clientRepository.findById(requestDto.getClientSeq())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다. id=" + requestDto.getClientSeq()));

        for (int i = 0; i < requestDto.getSalesYears().length; i++) {
            repository.save(Sales.builder()
                    .client(client)
                    .salesYear(requestDto.getSalesYears()[i])
                    .amount(requestDto.getSalesAmount()[i])
                    .build());
        }
    }

}
