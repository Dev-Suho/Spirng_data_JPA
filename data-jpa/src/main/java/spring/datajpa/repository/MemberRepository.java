package spring.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.datajpa.dto.MemberDto;
import spring.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByNameAndAgeGreaterThan(String name, int age);

    List<Member> findTop4HelloBy();

    @Query("select m from Member m where m.name = :name and m.age = :age")
    List<Member> findUser(@Param("name") String name, @Param("age") int age);

    @Query("select m.name from Member m")
    List<String> findNameList();

    @Query("select new spring.datajpa.dto.MemberDto(mt.id, mt.name, t.name)  from Member mt join mt.team t")
    List<MemberDto> findMember();
}
