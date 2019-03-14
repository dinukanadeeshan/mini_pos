package org.syscolabs.cx.pos.controller;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.syscolabs.cx.pos.SyscoPosApplication;
import org.syscolabs.cx.pos.dto.model.Order;
import org.syscolabs.cx.pos.dto.model.OrderItem;
import org.syscolabs.cx.pos.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SyscoPosApplication.class)
@WebAppConfiguration
public class OrderControllerTest {

    @MockBean
    OrderRepository orderRepository;
    private MockMvc mockMvc;
    private String accessToken;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private FilterChainProxy springSecurityFilterChain;
    private Order order;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();// Standalone context
        this.accessToken = obtainAccessToken("Dinuka", "password");
        this.order = new Order();
        ArrayList<OrderItem> list = new ArrayList<>();
        list.add(new OrderItem("5c87ab5db4b2fb629c1b3ef2", "5c87aaaab4b2fb629c1b3ef1"));
        this.order.setItemList(list);
        this.order.setTotal_amount(120.00);
        this.order.set_id(new ObjectId("5c87ab5db4b2fb629c1b3ef2"));
    }

    @After
    public void tearDown() throws Exception {
//        this.order = null;
//        this.accessToken = null;
//        System.gc();
    }

    @Test
    public void addNewItemToOrderNormalRequest() throws Exception {
        when(orderRepository.save(order)).thenReturn(this.order);
        when(orderRepository.findBy_id("5c87ab5db4b2fb629c1b3ef2")).thenReturn(this.order);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("orderId", "5c87ab5db4b2fb629c1b3ef2");
        params.add("itemId", "5c87aaaab4b2fb629c1b3ef1");
        params.add("unitPrice", "60.00");
        params.add("qty", "2");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/order/add/item")
                .header(
                        "Authorization",
                        "Bearer " + this.accessToken).params(params)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addNewItemToOrderWithoutOrderId() throws Exception {

        when(orderRepository.findBy_id("5c87ab5db4b2fb629c1b3ef2")).thenReturn(this.order);


        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("orderId", "5c87ab5db4b2fb629c1b3ef2");
        params.add("itemId", "5c87aaaab4b2fb629c1b3ef1");
        params.add("unitPrice", "60.00");
        params.add("qty", "2");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/order/add/item")
                .header(
                        "Authorization",
                        "Bearer " + this.accessToken).params(params)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void addNewItemToOrderWithoutItemId() throws Exception {


        when(orderRepository.findBy_id("5c87ab5db4b2fb629c1b3ef2")).thenReturn(this.order);


        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("orderId", "5c87ab5db4b2fb629c1b3ef2");
//        params.add("itemId", "5c87aaaab4b2fb629c1b3ef1");
        params.add("unitPrice", "60.00");
        params.add("qty", "2");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/order/add/item")
                .header(
                        "Authorization",
                        "Bearer " + this.accessToken).params(params)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }


    @Test
    public void updateItemQtyOfOrderNormalRequest() throws Exception {

        when(orderRepository.findBy_id("5c87ab5db4b2fb629c1b3ef2")).thenReturn(this.order);
        when(orderRepository.save(order)).thenReturn(this.order);


        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("orderId", "5c87ab5db4b2fb629c1b3ef2");
        params.add("itemId", "5c87aaaab4b2fb629c1b3ef1");
        params.add("qty", "3");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/order/update/item/qty")
                .header(
                        "Authorization",
                        "Bearer " + this.accessToken).params(params)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getAllOrders() throws Exception {
        List<Order> orderList = new ArrayList<>();
        Order o = new Order();
        o.set_id(new ObjectId("5c89de72640eaa5a0e7e5442"));
        orderList.add(o);

        when(orderRepository.findAll()).thenReturn(orderList);

        ResultActions orderResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/order/")
                .header(
                        "Authorization",
                        "Bearer " + this.accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        System.out.println(jsonParser.parseMap(orderResult.andReturn().getResponse().getContentAsString()));

    }


    private String obtainAccessToken(String username, String password) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "sysco_pos_client");
        params.add("username", username);
        params.add("password", password);

        ResultActions result
                = mockMvc.perform(MockMvcRequestBuilders.post("/oauth/token")
                .params(params)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("sysco_pos_client", "devglan-secret"))
                .accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }
}