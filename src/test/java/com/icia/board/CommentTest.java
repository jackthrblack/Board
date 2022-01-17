package com.icia.board;

import com.icia.board.dto.BoardSaveDTO;
import com.icia.board.dto.CommentDetailDTO;
import com.icia.board.dto.CommentSaveDTO;
import com.icia.board.entity.CommentEntity;
import com.icia.board.repository.BoardRepository;
import com.icia.board.repository.CommentRepository;
import com.icia.board.service.BoardService;
import com.icia.board.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
public class CommentTest {

    @Autowired
    private BoardService bs;
    @Autowired
    private BoardRepository br;
    @Autowired
    private CommentService cs;
    @Autowired
    private CommentRepository cr;

    @Test
    @Transactional
    @DisplayName("댓글작성테스트")
    public void CommentSaveTest(){
        //게시글존재
        BoardSaveDTO boardSaveDTO = new BoardSaveDTO("bw1","bp1","bt1","bc1");
        Long boardId = bs.save(boardSaveDTO);
        System.out.println("bbbb:"+boardId);

        //댓글
        CommentSaveDTO commentSaveDTO = new CommentSaveDTO(boardId,"cr1","cc1");
        cs.save(commentSaveDTO);
        System.out.println("b2222222:"+commentSaveDTO);
    }


    @Test
    @Transactional
    @DisplayName("댓글조회")
    public void findByIdTest(){
        CommentEntity commentEntity = cr.findById(1L).get();
        System.out.println("commentEntity.toString() = " + commentEntity.toString());
        System.out.println("commentEntity.getId() = " + commentEntity.getId());
        System.out.println("commentEntity.getCommentWriter() = " + commentEntity.getCommentWriter());
        System.out.println("commentEntity.getCommentContents() = " + commentEntity.getCommentContents());
        System.out.println("commentEntity.getBoardEntity() = " + commentEntity.getBoardEntity());
        System.out.println("commentEntity.getBoardEntity() = " + commentEntity.getBoardEntity().getBoardTitle());
    }

    @Test
    @Transactional
    @DisplayName("댓글목록출력")
    public void findAllTest(){
        List<CommentDetailDTO> commentDetailDTOS = cs.findAll(1L);
        for(CommentDetailDTO c:commentDetailDTOS){
            System.out.println("c.toStirng()="+c.toString());
        }
    }
}
