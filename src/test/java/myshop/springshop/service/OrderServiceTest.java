package myshop.springshop.service;

import myshop.springshop.domain.Address;
import myshop.springshop.domain.Member;
import myshop.springshop.domain.Order;
import myshop.springshop.domain.OrderStatus;
import myshop.springshop.domain.item.Book;
import myshop.springshop.exception.NotEnoughStockException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    ItemService itemService;
    @Autowired
    OrderService orderService;

    @Test
    @DisplayName("상품주문")
    public void order() {
        //given
        Member member = getMember();
        Book book = getBook("Name", "Author", 10000, 10);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        Order order = orderService.findOrder(orderId);

        //then
        assertEquals(order.getOrderStatus(), OrderStatus.ORDER, "상품 주문시 상태는 ORDER");
        assertEquals(order.getOrderItems().size(), 1, "주문한 상품 종류 수가 정확해야 한다");
        assertEquals(order.getOrderTotalPrice(), 10000 * orderCount, "주문 가격은 가격 * 수량");
        assertEquals(8, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다");
    }

    @Test
    @DisplayName("재고수량 초과주문")
    public void outOfStock() {
        //given
        Member member = getMember();
        Book book = getBook("Name1", "Author1", 10000, 10);
        //when
        int orderCount = 20;
        //then
        assertThrows(NotEnoughStockException.class, () ->
                orderService.order(member.getId(), book.getId(), orderCount));
    }

    @Test
    @DisplayName("주문취소")
    public void cancelOrder() {
        //given
        Member member = getMember();
        Book book = getBook("Name2", "Author2", 20000, 20);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //when
        orderService.cancelOrder(orderId);
        Order order = orderService.findOrder(orderId);
        //then
        assertEquals(OrderStatus.CANCEL, order.getOrderStatus(), "주문상태가 취소로 되어야 함");
        assertEquals(20, book.getStockQuantity(), "취소된주문 수량만큼 재고가 늘어나야 함");
    }

    private Book getBook(String name, String author, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        itemService.saveItem(book);
        return book;
    }

    private Member getMember() {
        Member member = new Member();
        member.setName("member1");
        Address address = new Address("City", "Street", "111-222");
        member.setAddress(address);
        memberService.join(member);
        return member;
    }
}