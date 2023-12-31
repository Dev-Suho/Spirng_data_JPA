package spring.datajpa.repository;

import lombok.RequiredArgsConstructor;
import spring.datajpa.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberCustomRepository {

    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m")
                .getResultList();
    }
}
