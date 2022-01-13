package com.icia.board.entity;

import com.icia.board.dto.BoardDetailDTO;
import com.icia.board.dto.BoardSaveDTO;
import com.icia.board.dto.BoardUpdateDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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
