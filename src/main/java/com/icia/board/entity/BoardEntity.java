package com.icia.board.entity;

import com.icia.board.dto.BoardDetailDTO;
import com.icia.board.dto.BoardSaveDTO;
import com.icia.board.dto.BoardUpdateDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(name = "boardWriter")
    private String boardWriter;

    @Column(name = "boardPassword")
    private String boardPassword;

    @Column(name = "boardTitle")
    private String boardTitle;

    @Column(name = "boardContents")
    private String boardContents;

    // 댓글 연관관계
    // mapperBy => 연관관계를 맺었을때 누가 주체인가를 지정하는 것.
    //여기서 주체는 자식이고 boardEntity는 자식에있는 것
    //fetch => 만약 댓글 데이터를 가져오면 여기 안에는 보드엔티티 내용도 같이 오는데
    //여기서 둘다 같이 가져오는건 => eager라는게 있고 일단 댓글만 가져오고 나중에
    // 필요할때 가져오는건 LAZY라는 옵션을 쓴다.
    @OneToMany(mappedBy = "boardEntity", cascade =CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) // 게시글 하나에 댓글이 여러개가 붙음.
    private List<CommentEntity> commentEntityList = new ArrayList<>();

/*    @Column(name = "boardDate")
    private LocalDateTime boardDate;*/

    public static BoardEntity saveBoard(BoardSaveDTO boardSaveDTO) {

        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardSaveDTO.getBoardWriter());
        boardEntity.setBoardPassword(boardSaveDTO.getBoardPassword());
        boardEntity.setBoardTitle(boardSaveDTO.getBoardTitle());
        boardEntity.setBoardContents(boardSaveDTO.getBoardContents());
        /*boardEntity.setBoardDate(LocalDateTime.now());*/
        return boardEntity;
    }

   public static BoardEntity toUpdateBoard(BoardDetailDTO boardDetailDTO) {

        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setId(boardDetailDTO.getBoardId());

        boardEntity.setBoardWriter(boardDetailDTO.getBoardWriter());
        boardEntity.setBoardTitle(boardDetailDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDetailDTO.getBoardContents());
        /*boardEntity.setBoardDate(LocalDateTime.now());*/

        return boardEntity;
    }

    public static BoardEntity toUpdateBoard(BoardUpdateDTO boardUpdateDTO) {

        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setId(boardUpdateDTO.getBoardId());

        boardEntity.setBoardWriter(boardUpdateDTO.getBoardWriter());
        boardEntity.setBoardPassword(boardUpdateDTO.getBoardPassword());
        boardEntity.setBoardTitle(boardUpdateDTO.getBoardTitle());
        boardEntity.setBoardContents(boardUpdateDTO.getBoardContents());
        /*boardEntity.setBoardDate(LocalDateTime.now());*/

        return boardEntity;
    }
}
