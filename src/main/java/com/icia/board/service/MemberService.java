package com.icia.board.service;

import com.icia.board.dto.MemberDetailDTO;
import com.icia.board.dto.MemberSaveDTO;

public interface MemberService {
    Long save(MemberSaveDTO memberSaveDTO);

    MemberDetailDTO findById(Long memberId);
}
