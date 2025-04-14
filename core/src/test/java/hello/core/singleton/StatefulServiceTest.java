package hello.core.singleton;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

//싱글톤 패턴의 문제점
class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //ThreadA : A가 10000원 주문
        statefulService1.order("UserA", 1000);
        //ThreadB : B가 20000원 주문
        statefulService2.order("UserB", 20000);

        //ThreadA
        int price = statefulService2.getPrice();
        System.out.println("price = " + price);

        org.assertj.core.api.Assertions.assertThat(statefulService1.getPrice()).isEqualTo(statefulService2.getPrice());
    }

    static class TestConfig{
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }

    }

}