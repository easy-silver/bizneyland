package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.company.Company;
import com.bizns.bizneyland.domain.company.CompanyRepository;
import com.bizns.bizneyland.web.dto.CompanyRequestDto;
import com.bizns.bizneyland.web.dto.CompanyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CompanyService {

    private final CompanyRepository repository;

    @Transactional
    public Long save(CompanyRequestDto requestDto) {
        return repository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, CompanyRequestDto requestDto) {
        Company company = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회사가 없습니다. id=" + id));
        company.update(requestDto.getAddress(), requestDto.getTel(), requestDto.getLogoFileSeq());

        return id;
    }

    public CompanyResponseDto findById(Long id) {
        Company entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회사가 없습니다. id=" + id));

        return new CompanyResponseDto(entity);
    }

    public List<CompanyResponseDto> findAllDesc() {
        return repository.findAllDesc()
                .stream()
                .map(CompanyResponseDto::new)
                .collect(Collectors.toList());
    }
}
