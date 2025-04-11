package hello.core.order;

public interface OrderService {
    Order createorder(Long memberId, String itemName, int itemPrice);
}
