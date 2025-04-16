package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor //final이 붙은 두개를 파라미터로 받는 생성자를 만들어줌
// 주문 생성 로직을 처리하는 구현 클래스
public class OrderServiceImpl implements OrderService {

    //사용할 의존 구현체 객체를 생성
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }


    @Override
    public Order createorder(long memberId, String itemName, int itemPrice) {

        //주문한 회원의 정보를 조회
        Member member = memberRepository.findById((long) memberId);

        //회원 정보 기반으로 할인 정책 계산
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order((long) memberId, itemName, itemPrice, discountPrice);

    }
}
