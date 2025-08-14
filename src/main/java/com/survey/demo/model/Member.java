package com.survey.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity // DB 연결(JPA)
@Getter // lombok 자동 생성
@Setter
public class Member {

    @Column(nullable = false) // not null
    private String name; // 이름
    @Column(nullable = false)
    private String password; // 비밀번호
    @Id // PK(중복 안됨)
    private String studentId; // 학번
    @Column(nullable = false, unique = true) // not null + 중복 불가
    private String email; // 이메일

    @Override
    public String toString() {
        return "이름 = " + name +
                "학번 = " + studentId +
                "이메일 = " + email;
    }

}
