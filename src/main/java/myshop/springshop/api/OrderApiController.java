package myshop.springshop.api;

import lombok.RequiredArgsConstructor;
import myshop.springshop.domain.Order;
import myshop.springshop.repository.OrderRepository;
import myshop.springshop.repository.OrderSearch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
        for (Order order : orders) {
            order.getMember().getName();
            order.getDelivery().getAddress();

            order.getOrderItems().stream().forEach(orderItem -> orderItem.getItem().getName());
        }
        return orders;
    }
}
