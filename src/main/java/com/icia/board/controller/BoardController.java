package com.icia.board.controller;

import com.icia.board.common.PagingConst;
import com.icia.board.dto.BoardDetailDTO;
import com.icia.board.dto.BoardPagingDTO;
import com.icia.board.dto.BoardSaveDTO;
import com.icia.board.dto.BoardUpdateDTO;
import com.icia.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j // 로그기록
public class BoardController {

    private final BoardService bs;

    @GetMapping("/save")
    public String save_form(Model model){
        model.addAttribute("board",new BoardSaveDTO());
        return "/board/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardSaveDTO boardSaveDTO){
    Long boardId = bs.save(boardSaveDTO);
    return "index";
    }

    // /board/
    @GetMapping("/")
    public String findAll(Model model){
        List<BoardDetailDTO> boardList = bs.findAll();
        model.addAttribute("boardList",boardList);
        log.info("findAll 호출");
        return "board/findAll";
    }

    // 페이징처리(/board?page=5) => query 스트링 => 주소뒤에 물음표를 붙이고 그 뒤에 어떤 값을 담아서 보내는 방법
    // 반드시 page라는 이름으로 요청해야한다.
    // 페이지는 1페이지, 글 갯수는 3개 이렇게하면 1페이지에는 8 7 6 이렇게 나오고 만약 글 갯수가9개이면 9 8 7 이렇게 나올것이다.
    // 즉, 디테일을 봤을때 처럼 고유 번호가 아니라 계속해서 변하는 값이다.
    // 5번글(/board/5) 이 5번이라는 번호는 고유번호(PK)
    @GetMapping
    public String paging(@PageableDefault(page=1)Pageable pageable, Model model){
        // @PageableDefault(page=1)Pageable => 페이지 요청값이 없을때는 기본적으로 1페이지를 띄우겠다. 디폴트값 설정

        //Page라는 객체가 있다.
        Page<BoardPagingDTO> boardList = bs.paging(pageable);
        model.addAttribute("boardList",boardList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < boardList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : boardList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage",endPage);
        return  "board/paging";

    }

    @GetMapping("/{boardId}")
    public String detail(@PathVariable("boardId") Long boardId, Model model){
        log.info("글보기 메서드 호출. 요청글번호: {}",boardId);
        BoardDetailDTO board = bs.findById(boardId);
        model.addAttribute("board",board);
        return "board/findById";
    }

    //아작이 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity deleteById(@PathVariable("boardId") Long boardId){
        bs.deleteById(boardId);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 아작이 조회
   /* @PostMapping("/{boardId}")
    public @ResponseBody
    BoardDetailDTO detail2(@PathVariable("boardId") Long boardId){
        BoardDetailDTO board = bs.findById(boardId);
        return board;
    }*/

    //아작이 조회
    // ResponseEntity를 데이터와 상태코드를 같이 넘기는 것
    @PostMapping("/{boardId}")
    public ResponseEntity findById(@PathVariable Long boardId){
        BoardDetailDTO board = bs.findById(boardId);
        //<객체타입>(객체,상태코드)
        return new ResponseEntity<BoardDetailDTO>(board,HttpStatus.OK);
    }

    @GetMapping("/update/{boardId}")
    public String update_form(Model model, @PathVariable("boardId") Long boardId){

        BoardDetailDTO board = bs.findById(boardId);
        model.addAttribute("board",board);
        return "/board/update";
    }


   /* @PostMapping("/update")
    public String update(@ModelAttribute BoardDetailDTO boardDetailDTO){

        Long boardId = bs.update(boardDetailDTO);
        return "redirect:/board/"+boardDetailDTO.getBoardId();
    }*/

    /*    @PutMapping("{boardId}")
    public ResponseEntity update2(@RequestBody BoardDetailDTO boardDetailDTO){

        Long boardId = bs.update(boardDetailDTO);
        return new ResponseEntity(HttpStatus.OK);
    }*/

    @PostMapping("/update")
    public String update(@ModelAttribute BoardUpdateDTO boardUpdateDTO){

        bs.update(boardUpdateDTO);
        return "redirect:/board/"+boardUpdateDTO.getBoardId();
    }

    @PutMapping("{boardId}")
    public ResponseEntity update2(@RequestBody BoardUpdateDTO boardUpdateDTO){

        Long boardId = bs.update(boardUpdateDTO);
        return new ResponseEntity(HttpStatus.OK);
    }




}
