package spring.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.transaction.annotation.Transactional;
import spring.datajpa.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository memberJpaRepository;

    @Test
    void testMember() {
        Member member = new Member("suho");
        Member save = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(save.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getName()).isEqualTo(member.getName());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void crudMember() {
        Member memberA = new Member("memberA");
        Member memberB = new Member("memberB");

        memberJpaRepository.save(memberA);
        memberJpaRepository.save(memberB);

        Member findMemberA = memberJpaRepository.findById(memberA.getId()).get();
        Member findMemberB = memberJpaRepository.findById(memberB.getId()).get();

        assertThat(findMemberA).isEqualTo(memberA);
        assertThat(findMemberB).isEqualTo(memberB);

        List<Member> memberList = memberJpaRepository.findAll();
        assertThat(memberList).hasSize(2);

        long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        memberJpaRepository.delete(memberA);

        long deleteMember = memberJpaRepository.count();
        assertThat(deleteMember).isEqualTo(1);
    }

    @Test
    void findByNameAndAgeGreaterThen() {
        Member memberA = new Member("suho", 23);
        Member memberB = new Member("buho", 32);

        memberJpaRepository.save(memberA);
        memberJpaRepository.save(memberB);

        List<Member> memberList = memberJpaRepository.findByNameAndAgeGreaterThen("suho", 15);
        assertThat(memberList.get(0).getName()).isEqualTo("suho");
        assertThat(memberList.get(0).getAge()).isEqualTo(23);
        assertThat(memberList.size()).isEqualTo(1);
    }

    @Test
    void paging() {
        memberJpaRepository.save(new Member("java1", 15));
        memberJpaRepository.save(new Member("java2", 15));
        memberJpaRepository.save(new Member("java3", 15));
        memberJpaRepository.save(new Member("java4", 15));
        memberJpaRepository.save(new Member("java5", 15));
        memberJpaRepository.save(new Member("java6", 15));

        int age = 15;
        int offset = 1;
        int limit = 3;

        List<Member> memberList = memberJpaRepository.findByPage(age, offset, limit);
        long totalCount = memberJpaRepository.totalCount(age);

        assertThat(memberList.size()).isEqualTo(3);
        assertThat(totalCount).isEqualTo(6);
    }

    @Test
    void plusAge() {
        memberJpaRepository.save(new Member("memberA", 20));
        memberJpaRepository.save(new Member("memberB", 15));
        memberJpaRepository.save(new Member("memberC", 25));
        memberJpaRepository.save(new Member("memberD", 30));
        memberJpaRepository.save(new Member("memberE", 10));

        int count = memberJpaRepository.plusAge(20);
        assertThat(count).isEqualTo(3);
    }
}