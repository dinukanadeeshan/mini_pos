package org.syscolabs.cx.pos.repository;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.syscolabs.cx.pos.SyscoPosApplication;
import org.syscolabs.cx.pos.dto.model.Order;
import org.syscolabs.cx.pos.dto.model.OrderItem;

import static org.junit.Assert.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SyscoPosApplication.class})
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    private Order order;
    private String orderId;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        if (order != null) orderRepository.delete(order);
    }

    @Test
    public void saveOrderWithNewOrder() {
        this.order = new Order();
        this.order = orderRepository.save(order);
        this.orderId = order.get_id().toHexString();
        assertTrue(order != null);
        assertFalse(orderId.isEmpty());
    }

    @Test
    public void saveOrderWithExistOrder() {
        this.order = new Order();
        this.order = orderRepository.save(order);
        this.order.setTotal_amount(120.00);

        orderId = orderRepository.save(order).get_id().toHexString();
        assertTrue(order != null);
        assertFalse(orderId.isEmpty());
    }

    @Test
    public void findByIdWithCorrectId() {
        this.order = new Order();
        this.order = orderRepository.save(order);
        this.orderId = order.get_id().toHexString();
        Order order = orderRepository.findBy_id(orderId);
        assertTrue(order != null);
        assertTrue("Not Correct Order Id", order.get_id().equals(new ObjectId(orderId)));
    }

    @Test
    public void findByIdWithWrongId() {
        Order order = orderRepository.findBy_id("5c87ab5db4b2fb629c1b3ef2");
        assertTrue(order == null);
    }

    @Test
    public void findByIdWithNullValue() {
        Order order = orderRepository.findBy_id(null);
        assertTrue(order == null);
    }
}

