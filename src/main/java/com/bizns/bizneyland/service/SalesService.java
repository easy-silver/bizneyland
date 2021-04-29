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
     * 매출 정보 복수건 등록
     * @param salesList 리스트
     */
    @Transactional
    public void register(List<Sales> salesList) {
        for (Sales sales : salesList) {
            repository.save(sales);
        }
    }

    /**
     * 매출 정보 단건 등록
     * @param requestDto
     */
    @Transactional
    public void register(SalesRequestDto requestDto) {
        Client client = clientRepository.findById(requestDto.getClientSeq())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다. id=" + requestDto.getClientSeq()));

        repository.save(Sales.builder()
                .client(client)
                .salesYear(requestDto.getSalesYear())
                .amount(requestDto.getSalesAmount())
                .build());

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

    /**
     * 해당 고객의 매출 정보 삭제
     * @param clientSeq
     */
    public void deleteByClient(Long clientSeq) {
        Client client = clientRepository.findById(clientSeq)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다. id=" + clientSeq));

        repository.deleteByClient(client);
    }

}
