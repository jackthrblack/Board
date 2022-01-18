package com.icia.board.dto;


import com.icia.board.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDetailDTO {

    private Long memberId;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    public static MemberDetailDTO toMemberDetailDTO(MemberEntity m) {
        MemberDetailDTO memberDetailDTO = new MemberDetailDTO();
        memberDetailDTO.setMemberId(m.getId());
        memberDetailDTO.setMemberEmail(m.getMemberEmail());
        memberDetailDTO.setMemberPassword(m.getMemberPassword());
        memberDetailDTO.setMemberName(m.getMemberName());
        return memberDetailDTO;
    }
}
