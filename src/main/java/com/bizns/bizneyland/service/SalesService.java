package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.domain.client.ClientRepository;
import com.bizns.bizneyland.domain.sales.Sales;
import com.bizns.bizneyland.domain.sales.SalesRepository;
import com.bizns.bizneyland.web.dto.SalesRequestDto;
import com.bizns.bizneyland.web.dto.SalesResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SalesService {
    private final SalesRepository repository;
    private final ClientRepository clientRepository;

    /**
     * 매출 정보 등록
     * @param requestDto
     */
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

    /**
     * 업체의 매출 정보
     * @param clientSeq
     * @return 매출 리스트
     */
    public List<SalesResponseDto> findAllByClient(Long clientSeq) {
        Client client = clientRepository.findById(clientSeq)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다. id=" + clientSeq));

        return repository.findByClient(client)
                .stream()
                .map(SalesResponseDto::new)
                .collect(Collectors.toList());
    }

}
