package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//할인 정책
public class FixDiscountPolicy implements DiscountPolicy{
    private int discountFixAmount = 1000; //천원 할인

    @Override
    public int discount(Member member, int price) {
        //조건 : VIP 여야됨
        if (member.getGrade() == Grade.VIP) {
            return discountFixAmount;
        } else {
            return 0;
        }
    }
}
