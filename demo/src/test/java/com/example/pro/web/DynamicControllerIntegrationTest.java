package com.example.pro.web;

import com.example.pro.domain.Dynamic;
import com.example.pro.mapper.DynamicMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DynamicControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private DynamicMapper dynamicMapper;

    @BeforeEach
    void seed() {
        dynamicMapper.delete(null);

        Dynamic d1 = new Dynamic();
        d1.setType("STATUS");
        d1.setPayload("Ran posted a blog");
        dynamicMapper.insert(d1);

        Dynamic d2 = new Dynamic();
        d2.setType("EVENT");
        d2.setPayload("System maintenance");
        dynamicMapper.insert(d2);
    }

    @Test
    void list_filterByType() throws Exception {
        String filter = """
          {"type":"STATUS"}
        """;

        mockMvc.perform(post("/dynamic/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2001))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].type").value("STATUS"));
    }

    @Test
    void page_basic() throws Exception {
        String paging = """
          {"page":1,"size":1,"desc":"id"}
        """;

        mockMvc.perform(post("/dynamic/page")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(paging))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2001))
                .andExpect(jsonPath("$.data.records", hasSize(1)))
                .andExpect(jsonPath("$.data.total", is(2)));
    }
}