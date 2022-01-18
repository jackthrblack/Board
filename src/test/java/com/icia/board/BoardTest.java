package com.icia.board;

import com.icia.board.common.PagingConst;
import com.icia.board.dto.BoardPagingDTO;
import com.icia.board.dto.BoardSaveDTO;
import com.icia.board.dto.MemberDetailDTO;
import com.icia.board.dto.MemberSaveDTO;
import com.icia.board.entity.BoardEntity;
import com.icia.board.repository.BoardRepository;
import com.icia.board.service.BoardService;
import com.icia.board.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardTest {

    @Autowired
    private BoardService bs;

    @Autowired
    private BoardRepository br;

    @Autowired
    private MemberService ms;

   @Test
    @DisplayName("글작성 30개")
    public void newBoard(){
        // IntStream 이용하여 새글 30개 DB에 저장하기
       Long memberId =ms.save(new MemberSaveDTO("이메일","비밀번호","이름"));
        IntStream.rangeClosed(1,30).forEach(i-> {
            MemberDetailDTO findMember = ms.findById(memberId);
            bs.save(new BoardSaveDTO(memberId,"작성자"+i, "비밀번호"+i,"주제"+i,"내용"+i));
        });
    }


    @Test
    @DisplayName("삼항연산자")
    public void test1(){

        int num =10;
        int num2 = 0;

        // 간단한 조건식을 쓸 때
        if(num==10){
            num2=5;
        }else{
            num2=100;
        }

        num2=(num==10)? 5: 100;
    }

    @Test
    @Transactional
    @DisplayName("페이징테스트")
    public void pagingTest(){
        int page =3;
        Page<BoardEntity> boardEntities =
                br.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        //Page 객체가 제공해주는 메서드 확인
        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청페이지에 들어있는 데이터 toString이 없어서 주소값이 나옴
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글 갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // 요청페이지 jpa기준
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 화면에 나오는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존배 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지인지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지인지 여부

        // Page<BoardEntity> -> Page<BoardPagingDTO>
        //map(): 엔티티가 담긴 페이지 객체를 dto가 담긴 페이지객체로 변환해주는 역할
        Page<BoardPagingDTO> boardList = boardEntities.map(
                board -> new BoardPagingDTO(board.getId(),
                                            board.getBoardWriter(),
                                            board.getBoardTitle())
        );
        System.out.println("boardList.getContent() = " + boardList.getContent()); // 요청페이지에 들어있는 데이터 toString이 없어서 주소값이 나옴
        System.out.println("boardList.getTotalElements() = " + boardList.getTotalElements()); // 전체 글 갯수
        System.out.println("boardList.getNumber() = " + boardList.getNumber()); // 요청페이지 jpa기준
        System.out.println("boardList.getTotalPages() = " + boardList.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardList.getSize() = " + boardList.getSize()); // 한 화면에 나오는 글 갯수
        System.out.println("boardList.hasPrevious() = " + boardList.hasPrevious()); // 이전 페이지 존배 여부
        System.out.println("boardList.isFirst() = " + boardList.isFirst()); // 첫 페이지인지 여부
        System.out.println("boardList.isLast() = " + boardList.isLast()); // 마지막 페이지인지 여부
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("게시글 삭제")
    public void boardDelete(){
       br.deleteById(1L);
    }
}
