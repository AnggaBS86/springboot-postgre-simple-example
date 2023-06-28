package com.angga.springbootjwtmysqlsimple;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

public class RegisterControllerTest extends SpringbootJwtMysqlSimpleApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Test
    public void testRegister() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity()) // enable security for the mock set up
                .build();
        mockMvc
                .perform(
                        post("/register").with(user("admin")))
                .andExpect(content().contentType("application/json"))
                .andReturn().getResponse().getContentAsString();

    }

    @Test
    public void testUpdateName() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity()) // enable security for the mock set up
                .build();
        mockMvc
                .perform(
                        post("/register/update").with(user("admin")))
                .andExpect(content().contentType("application/json"))
                .andReturn().getResponse().getContentAsString();

    }
}
