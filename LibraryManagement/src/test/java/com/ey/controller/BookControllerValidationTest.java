package com.ey.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ey.dto.request.BookCreateRequest;
import com.ey.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc(addFilters = false)
class BookControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Mock the service because @WebMvcTest loads only the MVC slice
    @MockBean
    private BookService bookService;

    @Test
    void createBook_withEmptyBody_returns400() throws Exception {
        // Empty request object (violates @NotBlank/@NotNull on the DTO)
        BookCreateRequest req = new BookCreateRequest();

        mockMvc.perform(
                post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req))
        ).andExpect(status().isBadRequest());
    }
}



