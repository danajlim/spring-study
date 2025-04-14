package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.shouldHaveThrown;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextSameBeanFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    //타입으로만 조회할때 예외 발생
    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 중복 오류가 발생")
    void findBeanByTypeDuplicate(){
        //어떤 빈을 줘야할지 모호해서 NoUniqueBeanDefinitionException 예외 발생함
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(MemberRepository.class));
    }

    //이름과 타입으로 조회
    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면 빈 이름을 지정하면 됨")
    void findBeanByName(){
        //구체적인 이름 memberRepository1을 지정해줌 -> 정상적으로 동작
        MemberRepository memberRepository = ac.getBean("memberRepository1", MemberRepository.class);
        assertThat(memberRepository).isInstanceOf(MemberRepository.class);
    }

    //특정 타입의 모든 빈 조회
    @Test
    @DisplayName("특정 타입을 모두 조회하기")
    void findAllBeanType(){
        //해당 타입 모든 빈을 Map으로 반환 <빈 이름 : 빈 객체>
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + "value = " + beansOfType.get(key));
        }
        System.out.println("beansOfType = " + beansOfType);
        //수가 2개인지 검증
        assertThat(beansOfType.size()).isEqualTo(2);
    }

    //내부 설정 클래스
    @Configuration
    //스프링 컨테이너에 MemberRepository 타입의 빈 두개 있음
    static class SameBeanConfig {
        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }
}
