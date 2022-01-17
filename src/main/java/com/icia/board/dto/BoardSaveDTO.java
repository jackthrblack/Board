package com.icia.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardSaveDTO {

    @NotBlank(message = "작성!")
    private String boardWriter;
    private String boardPassword;
    private String boardTitle;
    private String boardContents;

    public BoardSaveDTO(String boardWriter, String boardPassword, String boardTitle, String boardContents) {
        this.boardWriter = boardWriter;
        this.boardPassword = boardPassword;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
    }
}



