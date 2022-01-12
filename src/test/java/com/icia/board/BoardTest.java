package com.icia.board;

import com.icia.board.dto.BoardSaveDTO;
import com.icia.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardTest {

    @Autowired
    private BoardService bs;

    @Test
    @DisplayName("글작성 30개")
    public void newBoard(){
        // IntStream 이용하여 새글 30개 DB에 저장하기
        IntStream.rangeClosed(1,30).forEach(i-> {
            bs.save(new BoardSaveDTO("글작성자"+i,"글비밀번호"+i,
                    "글제목"+i,"글내용"+i, LocalDateTime.now()));
        });

    }
}
