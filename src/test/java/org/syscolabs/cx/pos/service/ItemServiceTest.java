package org.syscolabs.cx.pos.service;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.syscolabs.cx.pos.SyscoPosApplication;
import org.syscolabs.cx.pos.dto.model.Item;
import org.syscolabs.cx.pos.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SyscoPosApplication.class)
@WebAppConfiguration
public class ItemServiceTest {

    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    private List<Item> itemList;

    @Before
    public void setUp() throws Exception {
        this.itemList = new ArrayList<>();

        Item i = new Item();
        i.set_id(new ObjectId("5c87aaaab4b2fb629c1b3ef1"));
        i.setName("Veg Burger");
        i.setQtyOnStock(10);
        i.setUnit_price(120.00);
        i.setStatus("A");
        this.itemList.add(i);

        i = new Item();
        i.set_id(new ObjectId("5c88f8086758596f7b3419d2"));
        i.setName("Fried Rice Veg");
        i.setQtyOnStock(18);
        i.setUnit_price(150.00);
        i.setStatus("A");
        this.itemList.add(i);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAllItemServiceTest() {
        when(itemRepository.findAll()).thenReturn(itemList);

        List<Item> allItems = itemService.getAllItems();

        assertNotNull(allItems);
        assertEquals(2, allItems.size());
        assertEquals("5c87aaaab4b2fb629c1b3ef1", allItems.get(0).getItemId());
        assertEquals("5c88f8086758596f7b3419d2", allItems.get(1).getItemId());
    }


    @Test
    public void getAllItemIfRepositoryReturnNull() {
        when(itemRepository.findAll()).thenReturn(null);

        List<Item> allItems = itemService.getAllItems();

        assertNotNull(allItems);
        assertEquals(0, allItems.size());
    }
}