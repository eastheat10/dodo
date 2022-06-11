package com.nhnacademy.gateway.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = IndexController.class)
@WithMockUser
class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void index() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
               .andExpect(view().name("index"));
    }
}