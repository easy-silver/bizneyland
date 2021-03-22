package com.bizns.springboot.service;

import com.bizns.springboot.domain.member.Member;
import com.bizns.springboot.domain.member.MemberRepository;
import com.bizns.springboot.web.dto.MemberResponseDto;
import com.bizns.springboot.web.dto.MemberSaveRequestDto;
import com.bizns.springboot.web.dto.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long save(MemberSaveRequestDto requestDto) {
        return memberRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, MemberUpdateRequestDto requestDto) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + id));
        member.update(requestDto.getName(),
                requestDto.getNickname(),
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
}
