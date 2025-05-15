package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity //JPA에서 관리할 엔티티다
@TableGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        table = "MY_SEQUENCES",
        pkColumnValue = "MEMBER_SEQ",
        allocationSize = 1
)
public class Member {

    @Id //이 필드가 기본키를 인식
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_SEQ_GENERATOR") //JPA에서 기본 키 값을 자동 생성해주는 방법
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
