package myshop.springshop.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager entityManager;

    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> orders = getOrders();

        orders.forEach(orderQueryDto -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(orderQueryDto.getOrderId());
            orderQueryDto.setOrderItems(orderItems);
        });

        return orders;
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return entityManager.createQuery(
                        "select new myshop.springshop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                                " from OrderItem oi" +
                                " join oi.item i" +
                                " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    private List<OrderQueryDto> getOrders() {
        return entityManager.createQuery(
                        "select new myshop.springshop.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.orderStatus, d.address) from Order o" +
                                " join o.member m" +
                                " join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }

    public List<OrderQueryDto> findAllByDto() {
        List<OrderQueryDto> orders = getOrders();

        List<Long> orderIds = getOrderIds(orders);
        Map<Long, List<OrderItemQueryDto>> orderItemMap = getOrderItemMap(orderIds);

        orders.forEach(orderQueryDto -> orderQueryDto.setOrderItems(orderItemMap.get(orderQueryDto.getOrderId())));

        return orders;
    }

    private Map<Long, List<OrderItemQueryDto>> getOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryDto> orderItems = entityManager.createQuery(
                        "select new myshop.springshop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                                " from OrderItem oi" +
                                " join oi.item i" +
                                " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));
        return orderItemMap;
    }

    private List<Long> getOrderIds(List<OrderQueryDto> orders) {
        return orders.stream()
                .map(orderQueryDto -> orderQueryDto.getOrderId())
                .collect(Collectors.toList());
    }
}
