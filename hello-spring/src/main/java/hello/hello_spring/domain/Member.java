package hello.hello_spring.domain;

import jakarta.persistence.*;

@Entity //JPA가 이 클래스를 DB 테이블로 인식
public class Member {

    @Id //id가 테이블의 기본키라는 의미
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본 키 값을 자동 생성
    //identity 전략은 db에서 자동으로 증가시키는 방식 사용
    private Long id;

    @Column(name = "username") //db의 username이라는 컬럼과 연결시키겠다는 뜻
    private String name;

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
