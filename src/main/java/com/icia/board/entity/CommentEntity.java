package com.icia.board.entity;

import com.icia.board.dto.CommentSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="comment_table")
public class CommentEntity extends BaseEntity {

    // 댓글번호, 작성자, 내용, 원글

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long id;

    // 원글의 게시글 번호를 참조하기 위한 설정 -
    // 게시글 입장일때 1개의 글에 여러개의 댓글이 있으니 1:n 이지만
    // 댓글의 입장에서는 n:1이다
    // many(comment,n) one(board,1)
    @ManyToOne(fetch = FetchType.LAZY) // 1:n / n:1 등등의 어노테이션션
    @JoinColumn(name="board_id") // 어떤 컬럼에대한 참조인가 -> 부모테이블(참조하고자하는 테이블)의 pk 컬럼이름
    private BoardEntity boardEntity; // 참조하고자하는 테이블을 관리하는 엔티티

    @Column
    private String commentWriter;

    @Column
    private String commentContents;

    public static CommentEntity toSaveEntity(CommentSaveDTO commentSaveDTO, BoardEntity boardEntity){
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentSaveDTO.getCommentWriter());
        commentEntity.setCommentContents(commentSaveDTO.getCommentContents());
        commentEntity.setBoardEntity(boardEntity);
        return commentEntity;
    }

}
