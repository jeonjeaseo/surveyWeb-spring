package controller;

import jakarta.servlet.http.HttpSession;
import model.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        boolean success = memberService.registerMember(member); // 학번이 중복인지 아닌지 true, false

        if(!success) {
            model.addAttribute("error", "이미 사용중인 학번입니다.");
            return "register";
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(@RequestParam String studentId, @RequestParam String password, Model model,
                        HttpSession session /* 로그인 성공시 사용자 정보 저장 */) {
        Member member = memberService.findByStudentId(studentId);

        // 비밀번호가 null이거나 틀렸을 때
        if(member == null || memberService.checkPassword(password, member.getPassword())) {
            model.addAttribute("error", "비밀번호가 틀렸습니다");

            return "login";
        }

        // 로그인 성공했을 때
        session.setAttribute("loginMember", member);
        return "redirect:/";
    }


}
