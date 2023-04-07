package com.goldgshok.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldgshok.parser.app.Main;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = {
        Main.class
})
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.properties")
public abstract class BaseControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;

    @BeforeEach
    void init() {
        mvc = MockMvcBuilders.webAppContextSetup(wac)
                .dispatchOptions(true)
                .build();
    }

    @SneakyThrows
    public final ResultActions makeRequest(String url, Object request) {
        var mockRequest = MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        if (request != null) {
            var body = objectMapper.writeValueAsString(request);
            mockRequest = mockRequest.content(body);
        }
        return mvc.perform(mockRequest);
    }
}
