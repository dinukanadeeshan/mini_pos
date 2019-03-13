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
import org.syscolabs.cx.pos.dto.exception.OrderNotFoundException;
import org.syscolabs.cx.pos.dto.model.Order;
import org.syscolabs.cx.pos.dto.model.OrderItem;
import org.syscolabs.cx.pos.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SyscoPosApplication.class)
@WebAppConfiguration
public class OrderServiceTest {

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    private OrderItem orderItem;

    @Before
    public void setUp() throws Exception {
        this.orderItem = new OrderItem();
        this.orderItem.setQty(3);
        this.orderItem.setOrderId("5c88c5f5336365aa741394e2");
        this.orderItem.setItemId("5c87aaaab4b2fb629c1b3ef1");
        this.orderItem.setUnit_price(90.00);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addNewItem() {
        Order o = new Order();
        o.set_id(new ObjectId(orderItem.getOrderId()));

        when(orderRepository.findBy_id(orderItem.getOrderId())).thenReturn(o);
        when(orderRepository.save(o)).thenReturn(o);

        ObjectId objectId = orderService.addNewItem(orderItem);
        assertTrue(objectId != null);
        assertTrue(objectId.toHexString().equals(orderItem.getOrderId()));
    }

    @Test(expected = OrderNotFoundException.class)
    public void addNewItemWithInvalidOrderId() {
        this.orderItem.setOrderId("5c87ab5db4b2fb629c1b3ef2");
        orderService.addNewItem(orderItem);
    }

    @Test
    public void updateItemQty() {
        Order o = new Order();
        o.set_id(new ObjectId(orderItem.getOrderId()));
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem);
        o.setItemList(orderItemList);

        when(orderRepository.findBy_id(orderItem.getOrderId())).thenReturn(o);
        when(orderRepository.save(o)).thenReturn(o);

        orderItem.setQty(10);
        ObjectId objectId = orderService.updateItemQty(orderItem);

        assertNotNull(objectId);
        assertEquals(objectId.toHexString(), "5c88c5f5336365aa741394e2");

    }
}