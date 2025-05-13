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

            Member findMember1 = em.find(Member.class, 101L);
            Member findMember2 = em.find(Member.class, 101L);

            System.out.println("result= " + (findMember1==findMember2));


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
