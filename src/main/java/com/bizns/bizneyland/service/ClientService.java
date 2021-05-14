package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.domain.client.ClientRepository;
import com.bizns.bizneyland.domain.sales.Sales;
import com.bizns.bizneyland.web.dto.ClientCreateRequestDto;
import com.bizns.bizneyland.web.dto.ClientResponseDto;
import com.bizns.bizneyland.web.dto.ClientUpdateRequestDto;
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
     * 전체 고객 조회(관리자)
     */
    public List<ClientResponseDto> findAll() {
        return repository.findAllDesc()
                .stream()
                .map(ClientResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 담당회사별 고객 조회(대표자)
     */
    public List<ClientResponseDto> findAll(Long companySeq) {
        return repository.findByCompany(companySeq)
                .stream()
                .map(ClientResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 단건 조회
     * @param clientSeq
     */
    public ClientResponseDto findById(Long clientSeq) {
        Client entity = repository.findById(clientSeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 업체가 없습니다. seq=" + clientSeq));

        return new ClientResponseDto(entity);
    }

    /**
     * 고객 등록
     * @param dto
     * @return
     */
    @Transactional
    public Long save(ClientCreateRequestDto dto) {
        // 업체 등록
        Client savedClient = repository.save(dto.toEntity());
        // 매출 정보 만들기
        List<Sales> salesList = toSalesList(dto.getSalesYears(), dto.getSalesAmount(), savedClient);
        // 업체 매출 정보 등록
        salesService.register(salesList);

        return savedClient.getClientSeq();
    }

    /** Sales(매출) 리스트 생성 */
    private List<Sales> toSalesList(String[] years, String[] amounts, Client client) {
        List<Sales> salesList = new ArrayList<>();

        if (years != null && amounts != null) {
            // 발생연도 만큼 반복
            int length = years.length;

            for (int i = 0; i < length; i++) {

                salesList.add(Sales.builder()
                        .client(client)
                        .salesYear(years[i])
                        .amount(i < amounts.length ? amounts[i] : null)
                        .build());
            }
        }
        return salesList;
    }

    /**
     * 고객 정보 수정
     * @return 고객번호
     */
    @Transactional
    public Long update(Long seq, ClientUpdateRequestDto dto) {
        Client client = repository.findById(seq)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 고객입니다. seq=" + seq));

        // UPDATE
        client.update(dto);

        // SALES DELETE -> INSERT
        List<Sales> salesList = toSalesList(dto.getSalesYears(), dto.getSalesAmount(), client);
        salesService.deleteByClient(client.getClientSeq());
        salesService.register(salesList);

        return client.getClientSeq();
    }

    /**
     * 고객 삭제
     * @param clientSeq
     */
    @Transactional
    public void delete(Long clientSeq) {
        repository.deleteById(clientSeq);
    }

    /**
     * 이미 등록된 회사인지 확인
     * @param contact
     * @param companySeq
     * @return
     */
    public ClientResponseDto findBeforeRegister(String contact, Long companySeq) {
        Client client = repository.findByContactAndCompanyCharge(contact, companySeq);

        return client == null ? null : new ClientResponseDto(client);
    }

    /**
     * 동일한 고객(회사)명 존재하는지 확인
     * */
    public boolean isExist(String name) {
        Client entity = repository.findByName(name);
        return entity != null;
    }

    /**
     * 이름으로 조회 후 수정
     */
    public Client updateByName(String name, ClientCreateRequestDto dto) {
        Client entity = repository.findByName(name);
        //entity.update(dto);

        return entity;
    }

}
