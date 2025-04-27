package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//추상화된걸 생성자를 통해서 구현체랑 연결해줌
// 애플리케이션의 실제 동작에 필요한 구현 객체를 생성
public class AppConfig {

    @Bean
    //어떤 역할 사용하는지 선언
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    //그 역할의 실제 구현체가 뭔지 반환
    public MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }
}
