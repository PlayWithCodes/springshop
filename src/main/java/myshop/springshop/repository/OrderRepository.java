package myshop.springshop.repository;

import lombok.RequiredArgsConstructor;
import myshop.springshop.domain.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager entityManager;

    public void save(Order order) {
        entityManager.persist(order);
    }

    public Order findOrder(Long id) {
        return entityManager.find(Order.class, id);
    }
}
