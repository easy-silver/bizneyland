package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.client.Client;
import com.bizns.bizneyland.domain.client.ClientRepository;
import com.bizns.bizneyland.domain.member.Member;
import com.bizns.bizneyland.domain.member.MemberRepository;
import com.bizns.bizneyland.domain.tm.Tm;
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
    private final ClientRepository clientRepository;
    private final MemberRepository memberRepository;

    /**
     * TM 상담 등록
     * @param requestDto
     * @return
     */
    @Transactional
    public Long save(TmRequestDto requestDto) {

        // 고객(Client) 조회
        Client client = clientRepository.findById(requestDto.getClientSeq())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다. id=" + requestDto.getClientSeq()));

        // 상담사(Member) 조회
        Member member = memberRepository.findByUserSeq(requestDto.getUserSeq())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상담사입니다. id=" + requestDto.getUserSeq()));

        Tm tm = requestDto.toEntity().updateClient(client).updateCaller(member);

        // TM 상담 내역 등록
        return repository.save(tm).getTmSeq();
    }

    /**
     * 전체 상담건 조회
     */
    public List<TmResponseDto> findAllDesc() {
        return repository.findAllDesc()
                .stream()
                .map(TmResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 고객번호로 상담건 조회
     * @param clientSeq
     * @return tmList
     */
    public List<TmResponseDto> findByClient(Long clientSeq) {
        return repository.findByClient(clientSeq)
                .stream()
                .map(TmResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 상담번호로 한 건 조회
     */
    public TmResponseDto findById(Long tmSeq) {
        Tm entity = repository.findById(tmSeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 상담 건이 없습니다. tmSeq=" + tmSeq));
        return new TmResponseDto(entity);
    }
}
