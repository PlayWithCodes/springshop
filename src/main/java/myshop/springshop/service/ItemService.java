package myshop.springshop.service;

import lombok.RequiredArgsConstructor;
import myshop.springshop.domain.item.Item;
import myshop.springshop.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public Item findItem(Long id) {
        return itemRepository.findItem(id);
    }

    public List<Item> findItems() {
        return itemRepository.findItems();
    }
}
