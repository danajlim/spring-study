package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        //EntityManager를 생성할 수 있는 팩토리 객체를 생성: 데이터베이스와 연결
        // 하나만 생성해서 애플리케이션 전체에 공유
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        //실제로 DB 작업을 수행하는 JPA 핵심 객체: 단위 작업은 여기서 수행
        // 쓰레드간 공유하면 안됨 -> 사용하고 버려야함
        EntityManager em = emf.createEntityManager();

        //트랜잭션 시작: JPA는 데이터 변경 작업 시 항상 트랜잭션 안에서 작업해야함.
        //flush, persist, merge, remove
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //1. 등록
            //Member 객체 생성: 아직 메모리 상에 존재, DB에는 반영 X
            Member member = new Member();
            member.setId(2L);
            member.setName("Jelly");

            //만든 Member 객체를 영속성 컨텍스트에 저장 (DB에 저장 예정으로 등록됨)
            em.persist(member);

            //2. 조회
            Member findMember = em.find(Member.class, 1L);

            //JPQL로 전체 회원 검색
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10) //1~10까지 가져오기
                    .getResultList();


            //3. 삭제
            em.remove(findMember);

            //4. 수정
            findMember.setName("HelloJpa");

            //트랙잭션 commit: JPA가 내부적으로 insert into.. 쿼리를 생성하고 DB에 전송
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        //자원 해제하고 DB 연결 종료
        emf.close();
    }
}
