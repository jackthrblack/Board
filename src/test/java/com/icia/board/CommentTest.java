package com.icia.board;

import com.icia.board.dto.BoardSaveDTO;
import com.icia.board.dto.CommentDetailDTO;
import com.icia.board.dto.CommentSaveDTO;
import com.icia.board.dto.MemberSaveDTO;
import com.icia.board.entity.CommentEntity;
import com.icia.board.repository.BoardRepository;
import com.icia.board.repository.CommentRepository;
import com.icia.board.service.BoardService;
import com.icia.board.service.CommentService;
import com.icia.board.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

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
    @Autowired
    private MemberService ms;

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("댓글작성테스트")
    public void CommentSaveTest(){

        MemberSaveDTO memberSaveDTO = new MemberSaveDTO("작성자5","작성자비번5","작성자제목5");
        Long memberId = ms.save(memberSaveDTO);

        //게시글존재
        BoardSaveDTO boardSaveDTO = new BoardSaveDTO("bw2","bp2","bt2","bc2");
        Long boardId = bs.save(boardSaveDTO);
        System.out.println("bbbb:"+boardId);

        //댓글
        CommentSaveDTO commentSaveDTO = new CommentSaveDTO(boardId,memberId,"cr2","cc2");
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
        List<CommentDetailDTO> commentDetailDTOS = cs.findAll(2L);
        for(CommentDetailDTO c:commentDetailDTOS){
            System.out.println("c.toStirng()="+c.toString());
        }
    }
}
