package com.icia.board.controller;

import com.icia.board.dto.MemberDetailDTO;
import com.icia.board.dto.MemberSaveDTO;
import com.icia.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService ms;

    @GetMapping("/save")
    public String save_form(){
        return "/member/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberSaveDTO memberSaveDTO){

        Long memberId = ms.save(memberSaveDTO);

        return "index";
    }

    @GetMapping("{memberId}")
    public String findById(@PathVariable("memberId") Long memberId, Model model) {
        MemberDetailDTO member = ms.findById(memberId);
        model.addAttribute("member", member);
        return "member/findById";
    }
}
