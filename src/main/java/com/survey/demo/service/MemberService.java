package service;

import lombok.RequiredArgsConstructor;
import model.Member;
import org.springframework.stereotype.Service;
import repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public boolean registerMember(Member member) {
        if(memberRepository.existsByStudentId(member.getStudentId())) {
            return false; // 학번이 중복이면 false
        }

        memberRepository.save(member);

        return true;
    }

    public Member findByStudentId(String studentId) {
        return memberRepository.findByStudentId(studentId).orElse(null);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword);
    }
}

