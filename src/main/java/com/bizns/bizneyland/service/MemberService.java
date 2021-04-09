package com.bizns.bizneyland.service;

import com.bizns.bizneyland.domain.member.Member;
import com.bizns.bizneyland.domain.member.MemberRepository;
import com.bizns.bizneyland.web.dto.MemberRequestDto;
import com.bizns.bizneyland.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long save(MemberRequestDto requestDto) {
        return memberRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, MemberRequestDto requestDto) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + id));
        member.update(requestDto.getNickname(),
                requestDto.getBirth(),
                requestDto.getGender(),
                requestDto.getMobile(),
                requestDto.getGrade(),
                requestDto.getWorkingArea(),
                requestDto.getEmail(),
                requestDto.getFax(),
                requestDto.getProfileFileSeq());

        return id;
    }

    public MemberResponseDto findById(Long id) {
        Member entity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + id));

        return new MemberResponseDto(entity);
    }

    public MemberResponseDto findByUserSeq(Long userSeq) {
        Member entity = memberRepository.findByUserSeq(userSeq);

        return entity != null ? new MemberResponseDto(entity) : null;
    }

    public List<MemberResponseDto> findAllDesc() {
        return memberRepository.findAllDesc()
                .stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }
}
