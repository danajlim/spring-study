package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.*;

//싱글톤 범위 동작 테스트
//스프링의 기본 Bean scope는 싱글톤 == 컨테이너 내에서 해당 Bean 클래스는 오직 하나만 생성, 동일한 인스턴스를 공유
public class SingletonTest {

    @Test
    void singletonBeanFind() {
        //Annotation 기반으로 설정된 ApplicationContext
        //SingletonBean 클래스가 Bean으로 등록됨
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);

        //두번 getBean을 호출해도, 스코프가 싱글톤이기 때문에 동일한 인스턴스 반환
        SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
        SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);

        System.out.println("singletonBean1 = " + singletonBean1);
        System.out.println("singletonBean2 = " + singletonBean2);
        assertThat(singletonBean1).isSameAs(singletonBean2);

        //ApplicationContext를 종료하면서 Bean을 소멸시킴 → @PreDestroy 실행됨
        ac.close();
    }

    @Scope("singleton") //Spring Bean으로 등록되며, 싱글톤으로 관리
    static class SingletonBean {
        @PostConstruct //초기화 : Bean 생성 후 호출
        public void init() {
            System.out.println("SingletonBean.init");
        }

        @PreDestroy //정리 : Context 종료 시 호출
        public void destroy() {
            System.out.println("SingletonBean.destroy");
        }
    }
}
