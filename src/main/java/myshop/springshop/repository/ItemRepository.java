package myshop.springshop.repository;

import lombok.RequiredArgsConstructor;
import myshop.springshop.domain.item.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager entityManager;

    public void save(Item item) {
        if (item.getId() == null) {
            entityManager.persist(item);
        } else {
            entityManager.merge(item);
        }
    }

    public Item findItem(Long id) {
        return entityManager.find(Item.class, id);
    }

    public List<Item> findItems() {
        return entityManager.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
