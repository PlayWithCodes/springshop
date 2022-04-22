package myshop.springshop.domain;

import lombok.Getter;
import lombok.Setter;
import myshop.springshop.domain.item.Item;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

    public OrderItem createOrderItem(Item item, int orderPrice, int count) {
        item.removeStock(count);
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        return orderItem;
    }

    public void cancel() {
        this.item.addStock(count);
    }

    public int getItemTotalPrice() {
        return this.orderPrice * this.count;
    }
}
