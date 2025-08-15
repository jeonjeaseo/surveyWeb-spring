package com.survey.demo.controller;

import com.survey.demo.domain.Member;
import com.survey.demo.repository.MemberRepository;
import com.survey.demo.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

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
        String result = memberService.registerMember(member);

        if(result.equals("STUDENT_ID")) {
            model.addAttribute("error", "이미 존재하는 학번입니다.");
            return "admin_add";
        }
        if(result.equals("EMAIL")) {
            model.addAttribute("error", "이미 존재하는 이메일입니다.");
            return "admin_add";
        }

        return "redirect:/admin/members";
    }

    // 회원 삭제
    @GetMapping("/members/delete/{studentId}")
    public String deleteMember(@PathVariable String studentId) {
        memberService.deleteMember(studentId);

        return "redirect:/admin/members";
    }

    // 회원 수정
    @GetMapping("/members/edit/{studentId}")
    public String showEditMemberForm(@PathVariable String studentId, Model model) {
        Member member = memberService.findByStudentId(studentId);
        model.addAttribute("member", member);

        return "admin_edit";
    }
    @PostMapping("/members/edit/{studentId}")
    public String editMember(@PathVariable String studentId, @ModelAttribute Member member, Model model) {

        // !studentId.equals(member.getStudentId())
        // = 수정 전 학번(studentId)과 수정 후 이메일(member.getStudentId())이 다를 때
        // if(학번이 바뀌었을 때 && 바뀐 학번이 DB에 존재하면)
        if(!studentId.equals(member.getStudentId()) && memberRepository.existsByStudentId(member.getStudentId())) {
            model.addAttribute("error", "이미 존재하는 학번입니다.");
            model.addAttribute("member", member);

            return "admin_edit";
        }

        Member originMember = memberService.findByStudentId(studentId);

        // !originMember.getEmail().equals(member.getEmail())
        // = 기존 이메일(originMember.getEmail())과 수정 후 이메일(member.getEmail())이 다를 때
        // if(이메일이 바뀌었을 때 && 바뀐 이메일이 DB에 존재하면)
        if(!originMember.getEmail().equals(member.getEmail()) && memberRepository.existsByEmail(member.getEmail())) {
            model.addAttribute("error", "이미 존재하는 이메일입니다.");
            model.addAttribute("member", member);

            return "admin_edit";
        }


        if(!studentId.equals(member.getStudentId())) {
            memberService.deleteMember(studentId);
        }

        memberService.updateMember(member);

        return "redirect:/admin/members";
    }




}
