package org.syscolabs.cx.pos.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.syscolabs.cx.pos.SyscoPosApplication;
import org.syscolabs.cx.pos.dto.model.Item;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SyscoPosApplication.class})
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findAllItems() {
        List<Item> allItems = itemRepository.findAll();
        Assert.assertNotNull(allItems);
    }
}