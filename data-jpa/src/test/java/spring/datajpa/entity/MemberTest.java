package spring.datajpa.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import spring.datajpa.repository.MemberRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void testEntity() {
        Team team1 = new Team("team1");
        Team team2 = new Team("team2");
        em.persist(team1);
        em.persist(team2);

        Member member1 = new Member("member1", 20, team1);
        Member member2 = new Member("member2", 23, team1);
        Member member3 = new Member("member3", 28, team2);
        
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        
        em.flush();
        em.clear();

        List<Member> memberList = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        
        for (Member member : memberList) {
            System.out.println("member = " + member);
            System.out.println("member.getTeam() = " + member.getTeam());
        }

    }

    @Test
    void JpaEventBaseEntity() throws Exception {
        Member member = new Member("memberA");
        memberRepository.save(member);

        Thread.sleep(100);
        member.setName("memberB");

        em.flush();
        em.clear();

        Member findMember = memberRepository.findById(member.getId()).get();

        assertThat(member.getName()).isEqualTo(findMember.getName());
        System.out.println("findMember = " + findMember.getCreatedDate());
        System.out.println("findMember = " + findMember.getUpdatedDate());
        System.out.println("findMember = " + findMember.getCreatedBy());
        System.out.println("findMember = " + findMember.getLastModifiedBy());
    }
}