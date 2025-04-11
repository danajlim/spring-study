package hello.core.order;


//주문 정보 클래스
public class Order {

    private Long memberId; //주문한 회원 아이디
    private String itemName; // 주문한 상품 이름
    private int itemPrice; //상품 원가
    private int discountPrice; // 할인된 금액

    public Order(Long memberId, String itemName, int itemPrice, int discountPrice) {
        this.memberId = memberId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.discountPrice = discountPrice;
    }

    //할인 후 금액 계산
    public int calculatePrice(){
        return itemPrice - discountPrice;
    }

    public Long getMemberId() {
        return memberId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "memberId=" + memberId +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                ", discountPrice=" + discountPrice +
                '}';
    }
}
