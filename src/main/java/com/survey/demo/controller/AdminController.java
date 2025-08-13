package com.survey.demo.controller;

import com.survey.demo.model.Member;
import com.survey.demo.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;

    // 관리자 메인
    @GetMapping
    public String adminMain(HttpSession session, Model model) {
        Member admin = (Member) session.getAttribute("loginMember");
        admin.setStudentId("0000");
        admin.setPassword("admin");

        if(admin == null || !admin.getPassword().equals("admin")) {
            return "redirect:/members/login";
        }

        model.addAttribute("adminName", admin.getName());
        return "admin";

    }

    // 회원 목록
    @GetMapping("/members")
    public String memberList(Model model) {
        model.addAttribute("members", memberService.findAll());

        return "admin_list";
    }

    // 회원 추가
    @GetMapping("/members/add")
    public String showAddMemberForm(Model model) {
        model.addAttribute("member", new Member());

        return "admin_add";
    }
    @PostMapping("/members/add")
    public String memberAdd(@ModelAttribute Member member, Model model) {
        boolean success = memberService.registerMember(member);

        if(!success) {
            model.addAttribute("error", "이미 존재하는 학번입니다.");
            return "admin_add";
        }

        return "redirect:/admin/members";
    }


    // 회원 수정





}
