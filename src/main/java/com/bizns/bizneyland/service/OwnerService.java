package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.owner.OwnerRepository;
import com.bizns.bizneyland.web.dto.OwnerRequestDto;
import com.bizns.bizneyland.web.dto.OwnerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OwnerService {
    private final OwnerRepository repository;

    @Transactional
    public Long save(OwnerRequestDto requestDto) {
        return repository.save(requestDto.toEntity()).getOwnerSeq();
    }

    @Transactional
    public List<OwnerResponseDto> findAllByClientSeq(Long clientSeq) {
        return repository.findAllByClientSeq(clientSeq)
                .stream()
                .map(OwnerResponseDto::new)
                .collect(Collectors.toList());
    }

}
