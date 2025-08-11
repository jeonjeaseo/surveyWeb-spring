package controller;

import model.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import repository.MemberRepository;
import service.MemberService;

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
        boolean success = memberService.registerMember(member);

        if(!success) {
            model.addAttribute("error", "이미 사용중인 학번입니다.");
            return "register";
        }

        return "redirect:/login";
    }
}
