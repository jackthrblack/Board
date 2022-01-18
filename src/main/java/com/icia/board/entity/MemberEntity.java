package com.icia.board.entity;

import com.icia.board.dto.MemberSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="member_table")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private String memberName;

    // on delete cascade를 할 때 -> 이 방법을 더 추천!
   /* @OneToMany(mappedBy = "memberEntity", cascade =CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardEntity> boardEntityList = new ArrayList<>();*/

    /*@OneToMany(mappedBy = "memberEntity", cascade =CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
   private List<CommentEntity> commentEntityList = new ArrayList<>()*/

    // on delete set null을 할 때
   @OneToMany(mappedBy = "memberEntity", cascade =CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
   private List<BoardEntity> boardEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade =CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();


    public static MemberEntity toSaveEntity(MemberSaveDTO memberSaveDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberSaveDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberSaveDTO.getMemberPassword());
        memberEntity.setMemberName(memberSaveDTO.getMemberName());
        return memberEntity;
}

    @PreRemove
    private void preRemove(){
        System.out.println("MemberEntity.preRemove");
        boardEntityList.forEach(board -> board.setMemberEntity(null));
        /*for (BoardEntity board: boardEntityList){
            board.setMemberEntity(null);
        }*/
        commentEntityList.forEach(comment -> comment.setMemberEntity(null));
    }
}