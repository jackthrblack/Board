package com.icia.board.controller;

import com.icia.board.dto.BoardDetailDTO;
import com.icia.board.dto.BoardSaveDTO;
import com.icia.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/")
    public String findAll(Model model){
        List<BoardDetailDTO> boardList = bs.findAll();
        model.addAttribute("boardList",boardList);
        log.info("findAll 호출");
        return "board/findAll";
    }

    @GetMapping("/{boardId}")
    public String detail(@PathVariable("boardId") Long boardId, Model model){
        log.info("글보기 메서드 호출. 요청글번호: {}",boardId);
        BoardDetailDTO board = bs.findById(boardId);
        model.addAttribute("board",board);
        return "board/findById";
    }

    @DeleteMapping("{boardId}")
    public ResponseEntity deleteById(@PathVariable("boardId") Long boardId){
        bs.deleteById(boardId);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @PostMapping("/{boardId}")
    public @ResponseBody
    BoardDetailDTO detail2(@PathVariable("boardId") Long boardId){
        BoardDetailDTO board = bs.findById(boardId);
        return board;
    }
}
