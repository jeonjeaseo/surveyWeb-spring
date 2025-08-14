package com.survey.demo.service;

import lombok.RequiredArgsConstructor;
import com.survey.demo.model.Member;
import org.springframework.stereotype.Service;
import com.survey.demo.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public String registerMember(Member member) {
        if(memberRepository.existsByStudentId(member.getStudentId())) {
            return "STUDENT_ID";
        }
        if(memberRepository.existsByEmail(member.getEmail())) {
            return "EMAIL";
        }

        memberRepository.save(member);

        return "SUCCESS";
    }
    public boolean registerMemberEmail(Member member){

        if(memberRepository.existsByEmail(member.getEmail())) {
            return false; // 이메일이 중복이면 false
        }

        return true;
    }

    public Member findByStudentId(String studentId) {
        return memberRepository.findByStudentId(studentId).orElse(null);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword);
    }

    public List<Member> findAll() {
        return memberRepository.findAll(); // JPA
    }
}

