package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    //jpa 쓰려면 entitymanager를 주입받아야함
    //JPA에서 실제로 DB와 연결하고 SQL을 실행하는 객체
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        //persist = INSERT
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        //find = SELECT * FROM member WHERE id = ?
        //DB의 member 테이블에서 id가 일치하는 데이터를 찾아서 자바 객체 Member로 만들어 반환해줘
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        //JPQL을 사용해서 name으로 검색
        //:name 은 바인딩 변수, .setParameter로 실제 값을 넣음
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name",name)
                .getResultList(); //결과 리스트로 받음

        return result.stream().findAny();

    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }
}
