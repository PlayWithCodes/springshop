package myshop.springshop.service;

import myshop.springshop.domain.item.Book;
import myshop.springshop.domain.item.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired private ItemService itemService;

    @Test
    @DisplayName("아이템 저장")
    public void saveItem() {
        //given
        Book book = new Book();
        book.setAuthor("author1");
        //when
        itemService.saveItem(book);
        //then
        assertThat(itemService.findItems().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("아이템 조회")
    public void findItems() {
        //given
        Book book1 = new Book();
        book1.setAuthor("author2");
        Book book2 = new Book();
        book2.setAuthor("author2");
        //when
        itemService.saveItem(book1);
        itemService.saveItem(book2);
        //then
        assertThat(itemService.findItems().size()).isEqualTo(2);
    }
}