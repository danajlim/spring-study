package hello.core.order;

public interface OrderService {
    Order createorder(long memberId, String itemName, int itemPrice);
}
