package hello.core.order;

import hello.core.member.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

    MemberService memberService = new MemberServiceImpl();
    OrderService orderService = new OrderServiceImpl();

    @Test
    void createOrder() {
        long memeberId = 1L;
        Member member = new Member(memeberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createorder(memeberId, "itemA", 1000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);

    }
}
