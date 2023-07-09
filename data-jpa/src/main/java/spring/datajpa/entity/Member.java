package spring.datajpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    public Long id;
    private String name;

    protected Member() {
    }

    public Member(String name) {
        this.name = name;
    }
}
