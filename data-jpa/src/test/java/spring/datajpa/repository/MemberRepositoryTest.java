package spring.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import spring.datajpa.dto.MemberDto;
import spring.datajpa.entity.Member;
import spring.datajpa.entity.Team;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;

    @Test
    void testMember() {
        Member member = new Member("suho");

        Member save = memberRepository.save(member);
        Member findMember = memberRepository.findById(save.getId()).get();

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getName()).isEqualTo(member.getName());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void crudMember() {
        Member memberA = new Member("memberA");
        Member memberB = new Member("memberB");

        memberRepository.save(memberA);
        memberRepository.save(memberB);

        Member findMemberA = memberRepository.findById(memberA.getId()).get();
        Member findMemberB = memberRepository.findById(memberB.getId()).get();

        assertThat(findMemberA).isEqualTo(memberA);
        assertThat(findMemberB).isEqualTo(memberB);

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(2);

        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        memberRepository.delete(memberA);

        long deleteMember = memberRepository.count();
        assertThat(deleteMember).isEqualTo(1);
    }

    @Test
    void findByNameAndAgeGreaterThen() {
        Member memberA = new Member("suho", 23);
        Member memberB = new Member("buho", 32);

        memberRepository.save(memberA);
        memberRepository.save(memberB);

        List<Member> memberList = memberRepository.findByNameAndAgeGreaterThan("suho", 15);
        assertThat(memberList.get(0).getName()).isEqualTo("suho");
        assertThat(memberList.get(0).getAge()).isEqualTo(23);
        assertThat(memberList.size()).isEqualTo(1);
    }

    @Test
    void findBy() {
        List<Member> findBy = memberRepository.findTop4HelloBy();
    }


    @Test
    void testQuery() {
        Member memberA = new Member("suho", 23);
        Member memberB = new Member("buho", 32);

        memberRepository.save(memberA);
        memberRepository.save(memberB);

        List<Member> memberList = memberRepository.findUser("suho", 23);
        assertThat(memberList.get(0)).isEqualTo(memberA);
    }

    @Test
    void findName() {
        Member memberA = new Member("suho", 23);
        Member memberB = new Member("buho", 32);

        memberRepository.save(memberA);
        memberRepository.save(memberB);

        List<String> memberList = memberRepository.findNameList();
        for(String nameList : memberList) {
            System.out.println("nameList = " + nameList);
        }
    }

    @Test
    void findMemberDto() {
        Team team = new Team("java");
        teamRepository.save(team);

        Member member = new Member("java", 11);
        member.setTeam(team);
        memberRepository.save(member);

        List<MemberDto> memberDtoList = memberRepository.findMember();
        for (MemberDto mt : memberDtoList) {
            System.out.println("mt = " + mt);
        }
    }


    @Test
    void findByNames() {
        Member memberA = new Member("suho", 23);
        Member memberB = new Member("buho", 32);

        memberRepository.save(memberA);
        memberRepository.save(memberB);

        List<Member> memberList = memberRepository.findByNames(Arrays.asList("java", "kotlin"));
        for(Member member : memberList) {
            System.out.println("nameList = " + member);
        }
    }

    @Test
    void returnType() {
        Member memberA = new Member("zuho", 23);
        Member memberB = new Member("suho", 32);

        memberRepository.save(memberA);
        memberRepository.save(memberB);

        Optional<Member> name = memberRepository.findOptionalByName("buho");
        System.out.println("result = " + name);
    }
}