package repository;

import model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    // DB에서 학번으로 회원을 찾음
    Optional<Member> findByStudentId(String studentId);

    // DB에 학번이 이미 존재하는지 true, false
    boolean existsByStudentId(String studentId);
}
