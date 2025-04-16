package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.*;

//prototype 범위 동작 테스트
public class PrototypeTest {


    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        //getBean()을 호출할 때마다 매번 다른 인스턴스가 생성됨
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);

        assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        prototypeBean1.destroy();
        prototypeBean2.destroy();

        //하지만 prototype 스코프는 Spring이 관리하지 않기 때문에 destroy()는 호출되지 않음
        //ac.close()해도 destroy는 실행되지 않음 : 직접 호출해줘야함
        ac.close();
    }

    @Scope("prototype") //해당 Bean은 요청할 때마다 새로 생성됨
    static class PrototypeBean{
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
