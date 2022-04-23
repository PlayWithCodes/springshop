package myshop.springshop.repository;

import lombok.Getter;
import lombok.Setter;
import myshop.springshop.domain.OrderStatus;

@Getter @Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}
