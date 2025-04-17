package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.*;

//singleton bean이 prototype bean을 주입받을 때의 주의점
public class SingletonWithPrototypeTest1 {

    //spring이 getBean() 호출마다 새 인스턴스를 생성하는지 확인
    @Test
    void prototypeFind() {
        //spring container 만들어주기
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    //ClientBean은 싱글톤이기 때문에 spring container가 처음 한번만 생성 -> PrototypeBean도 한번만 주입됨.
    //prototypeBean은 계속 같은 인스턴스를 참조
    static class ClientBean{ //singleton Bean

        //항상 새로운 프로토타입 빈이 생성되게 ObjectProvider : Dependency Lookup
        //필요할 때마다 직접 prototype Bean을 요청
        @Autowired
        private ObjectProvider<PrototypeBean> prototypeBeanObjectProvider;

        public int logic(){
            //getObject()를 호출할 때마다 Spring이 새로운 프로토타입 인스턴스를 만들어줌
            PrototypeBean prototypeBean = prototypeBeanObjectProvider.getObject();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype") //매번 새로 만들어지는 prototype scope Bean
    static class PrototypeBean{
        private int count = 0;

        public void addCount(){
            count++;
        }
        public int getCount(){
            return count;
        }

        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init = " + this);
        }

        @PreDestroy
        public void destroy(){
            System.out.println(" PrototypeBean.destroy ");
        }
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class, ClientBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }
}
