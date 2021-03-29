package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.owner.OwnerRepository;
import com.bizns.bizneyland.web.dto.OwnerRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OwnerService {
    private final OwnerRepository repository;

    @Transactional
    public Long save(OwnerRequestDto requestDto) {
        return repository.save(requestDto.toEntity()).getOwnerSeq();
    }

}
