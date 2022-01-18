package com.icia.board.service;

import com.icia.board.dto.MemberDetailDTO;
import com.icia.board.dto.MemberSaveDTO;
import com.icia.board.entity.MemberEntity;
import com.icia.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository mr;

    @Override
    public Long save(MemberSaveDTO memberSaveDTO) {

        MemberEntity memberEntity = MemberEntity.toSaveEntity(memberSaveDTO);
        return mr.save(memberEntity).getId();
    }

    @Override
    public MemberDetailDTO findById(Long memberId) {
        Optional<MemberEntity> memberEntityOptional = mr.findById(memberId);
        MemberEntity memberEntity = memberEntityOptional.get();
        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(memberEntity);

        return memberDetailDTO;
    }


}
