package org.syscolabs.cx.pos.controller;

import org.bson.types.ObjectId;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
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
import org.syscolabs.cx.pos.model.Pets;
import org.syscolabs.cx.pos.repository.PetsRepository;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SyscoPosApplication.class)
@WebAppConfiguration
public class PetsControllerTest {

    MockMvc mockMvc;

    List<Pets> petsList;

    String accessToken;
    @MockBean
    PetsRepository petsRepository;
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Before
    public void setup() throws Exception {


        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();// Standalone context
        // mockMvc = MockMvcBuilders.webAppContextSetup(wac)
        // .build();
        Pets manga1 = new Pets(null, "Tarsha", "cross", "kdsjfoklom");
        Pets manga2 = new Pets(null, "Tadsfasdfrsha", "asgdgdkf", "kdsjfadsfoklom");

        petsList = new ArrayList<>();
        petsList.add(manga1);
        petsList.add(manga2);

        this.accessToken = obtainAccessToken("Dinuka", "password");
    }

    @Test
    public void getAllPets() throws Exception {
        // Mocking service
        Mockito.when(petsRepository.findAll()).thenReturn(petsList);
        mockMvc.perform(MockMvcRequestBuilders.get("/pets/").header(
                "Authorization",
                "Bearer " + this.accessToken).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Tarsha")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is("Tadsfasdfrsha")));

    }

    @Test
    public void getPetById() throws Exception {
        Mockito.when(petsRepository.findBy_id(ArgumentMatchers.any(ObjectId.class))).thenReturn(petsList.get(0));
        mockMvc.perform(MockMvcRequestBuilders.get("/pets/id/5c85f361f674acf0b07eb066")
//                .header(
//                "Authorization",
//                "Bearer " + this.accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Tarsha")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.species", Matchers.is("cross")));

    }

    @Test
    public void savePet() {
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