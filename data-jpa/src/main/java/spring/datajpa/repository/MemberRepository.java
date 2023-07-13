package spring.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByNameAndAgeGreaterThan(String name, int age);

    List<Member> findTop4HelloBy();
}
