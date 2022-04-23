package myshop.springshop.service;

import lombok.RequiredArgsConstructor;
import myshop.springshop.domain.Delivery;
import myshop.springshop.domain.Member;
import myshop.springshop.domain.Order;
import myshop.springshop.domain.OrderItem;
import myshop.springshop.domain.item.Item;
import myshop.springshop.repository.ItemRepository;
import myshop.springshop.repository.MemberRepository;
import myshop.springshop.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findById(memberId);
        Item item = itemRepository.findItem(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOrder(orderId);
        order.cancelOrder();
    }

    public Order findOrder(Long id) {
        return orderRepository.findOrder(id);
    }
}
