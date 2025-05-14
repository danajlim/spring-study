package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity //JPA에서 관리할 엔티티다
@SequenceGenerator(name = "member_seq_generator", //JPA가 사용할 시퀀스(DB 자동 증가 번호)를 설정해주는 애노테이션
        sequenceName = "member_seq",
        initialValue = 1000,
        allocationSize = 5)
public class Member {

    @Id //이 필드가 기본키를 인식
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "member_seq_generator") //JPA에서 기본 키 값을 자동 생성해주는 방법
    private Long id;

    @Column(name = "name", nullable = false, length = 50, columnDefinition = "varchar(100) default ‘EMPTY'")
    private String name;

    //Constructor
    public Member() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
