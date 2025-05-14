package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity //JPA에서 관리할 엔티티다
public class Member {

    @Id //이 필드가 기본키를 인식
    private Long id;

    /* name -> 실제 필드명과 DB 컬럼명이 다를 때 지정
    * nullable = false -> NOT NULL 조건
    * length = 10 -> 문자 길이 제약 조건, String에서만
    * columnDefinition = "varchar(100) Default 'EMPTY'" -> 컬럼 정보를 직접 넣어줌
    * */
    @Column(name = "name", nullable = false, length = 50, columnDefinition = "varchar(100) default ‘EMPTY'")
    private String name;

    private Integer age;

    @Enumerated(EnumType.STRING) //enum을 DB에 문자열로 안전하게 저장
    private RoleType roleType;

    private LocalDateTime createdDate; //LocalDateTime: date + time
    private LocalDateTime lastModifiedDate;

    @Lob //대용량 데이터 저장을 위한 애노테이션
    private String description;

    //Constructor
    public Member() {
    }
    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    //Getter, Setter
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
