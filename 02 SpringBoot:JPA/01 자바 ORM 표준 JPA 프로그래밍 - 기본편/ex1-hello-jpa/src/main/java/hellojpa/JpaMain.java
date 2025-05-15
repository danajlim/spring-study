package hellojpa;

import jakarta.persistence.*;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            //비영속(new) -> new로 생성했지만 persist 안한 상태
            Member member = em.find(Member.class, 150L);
            member.setName("Dana");

            //영속 -> JPA가 관리하는 상태
            em.persist(member);

            //준영속 상태 -> 이제 jpa에서 관리 안함: 영속성 컨텍스트에서 빠져나감
            em.detach(member); //특정 엔티티만 준영속 상태로 전환
            em.clear(); //영속성 컨텍스트를 완전히 초기화: 다 준영속 상태로 됨
            em.close(); // 영속성 컨텍스트 종료


            //삭제 -> 삭제 예정
            em.remove(member);

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();


    }
}
