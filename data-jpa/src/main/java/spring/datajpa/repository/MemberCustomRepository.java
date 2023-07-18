package spring.datajpa.repository;

import spring.datajpa.entity.Member;

import java.util.List;

public interface MemberCustomRepository {

    List<Member> findMemberCustom();
}
