package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.tm.TmRepository;
import com.bizns.bizneyland.web.dto.TmRequestDto;
import com.bizns.bizneyland.web.dto.TmResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TmService {
    private final TmRepository repository;

    @Transactional
    public Long save(TmRequestDto requestDto) {
        return repository.save(requestDto.toEntity()).getTmSeq();
    }

    public List<TmResponseDto> findAllDesc() {
        return repository.findAllDesc()
                .stream()
                .map(TmResponseDto::new)
                .collect(Collectors.toList());
    }
}
