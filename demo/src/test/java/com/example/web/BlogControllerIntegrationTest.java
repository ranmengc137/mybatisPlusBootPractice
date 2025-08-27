package com.example.web;

import com.example.domain.Blog;
import com.example.mapper.BlogMapper;
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

/**
 * Full-stack tests for /blog endpoints.
 * Uses the real Spring context + MyBatis-Plus + your ResponseAdvice wrapper.
 */
@SpringBootTest
@AutoConfigureMockMvc
class BlogControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private BlogMapper blogMapper;

    @BeforeEach
    void seed() {
        // Clean and seed minimal data
        blogMapper.delete(null);

        Blog a = new Blog();
        a.setTitle("My First Blog");
        a.setAuthor("Ran");
        a.setContent("hello");
        blogMapper.insert(a);

        Blog b = new Blog();
        b.setTitle("Spring Boot with MP");
        b.setAuthor("Liqing");
        b.setContent("guide");
        blogMapper.insert(b);
    }

    @Test
    void insert_shouldCreate() throws Exception {
        String body = """
          {"title":"New Post","author":"Ran","content":"content..."}
        """;

        mockMvc.perform(post("/blog/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2001))
                .andExpect(jsonPath("$.message", containsString("Inserted")));
    }

    @Test
    void list_shouldFilterByNonNullFields() throws Exception {
        // Filter by author = Ran (your BaseController builds QueryWrapper by non-null fields)
        String filter = """
          {"author":"Ran"}
        """;

        mockMvc.perform(post("/blog/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2001))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].author").value("Ran"))
                .andExpect(jsonPath("$.data[0].title").value("My First Blog"));
    }

    @Test
    void page_shouldReturnPagedData_sortedAsc() throws Exception {
        // page asc by created_at (or any existing column; adjust to your schema)
        String paging = """
          {
            "page": 1,
            "size": 1,
            "asc": "id",
            "condition": { "author": "Ran" }
          }
        """;

        mockMvc.perform(post("/blog/page")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(paging))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2001))
                .andExpect(jsonPath("$.data.records", hasSize(1)))
                .andExpect(jsonPath("$.data.total", greaterThanOrEqualTo(1)));
    }

    @Test
    void getById_shouldReturnOne() throws Exception {
        // Grab an ID to query (just pick the first by author)
        Long id = blogMapper.selectList(null).stream()
                .filter(b -> "Ran".equals(b.getAuthor()))
                .findFirst()
                .map(Blog::getId)
                .orElseThrow();

        mockMvc.perform(get("/blog/getById").param("id", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2001))
                .andExpect(jsonPath("$.data.id").value(id));
    }

    @Test
    void updateById_shouldUpdate() throws Exception {
        Blog existing = blogMapper.selectList(null).get(0);

        String update = """
          {"id": %d, "title":"Updated Title"}
        """.formatted(existing.getId());

        mockMvc.perform(post("/blog/updateById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(update))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2001))
                .andExpect(jsonPath("$.message", containsString("Updated")));
    }

    @Test
    void deleteById_shouldDeleteMultiple() throws Exception {
        var ids = blogMapper.selectList(null).stream().map(Blog::getId).toList();
        String json = ids.toString(); // e.g. [1,2]

        mockMvc.perform(post("/blog/deleteById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2001))
                .andExpect(jsonPath("$.message", containsString("Deleted")));
    }

    @Test
    void count_shouldReturnCountByFilter() throws Exception {
        String filter = """
          {"author":"Ran"}
        """;

        mockMvc.perform(post("/blog/count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2001))
                .andExpect(jsonPath("$.data", greaterThanOrEqualTo(1)));
    }
}