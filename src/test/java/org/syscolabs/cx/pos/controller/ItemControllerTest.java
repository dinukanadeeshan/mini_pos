package org.syscolabs.cx.pos.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import org.syscolabs.cx.pos.dto.model.Item;
import org.syscolabs.cx.pos.service.ItemService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SyscoPosApplication.class)
@WebAppConfiguration
public class ItemControllerTest {

    @MockBean
    private ItemService itemService;

    private MockMvc mockMvc;
    private String accessToken;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private FilterChainProxy springSecurityFilterChain;


    private List<Item> itemList;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();// Standalone context
        this.accessToken = obtainAccessToken("Dinuka", "password");
        this.itemList = new ArrayList<>();
        Item i = new Item();
        i.setItemId("5c87aaaab4b2fb629c1b3ef1");
        i.setName("Veg Burger");
        i.setQtyOnStock(10);
        i.setUnit_price(120.00);
        i.setStatus("A");
        this.itemList.add(i);
        i = new Item();
        i.setItemId("5c88f8086758596f7b3419d2");
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
    public void getAllItems() throws Exception {
        when(itemService.getAllItems()).thenReturn(itemList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/item/")
                .header(
                        "Authorization",
                        "Bearer " + this.accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.list[0].itemId")
                        .value("5c87aaaab4b2fb629c1b3ef1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.list[1].itemId")
                        .value("5c88f8086758596f7b3419d2"));


    }

    @Test
    public void getAllItemsWhenItemListEmpty() throws Exception {
        itemList = new ArrayList<>();
        when(itemService.getAllItems()).thenReturn(itemList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/item/")
                .header(
                        "Authorization",
                        "Bearer " + this.accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.list").isEmpty());


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