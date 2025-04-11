package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;


// 주문 생성 로직을 처리하는 구현 클래스
public class OrderServiceImpl implements OrderService{

    //사용할 의존 구현체 객체를 생성
    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createorder(Long memberId, String itemName, int itemPrice) {

        //주문한 회원의 정보를 조회
        Member member = memberRepository.findById(memberId);

        //회원 정보 기반으로 할인 정책 계산
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);

    }
}
