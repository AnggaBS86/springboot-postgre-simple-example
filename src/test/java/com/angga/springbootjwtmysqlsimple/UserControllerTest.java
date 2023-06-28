package com.angga.springbootjwtmysqlsimple;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.angga.springbootjwtmysqlsimple.api.entity.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

public class UserControllerTest extends SpringbootJwtMysqlSimpleApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
    public void testGetUsername() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity()) // enable security for the mock set up
                .build();
        String authzToken = mockMvc
                .perform(
                        get("/users/name").with(user("admin")))
                .andExpect(content().contentType("application/json"))
                .andReturn().getResponse().getContentAsString();

        System.out.print(authzToken);

    }

}
