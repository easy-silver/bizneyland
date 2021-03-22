package com.bizns.springboot.service;

import com.bizns.springboot.domain.company.Company;
import com.bizns.springboot.domain.company.CompanyRepository;
import com.bizns.springboot.web.dto.CompanyRequestDto;
import com.bizns.springboot.web.dto.CompanyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
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
}
