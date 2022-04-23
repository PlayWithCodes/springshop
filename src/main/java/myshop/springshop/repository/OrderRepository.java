package myshop.springshop.repository;

import lombok.RequiredArgsConstructor;
import myshop.springshop.domain.Order;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        Join<Object, Object> objectJoin = orderRoot.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        if (orderSearch.getOrderStatus() != null) {
            Predicate status = criteriaBuilder.equal(orderRoot.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = criteriaBuilder.like(objectJoin.get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        criteriaQuery.where(criteriaBuilder.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = entityManager.createQuery(criteriaQuery).setMaxResults(1000);
        return query.getResultList();
    }
}
