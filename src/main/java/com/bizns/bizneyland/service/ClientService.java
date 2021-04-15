package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.domain.client.ClientRepository;
import com.bizns.bizneyland.util.FormatUtil;
import com.bizns.bizneyland.web.dto.ClientRequestDto;
import com.bizns.bizneyland.web.dto.ClientResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ClientService {

    private final ClientRepository repository;

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
     * 고객 등록
     * */
    @Transactional
    public Client save(ClientRequestDto requestDto) {
        // 연락처 하이픈 제거
        requestDto.setContact(FormatUtil.removeHyphen(requestDto.getContact()));

        return repository.save(requestDto.toEntity());
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
    public Client updateByName(String name, ClientRequestDto dto) {
        Client entity = repository.findByCompanyName(name);
        entity.update(dto);

        return entity;
    }

}
