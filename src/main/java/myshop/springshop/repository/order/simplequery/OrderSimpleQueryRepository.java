package myshop.springshop.repository.order.simplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager entityManager;

    public List<OrderSimpleQueryDto> findOrderDtos() {
        List<OrderSimpleQueryDto> resultList = entityManager.createQuery(
                        "select new myshop.springshop.repository.order.simplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.orderStatus, d.address)" +
                                " from Order o" +
                                " join o.member m" +
                                " join o.delivery d", OrderSimpleQueryDto.class)
                .getResultList();
        return resultList;
    }
}
