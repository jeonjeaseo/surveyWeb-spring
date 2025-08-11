package service;

import model.Member;
import org.springframework.stereotype.Service;
import repository.MemberRepository;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public boolean registerMember(Member member) {
        if(memberRepository.existsByStudentId(member.getStudentId())) {
            return false;
        }

        memberRepository.save(member);

        return true;
    }
}
