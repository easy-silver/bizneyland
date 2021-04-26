package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.domain.client.ClientRepository;
import com.bizns.bizneyland.web.dto.ClientCreateRequestDto;
import com.bizns.bizneyland.web.dto.ClientResponseDto;
import com.bizns.bizneyland.web.dto.ClientUpdateRequestDto;
import com.bizns.bizneyland.web.dto.SalesRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ClientService {

    private final ClientRepository repository;
    private final SalesService salesService;

    /**
     * 고객 등록
     * @param requestDto
     * @return
     */
    @Transactional
    public Long save(ClientCreateRequestDto requestDto) {
        // 업체 등록
        Client savedClient = repository.save(requestDto.toEntity());

        // 매출 정보 만들기
        List<SalesRequestDto> salesList = createSalesList(requestDto.getSalesYears(), requestDto.getSalesAmount());

        // 업체 매출 정보 등록
        salesService.register(salesList, savedClient.getClientSeq());

        return savedClient.getClientSeq();
    }

    private List<SalesRequestDto> createSalesList(String[] salesYears, Integer[] salesAmounts) {
        List<SalesRequestDto> salesList = new ArrayList<>();

        if (salesYears != null && salesAmounts != null) {
            for (int i = 0; i < salesYears.length; i++) {
                if (salesYears[i] == null || salesAmounts[i] == null) {
                    continue;
                }
                salesList.add(SalesRequestDto.builder()
                        .salesYear(salesYears[i])
                        .salesAmount(salesAmounts[i])
                        .build());
            }

        }

        return salesList;
    }

    /**
     * 모든 고객 조회
     * */
    public List<ClientResponseDto> findAll() {
        return repository.findAllDesc()
                .stream()
                .map(ClientResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 기본키로 단일 고객 조회
     * */
    public ClientResponseDto findById(Long clientSeq) {
        Client entity = repository.findById(clientSeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 업체가 없습니다. seq=" + clientSeq));

        return new ClientResponseDto(entity);
    }

    /**
     * 기본키로 고객 삭제
     * @param clientSeq
     */
    @Transactional
    public void deleteById(Long clientSeq) {
        repository.deleteById(clientSeq);
    }

    /**
     * 동일한 고객(회사)명 존재하는지 확인
     * */
    public boolean isExist(String name) {
        Client entity = repository.findByCompanyName(name);
        return entity != null;
    }

    /**
     * 이름으로 조회 후 수정
     */
    public Client updateByName(String name, ClientCreateRequestDto dto) {
        Client entity = repository.findByCompanyName(name);
        //entity.update(dto);

        return entity;
    }

    /**
     * 고객 정보 수정
     * @return 고객번호
     */
    public Long update(Long seq, ClientUpdateRequestDto dto) {
        Client client = repository.findById(seq)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 고객입니다. seq=" + seq));

        client.update(dto);

        return client.getClientSeq();
    }

}
