package com.survey.demo.controller;

import jakarta.servlet.http.HttpSession;
import com.survey.demo.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.survey.demo.service.MemberService;

@Controller
@RequestMapping("/members")
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/register")
    public String showRegisterform(Model model) {
        model.addAttribute("member", new Member());

        return "register";
    }

    @PostMapping("/register")
    public String registerMember(@ModelAttribute Member member, Model model) {
        String result = memberService.registerMember(member);

        if(result.equals("STUDENT_ID")) {
            model.addAttribute("error", "이미 사용중인 학번입니다.");
            return "register";
        }
        if(result.equals("TEACHER_ID")) {
            model.addAttribute("error", "이미 사용중인 이메일입니다.");
        }

        return "redirect:/members/login";
    }

    @GetMapping("/login")
    public String showLoginform() {
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String studentId, @RequestParam String password, Model model,
                        HttpSession session /* 로그인 성공시 사용자 정보 저장 */) {

        // 관리자
        if(studentId.equals("0000") && password.equals("admin")) {
            Member admin = new Member();
            admin.setStudentId("0000");
            admin.setName("관리자");
            session.setAttribute("loginMember", admin);

            return "redirect:/admin";
        }

        Member member = memberService.findByStudentId(studentId);

        // 비밀번호가 null이거나 틀렸을 때
        if(member == null || !memberService.checkPassword(password, member.getPassword())) {
            model.addAttribute("error", "비밀번호가 틀렸습니다");

            return "login";
        }

        session.setAttribute("loginMember", member);

        // 로그인 성공했을 때
        session.setAttribute("loginMember", member);
        return "redirect:/members/surveySelect";
    }
    @GetMapping("/surveySelect")
    public String showSurveySelect(Model model) {
        return "surveySelect";
    }

    @GetMapping("/logout")
    public String goLogin(Model model) {
        return "login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/members/login";
    }

}
