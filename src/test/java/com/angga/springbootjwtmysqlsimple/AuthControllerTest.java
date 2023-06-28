package com.angga.springbootjwtmysqlsimple;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

public class AuthControllerTest extends SpringbootJwtMysqlSimpleApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Test
    public void testHelloworld() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(get("/hello-world")).andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"));

    }

    @WithMockUser(value = "test", password = "pass")
    @Test
    public void testAuth() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity()) // enable security for the mock set up
                .build();
        String authzToken = mockMvc
                .perform(
                        post("/auth")
                                .contentType("application/json;charset=UTF-8")
                                .content(""))
                .andExpect(content().contentType("application/json"))
                .andReturn().getResponse().getContentAsString();

        System.out.print(authzToken);

    }
}
